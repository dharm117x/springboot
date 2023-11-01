package com.example.controller;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Resource(name = "messageSource")
	MessageSource message;
	
	@GetMapping("/hello")
	public String welcome(Model model) {
		
		return "home";
	}
}
