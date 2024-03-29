package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@SuppressWarnings("deprecation")
//@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter{

	String clientId = "trusted_client";
	String clientSecret = "secret";
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CustomUserDetailsService userDetailsService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	ResourceLoader resourceLoader;
	@Value("classpath:jwtPublicKey.pem")
	Resource resource;
	
	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter= new JwtAccessTokenConverter();
		try {
			//converter.setSigningKey(Files.readString(resourceLoader.getResource("classpath:jwtPrivateKey.pem").getFile().toPath()));
			//converter.setVerifierKey(Files.readString(resource.getFile().toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return converter;
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).accessTokenConverter(tokenEnhancer());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(encoder.encode(clientSecret)).scopes("read","write")
			.authorizedGrantTypes("client_credentials", "password", "refresh_token")
			.accessTokenValiditySeconds(5000)
			.refreshTokenValiditySeconds(5000);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
}
