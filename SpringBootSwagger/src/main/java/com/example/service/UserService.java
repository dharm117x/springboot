package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired UserRepository repository;
	
	public User getUserById(Integer id) {
		return repository.findById(id).get() ;
	}

	public void deleteUserById(Integer id) {
		repository.deleteById(id);
	}

	public List<User> getAllUser() {
		return repository.findAll();
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
	public User save(User user) {
		return repository.save(user);
	}
	
	public void saveAll(List<User> users) {
		repository.saveAll(users);
	}
	
}
