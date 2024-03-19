package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.UsernameNotFoundException;
import com.example.repository.UserRepository;
import com.example.user.entity.AppUser;

@Service
@Transactional
public class UserService {

	@Autowired 
	UserRepository repository;

	@Transactional(readOnly = true)
	public List<AppUser> getAllUsers() {

		return repository.findAll();	
	}

	public AppUser getByUsername(String name) {
		return repository.findByUsername(name).orElseThrow();
	}
		
	public Optional<AppUser> getByToken(String token) {
		Optional<AppUser> appUser = repository.findByToken(token);
		
		return appUser;
	}
	
	public AppUser getUserById(Integer id)  {
		AppUser user = repository.findById(id).orElse(null);
		if(null == user) {
			throw new UsernameNotFoundException("User not found for id::"+id);
		}else {
			return user;
		}
	}

	public void deleteUserById(Integer id) {
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AppUser save(AppUser user) {
		return repository.save(user);
	}
	
	@Transactional
	public void saveAll(List<AppUser> users) {
		repository.saveAll(users);
	}

}
