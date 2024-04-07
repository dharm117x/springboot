package com.example.config;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class AppSpringConfig {

	@Autowired
	@Lazy
	private SessionFactory sessionFactory;

	@Bean
	@Profile(value = { "local" })
	public DataSource getLocalDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.addScripts("db/user_schema.sql", "db/user_insert.sql");
		builder.setType(EmbeddedDatabaseType.H2);
		builder.setName("TEST");
		return builder.build();
	}
	
	
	@Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
//        dataSourceBuilder.url("jdbc:h2:mem:test_db;Mode=Oracle");
        dataSourceBuilder.url("jdbc:h2:mem:test_db;DB_CLOSE_ON_EXIT=true");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
	
	public Session getSession() {
	    try {
	        return sessionFactory.getCurrentSession();
	    } catch (Exception e) {
	        System.out.println(e.getMessage().toString());
	    }
	    return sessionFactory.openSession();
	}


}
