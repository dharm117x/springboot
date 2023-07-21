package com.example.config;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import com.example.entity.LanguageEntity;
import com.example.repos.LanguageRepository;

@Component(value = "messageSource")
public class DBMessageSource extends AbstractMessageSource{

	public DBMessageSource() {
		System.out.println("DBMessageSource.DBMessageSource()");
	}
	
	@Autowired
	private LanguageRepository repository;
	
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		System.out.println("DBMessageSource.resolveCode()");
		LanguageEntity language = repository.findByKeyAndLocale(code, locale.getLanguage());
		if(language== null) {
			language = repository.findByKeyAndLocale(code, "en");
		}
		
		return new MessageFormat(language.getContent(), locale);
	}

}
