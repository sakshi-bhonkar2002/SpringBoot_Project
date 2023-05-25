package com.fire.spring.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fire.spring.datajpa.model.firesystem;

public interface firesystemRepository extends JpaRepository<firesystem, Long> {
	List<firesystem> findByPublished(boolean published);
	List<firesystem> findByTitleContaining(String title);
}
