package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.Qna;

public interface QnaRepository extends JpaRepository<Qna, Integer>{

	List<Qna> findByUser_id(Long id);
	
}
