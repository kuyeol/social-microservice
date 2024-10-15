package com.packt.cantata.controller;

import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;
import com.packt.cantata.service.JwtService;
import com.packt.cantata.service.LoginService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private UserRepository usrrepo;
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtService jwtService;
    private final PasswordEncoder Encoder;

    @RequestMapping(value = "/updateRecent")
    @Transactional
    public ResponseEntity<User> updateUser(@RequestParam("id") String id) {
        Optional<User> userr = usrrepo.findById(id);
        usrrepo.save(userr.get());
        return ResponseEntity.ok( userr.get());
    }

    @RequestMapping(value = "/pwdchan")
    @Transactional
    public void changePwd(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {

        Optional<User> useru = usrrepo.findById(id);

    }

    @RequestMapping(value = "/remove")
    public void removeMember(HttpServletRequest request) {
        String user = jwtService.getAuthUser(request);
        usrrepo.deleteById(user);
    }
}
