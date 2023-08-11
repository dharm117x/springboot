package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private RequestMatcher requiresMatcher = new OrRequestMatcher(new AntPathRequestMatcher("/api/**"));


	@Autowired
	AuthenticationProvider provider;

	public WebSecurityConfig(AuthenticationProvider provider) {
		super();
		this.provider = provider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/**");
		web.ignoring().antMatchers("/h2-console/**");
		web.ignoring().antMatchers("/token/**");
		
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.and()
			.authenticationProvider(provider)
			.addFilterBefore(authTokeFilter(), AnonymousAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/api/**").permitAll()
			.requestMatchers(requiresMatcher)
			.authenticated()
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.logout().disable();
			
	}

	@Bean
	public AuthTokenFilter authTokeFilter() throws Exception {
		AuthTokenFilter filter = new AuthTokenFilter(requiresMatcher);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	
	@Bean
	public AuthenticationEntryPoint forboddenEntry() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}
}
