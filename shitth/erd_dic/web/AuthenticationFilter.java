package com.packt.cantata;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.packt.cantata.service.JwtService;
import com.packt.cantata.service.UserDetailsServiceImpl;



@Component 
public class AuthenticationFilter extends OncePerRequestFilter { 
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Override 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
		// Get token from Authorization header 
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (jws != null) { // Verify token and get user 
			String user = jwtService.getAuthUser(request); // Authenticate 
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user).getAuthorities());
			System.out.println("ddddddd"+authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response); 
	}
}

	
