package com.packt.cantata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.AccountCredentials;
import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;
import com.packt.cantata.service.JwtService;
import com.packt.cantata.service.LoginService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping(value="/login")
public class LoginController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository usrrepo;
	@Autowired
	private LoginService loginService;
	@Autowired
	private AuthenticationManager authenticationManager;

	private final PasswordEncoder Encoder;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken( credentials.getId(), credentials.getPassword());
		Authentication auth = authenticationManager.authenticate(creds); // Generate token
		String jwts = jwtService.getToken(auth.getName()); // Build response with the generated token
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.build();
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ResponseEntity<User> postUser(@RequestBody User user) {
		String ecdpwd = Encoder.encode(user.getPassword());
		user.setPassword(ecdpwd);
		User useru = usrrepo.save(user);

        return ResponseEntity.ok(useru);
    }

}
