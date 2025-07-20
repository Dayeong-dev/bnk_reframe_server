package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

	List<Document> findAllByDocumentType(String string);

	List<Document> findByTitleContainingAndDocumentType(String search, String string);
	
}
