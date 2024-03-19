package com.example.repository;

import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.example.user.entity.AppUser;
import com.example.user.entity.Name;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

	@Query(value = "select u from AppUser u where u.username =?1 and u.password =?2")
	Optional<AppUser> login(String username, String password);

	Optional<AppUser> findByToken(String token);

	@QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true"),
			@QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
			@QueryHint(name = "org.hibernate.cacheRegion", value = "myEhCache") })
	@Query(value = "select u from AppUser u JOIN FETCH u.cars c JOIN FETCH u.books b where u.username =?1")
	Optional<AppUser> findByUsername(String username);

	Optional<AppUser> findByName(Name name);

}
