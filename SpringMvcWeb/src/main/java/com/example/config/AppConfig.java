package com.example.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {

	@Bean
	public DataSource getDataSource() {
		try {
			return (DataSource) new JndiTemplate().lookup("java:comp/env/jdbc/OracleDB");
		} catch (NamingException e) {
		}
		return null;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		
		return new JdbcTemplate(getDataSource());
	}
}
