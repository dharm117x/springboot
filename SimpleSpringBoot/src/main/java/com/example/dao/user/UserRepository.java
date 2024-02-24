package com.example.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}