package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer>{
	
	@Query(value = "select u from AppUser u where u.username =?1 and u.password =?2")
	Optional<AppUser> login(String username, String password);
	Optional<AppUser> findByToken(String token);
	
}
