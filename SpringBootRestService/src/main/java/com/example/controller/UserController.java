package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AppUser;
import com.example.service.UserService;

@RequestMapping("/api/")
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/userid/{userId}")
	public AppUser getUserById(@PathVariable Integer userId) {
		return userService.getUserById(userId);
	}

	@GetMapping("/users")
	public List<AppUser> allUsers() {
		return userService.getAllUser();
	}

	@PostMapping(path = "/adduser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public AppUser saveUser(@RequestBody @Valid AppUser user) {
		return userService.save(user);
	}
}
