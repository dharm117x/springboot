package com.example.controller;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Resource(name = "messageSource")
	MessageSource message;
	
	@GetMapping("/home")
	public ModelAndView welcome(Model model) {
		ModelAndView view = new ModelAndView("home");
		
		view.addObject("name", "Dharmendra kumar");
		System.out.println("HomeController.welcome()");
		
		return view;
	}
	
	
}
