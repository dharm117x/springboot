package com.example.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.AppSpringConfig;
import com.example.dto.UserDto;
import com.example.entity.user.Address;
import com.example.entity.user.AppUser;
import com.example.entity.user.Book;
import com.example.entity.user.Car;
import com.example.entity.user.Name;
import com.example.entity.user.UserType;
import com.example.service.UserService;

import net.sf.ehcache.CacheManager;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AppSpringConfig config;

	@GetMapping("/users/id/{userId}")
	public AppUser getUserById(@PathVariable Integer userId) {
		List<CacheManager> list = CacheManager.ALL_CACHE_MANAGERS;
		CacheManager manager = list.get(0);
		System.out.println("CS:: "+manager.getCache("myEhCache").getSize());
		return userService.getUserById(userId);
	}

	@GetMapping("/users/name/{name}")
	public AppUser getUserByName(@PathVariable String name) {
		Session session = config.getSession();
		System.out.println("Session:: "+session);
		return userService.getByUsername(name);
	}

	@GetMapping("/users")
	public List<AppUser> allUsers() {
		List<AppUser> users = userService.getAllUsers();
		users.forEach(u->{
			u.getBooks().forEach(b->{System.out.println(b);});
			u.getCars().forEach(c->{System.out.println(c);});
		});
		
		return users;
	}

	@PostMapping(path = "/adduser")
	public AppUser saveUser(@RequestBody @Valid UserDto dto) {
		AppUser user = new AppUser();
		Name name =  new Name(dto.getFirsrtName(), dto.getLastName());
		user.setDob(dto.getDob());
		user.setName(name);
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setToken( UUID.randomUUID().toString());
	
		Address address = new Address();
		address.setCity("PUNE");
		address.setCode("12345");
		address.setStreat("TCS circle");
		user.setAddress(address );
		
		user.setCars(Arrays.asList(new Car()));
		HashSet<Book> set = new HashSet<>();
		set.add(new Book());
		
		user.setBooks(set);
		user.setType(UserType.FULL);
		return userService.save(user );
	}
}
