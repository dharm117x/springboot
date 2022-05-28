package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.service.UserService;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	UserService userservice;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		//ok
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object token = authentication.getCredentials();
//		Optional optional = userservice.findByToken(token.toString());
		
		return Optional.ofNullable(token)
				.map(String:: valueOf)
				.flatMap(userservice :: getByToken)
				.orElseThrow(()-> new UsernameNotFoundException("User not found.."));
	}

}
