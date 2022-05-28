package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.filter.AuthTokenFilter;
import com.example.filter.RequestResponseLoggingFilter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableSwagger2
public class WebConfig extends WebSecurityConfigurerAdapter {

	  @Bean
	  public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
	          .paths(PathSelectors.ant("/api/**"))                     
	          .build()                                       
	        .apiInfo(apiInfo());
	  }

	  private ApiInfo apiInfo() {
	      return new ApiInfo(
	        "My REST API", 
	        "Some custom description of API.", 
	        "API TOS", 
	        "Terms of service", "",
	        "License of API", "API license URL");
	  }
	  
	@Bean  
	public FilterRegistrationBean<AuthTokenFilter> authTokenFilter() {
		FilterRegistrationBean<AuthTokenFilter> filterBean = new FilterRegistrationBean<>();
		filterBean.setFilter(new AuthTokenFilter());
		filterBean.addUrlPatterns("/api/**");
		
		return filterBean;
	}  

	@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter(){
	    FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new RequestResponseLoggingFilter());
	    registrationBean.setOrder(1);
	    registrationBean.addUrlPatterns("/api/**");
	        
	    return registrationBean;    
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		String[] urls = {"/webjars/**","/v2/api**","/swagger-resources/**","/swagger**","/csrf/**", "/actuator/**"};
		web.ignoring().antMatchers(urls);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.anyRequest().authenticated();
		
		http.addFilterBefore(authTokenFilter().getFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(loggingFilter().getFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}	
}
