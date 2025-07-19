package com.example.reframe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.reframe.dto.DocumentDTO;
import com.example.reframe.entity.Document;
import com.example.reframe.repository.DocumentRepository;
import com.example.reframe.util.DocumentMapper;

@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
	private final DocumentMapper documentMapper = new DocumentMapper();
	
	public DocumentService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	public List<DocumentDTO> getTermList(String search) {
		List<Document> result;
		
		if(search != null) {
			result = documentRepository.findByTitleContainingAndDocumentType(search, "T");
		}	
		else {
			result = documentRepository.findAllByDocumentType("T");
		}
		
		List<DocumentDTO> termList = new ArrayList<>();
		
		for(Document doc : result) {
			termList.add(documentMapper.toDTO(doc));
		}
		
		return termList;
	}
	
	public List<DocumentDTO> getManualList(String search) {
		List<Document> result; 
		
		if(search != null) {
			result = documentRepository.findByTitleContainingAndDocumentType(search, "M");
		}	
		else {
			result = documentRepository.findAllByDocumentType("M");
		}
		
		List<DocumentDTO> manualList = new ArrayList<>();
		
		for(Document doc : result) {
			manualList.add(documentMapper.toDTO(doc));
		}
		
		return manualList;
	}

	public DocumentDTO getById(Integer documentId) {
		Optional<Document> optional_document = documentRepository.findById(documentId);
		
		if(optional_document.isEmpty()) {
			return null;
		}
		
		return documentMapper.toDTO(optional_document.get());
	}

	public DocumentDTO setDocument(DocumentDTO documentDTO) throws Exception {
		if(documentDTO == null || documentDTO.getFilename() == null) {
			throw new Exception("등록할 문서가 없습니다. ");
		}
		
		Document result = documentRepository.save(documentMapper.toEntity(documentDTO));
		
		return documentMapper.toDTO(result);
	}
}
