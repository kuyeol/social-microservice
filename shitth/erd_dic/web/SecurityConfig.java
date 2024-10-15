package com.packt.cantata.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.packt.cantata.AuthEntryPoint;
import com.packt.cantata.AuthenticationFilter;
import com.packt.cantata.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration 
@EnableWebSecurity 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { 
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.csrf().disable().cors().and()
//		.authorizeRequests().anyRequest().permitAll();
//	}
	@Autowired
	private UserDetailsServiceImpl userDetailsService; 
	

	private final AuthenticationFilter authenticationFilter;
	
	
	private final AuthEntryPoint exceptionHandler; 
	

	private final PasswordEncoder passwordEncoder;

	@Bean 
	public AuthenticationManager getAuthenticationManager() throws Exception { 
		return authenticationManager(); 
		}
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder); 
		}
	

	@Override 
	protected void configure(HttpSecurity http) throws Exception { 
//		//기능이 올바르게 작동하는지 테스트 하기위해서 시작단계에서는 백엔드 보호되지않게 수정
//		http.csrf().disable().cors().and().authorizeRequests().anyRequest().permitAll();
		
		
		//두번째 단계에서는 백엔드 보안을 활성화하고 필요한 수정사항을 적용
		http.csrf().disable().cors().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
	
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**","/corporations/**","/ticket/**","/member/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated().and()
//		.anyRequest().permitAll().and()
		.exceptionHandling()
		.authenticationEntryPoint(exceptionHandler).and()
		.addFilterBefore(authenticationFilter, 
				UsernamePasswordAuthenticationFilter.class);
		}
	@Bean 
	CorsConfigurationSource corsConfigurationSource() { 
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
		CorsConfiguration config = new CorsConfiguration(); 
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
		config.setAllowedMethods(Arrays.asList("*")); 
		config.setAllowedHeaders(Arrays.asList("*")); 
		config.setAllowCredentials(false); 
		config.applyPermitDefaultValues(); 
		source.registerCorsConfiguration("/**", config); 
		return source; 
		}
	
	

	 
	} 
