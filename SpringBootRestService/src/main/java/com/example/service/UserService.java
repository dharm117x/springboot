package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.AppUser;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired UserRepository repository;

	public String login(String username, String password) throws Exception{
		Optional<AppUser> optional = repository.login(username, password);
		if(optional.isPresent()) {
			String token = UUID.randomUUID().toString();
			AppUser user = optional.get();
			user.setToken(token);
			save(user);
			return token;
		}
		return "";
	}
	
	public Optional<User> getByToken(String token) {
		Optional<AppUser> optional = repository.findByToken(token);
		if(optional.isPresent()) {
			AppUser appUser = optional.get();
			User user = new User(appUser.getUsername(), appUser.getPassword(), AuthorityUtils.createAuthorityList("USER"));
			return Optional.of(user);
		}
		return Optional.empty();
	}
	
	public AppUser getUserByName(String name) {
		AppUser appUser = repository.findByName(name);
		if(appUser== null) {
			throw new UsernameNotFoundException("User not found for name: "+name);
		}
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

	public List<AppUser> getAllUser() {
		return repository.findAll();
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
	public AppUser save(AppUser user) {
		return repository.save(user);
	}
	
	public void saveAll(List<AppUser> users) {
		repository.saveAll(users);
	}
	
}
