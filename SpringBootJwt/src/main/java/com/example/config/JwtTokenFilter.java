package com.example.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenUtility utility;
	@Autowired
	JwtUserDetailsService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			String username = utility.getUsernameFromToken(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails details = service.loadUserByUsername(username);
				if(utility.validateToken(token, details.getUsername())) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
