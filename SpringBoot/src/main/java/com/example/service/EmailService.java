package com.example.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class EmailService {

	@Autowired
	@Qualifier("gmailMailSender")
	private JavaMailSender emailSender;
	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Value("classpath:/mail-logo.png")
	Resource resourceFile;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
	
	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
	    MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	    helper.addInline("attachment.png", resourceFile, "image/png");
	    helper.addInline("attachment.png", new ClassPathResource("mail-logo.png"), "image/png");
	    
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(htmlBody, true);
	    emailSender.send(message);
	}
	

	public void sendTemplateMessage(String to, String subject, Map<String, Object> templateModel)
	        throws MessagingException {
	                
	    Context thymeleafContext = new Context();
	    thymeleafContext.setVariables(templateModel);
	    String htmlBody = thymeleafTemplateEngine.process("vaccine-template.html", thymeleafContext);
	    
	    sendHtmlMessage(to, subject, htmlBody);
	}
}