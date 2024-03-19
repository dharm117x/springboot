package com.example.config;

import java.nio.file.Files;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	private final UserDetailsService userService;

	@Value("${jwt.clientId:trusted_client}")
	private String clientId;

	@Value("${jwt.client-secret:secret}")
	private String clientSecret;

	@Value("${jwt.signing-key:123}")
	private String jwtSigningKey;

	@Value("${jwt.accessTokenValidititySeconds:43200}") // 12 hours
	private int accessTokenValiditySeconds;

	@Value("${jwt.authorizedGrantTypes:client_credentials,password,authorization_code,refresh_token}")
	private String[] authorizedGrantTypes;

	@Value("${jwt.refreshTokenValiditySeconds:2592000}") // 30 days
	private int refreshTokenValiditySeconds;

	@Value("classpath:jwtPublicKey.pem")
	private Resource publicKey;
	@Value("classpath:jwtPrivateKey.pem")
	private Resource privateKey;
	

	public OAuth2Configuration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			UserDetailsService userService) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//InMemoryClientDetailsServiceBuilder client = clients.inMemory();
		JdbcClientDetailsServiceBuilder client = clients.jdbc(dataSource);
		client.withClient(clientId).secret(passwordEncoder.encode(clientSecret))
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds).authorizedGrantTypes(authorizedGrantTypes)
				.scopes("read", "write")
				.resourceIds("api");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.tokenKeyAccess("permitAll");
	}
	
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.accessTokenConverter(accessTokenConverter()).userDetailsService(userService)
				.authenticationManager(authenticationManager);
	}

	@Bean
	JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		try {
			converter.setSigningKey(Files.readString(privateKey.getFile().toPath()));
			converter.setVerifierKey(Files.readString(publicKey.getFile().toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return converter;
	}
		
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
