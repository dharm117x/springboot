package com.example.config;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtility {

	String secret = "Hello JWT Secret word";

	public Claims getAllclaimsForToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimForToken(String token, Function<Claims, T> claimResolver) {
		Claims claims = getAllclaimsForToken(token);
		return claimResolver.apply(claims);
	}

	public String getUsernameFromToken(String token) {
		return getClaimForToken(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaimForToken(token, Claims::getExpiration);
	}

	public String genrateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 5);
		Date expDate = calendar.getTime();

		JwtBuilder jwtBuilder = Jwts.builder().addClaims(claims).setIssuedAt(calendar.getTime()).setExpiration(expDate)
				.setIssuer(username).setSubject(username).signWith(SignatureAlgorithm.HS256, secret);
		return jwtBuilder.compact();
	}

	public boolean validateToken(String token, String req_username) {
		String username = getUsernameFromToken(token);
		Date expDate = getExpirationDate(token);
		return (username.equals(req_username) && !expDate.before(new Date())) ;
	}

}
