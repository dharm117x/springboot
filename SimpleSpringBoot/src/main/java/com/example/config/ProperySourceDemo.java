package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:config.properties")
public class ProperySourceDemo implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProperySourceDemo.class);

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Autowired
    Environment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info(driver);
        LOGGER.info(url);
        LOGGER.info(password);
        LOGGER.info(username);
        setDatabaseConfig();
    }

    private void setDatabaseConfig() {
    	String data = env.getProperty("jdbc.driver")+env.getProperty("jdbc.url")+env.getProperty("jdbc.username")+env.getProperty("jdbc.password");
    	System.out.println(data);
    }
}