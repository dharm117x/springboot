package com.example.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.LanguageEntity;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

	LanguageEntity findByKeyAndLocale(String key, String locale);
}
