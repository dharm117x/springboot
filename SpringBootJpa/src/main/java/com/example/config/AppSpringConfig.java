package com.example.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class AppSpringConfig {

	@Bean
	@Profile(value = { "local" })
	public DataSource getLocalDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.addScripts("db/user_schema.sql", "db/user_insert.sql");
		builder.setType(EmbeddedDatabaseType.H2);
		builder.setName("TEST");
		return builder.build();
	}


}
