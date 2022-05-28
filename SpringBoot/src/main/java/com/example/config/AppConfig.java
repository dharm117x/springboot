package com.example.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class AppConfig {
	
	
	@Bean
	@Primary
	public ITemplateResolver thymeleafTemplateResolver() {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setPrefix("mail-templates/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode("HTML");
	    templateResolver.setCharacterEncoding("UTF-8");
	    return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    templateEngine.setTemplateEngineMessageSource(emailMessageSource());
	    return templateEngine;
	}
	
	@Bean
	public ResourceBundleMessageSource emailMessageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("mailMessages");
	    return messageSource;
	}
	
	@Bean
	public JavaMailSender gmailMailSender() {
	    //https://myaccount.google.com/u/1/lesssecureapps -> ON
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
	    mailSender.setUsername("dharm117x@gmail.com");
	    mailSender.setPassword("Cybercom117#");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}
}
