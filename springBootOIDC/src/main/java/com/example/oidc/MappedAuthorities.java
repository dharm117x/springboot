package com.example.oidc;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class MappedAuthorities {
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .authorizeRequests(authorizeRequests -> authorizeRequests
            .mvcMatchers("/my-endpoint")
              .hasAuthority("SCOPE_openid")
            .anyRequest().authenticated()
          );
        return http.build();
    }
    
}