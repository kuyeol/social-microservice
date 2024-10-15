package com.packt.cantata.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findById(username);
		UserBuilder builder = null;
		if (user.isPresent()) {
			User currentUser = user.get();
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(currentUser.getPassword());
			builder.roles(currentUser.getAuth());
			System.out.println(builder.build().toString());
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}
}
