package com.example.reframe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.Userr;

public interface UserrRepository extends JpaRepository<Userr,String>{

	Userr findByUsername(String username);
}
