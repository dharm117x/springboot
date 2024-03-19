package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.UserData;

@RestController
public class UserController {
	
	List<UserData> data = new ArrayList<>();
	{
		data.add(new UserData(1L,"A"));
		data.add(new UserData(2L,"B"));
		data.add(new UserData(3L,"C"));
	}
	
	
	@GetMapping("/user")
	public UserData getUser() {
		return data.get(0);
	}

	
	@GetMapping("/users")
	public List<UserData> getUsers() {
		
		return data;
	}

}
