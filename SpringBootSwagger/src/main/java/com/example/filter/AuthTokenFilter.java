package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.util.CommonUtils;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			String token = parseToken(request);
			if (token != null && CommonUtils.isTokenExist(token)) {
				PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(token,
						token);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				authentication.setAuthenticated(true);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
			SecurityContextHolder.clearContext();
			response.sendError(401, "Token: Sorry, we couldn't authenticate your request: " + e.getMessage());
			return;
		}
		System.out.println("OncePerRequestFilter req.........." + request.getRequestURI());
		chain.doFilter(request, response);
		System.out.println("OncePerRequestFilter res.........." + request.getRequestURI());

	}

	private String parseToken(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		} else {
			return CommonUtils.getToken();
		}

	}

}
