package com.packt.cantata.controller;



import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;
import com.packt.cantata.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


  @Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository usrrepo;

	private final PasswordEncoder Encoder;


	@GetMapping(value="/info")
	public Optional<User> getUser(HttpServletRequest request){
		String user = jwtService.getAuthUser(request);
		System.out.println(user+"dddd");

		return usrrepo.findById(user);
	}


	@PostMapping(value="/pwdchange")
	@Transactional
	public void updateUser(HttpServletRequest request, @RequestParam("pwd") String pwd){
		String user = jwtService.getAuthUser(request);
		Optional<User> useru = usrrepo.findById(user);
		System.out.println(user+"ddddffff");
		useru.get().setPassword(Encoder.encode(pwd));

	}



}
