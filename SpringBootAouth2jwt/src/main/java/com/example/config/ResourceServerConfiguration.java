package com.example.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	 private static final String RESOURCE_ID = "api";

	    @Override
	    public void configure(ResourceServerSecurityConfigurer resources) {
	        resources.resourceId(RESOURCE_ID).stateless(false);
	    }

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http.headers().frameOptions().disable().and()
	                .authorizeRequests()
	                .antMatchers("/register").permitAll()
	                .antMatchers("/ex/**").authenticated();
	    }

}