package com.example.reframe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.CardSubcategory;

public interface CardSubcategoryRepository extends JpaRepository<CardSubcategory, Long> {

	Optional<CardSubcategory> findByName(String name);

}
