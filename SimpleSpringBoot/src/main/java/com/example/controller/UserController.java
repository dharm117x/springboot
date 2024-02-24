package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.user.UserRepository;
import com.example.data.UserData;
import com.example.model.user.User;

@RestController
@RequestMapping("/rest")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository repository;
	
	@GetMapping(value = "/users")
	public List<User> allUsers() {
	
		return repository.findAll();
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping(value = "/users")
	public void method(@RequestBody MultiValueMap<String, String> values) {
		logger.info("Values:{}", values);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@Valid @RequestBody UserData u) {
		logger.info("User: {}", u);
		User user = new User();
		user.setEmail(u.getEmail());
		user.setName(u.getName());
		user.setDob(u.getDob());
		repository.save(user);
		
		return "Success";
		
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public Map<String, String> name(MethodArgumentNotValidException ex) {
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		Map<String, String> map = new HashMap<String, String>();
		errors.stream().forEach(er -> map.put(er.getField(), er.getDefaultMessage()));
		return map;
	}
}