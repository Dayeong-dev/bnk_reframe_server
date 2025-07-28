package com.example.reframe.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.DocumentDTO;
import com.example.reframe.entity.Document;
import com.example.reframe.repository.DocumentRepository;
import com.example.reframe.util.DocumentMapper;

@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
	private final DocumentMapper documentMapper = new DocumentMapper();
	
	private String TERM_PATH = System.getProperty("user.dir") + "/uploads/terms/";
	private String MANUAL_PATH = System.getProperty("user.dir") + "/uploads/manuals/";
	
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
	
	public List<String> getImages(Integer documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new IllegalArgumentException(documentId + "번 문서를 찾을 수 없습니다. "));
		
		String path = document.getDocumentType().equals("T") ? TERM_PATH : MANUAL_PATH;
		String filename = document.getFilename();
		
		File dir = new File(path);
		File[] jpgFiles = dir.listFiles((d, name) -> name.startsWith(filename) && name.endsWith(".jpg"));

	    List<String> imagePaths = Arrays.stream(jpgFiles)
	        .sorted(Comparator.comparing(File::getName))
	        .map(file -> "/uploads/" + (document.getDocumentType().equals("T") ? "terms" : "manuals") + "/" + file.getName()) // 웹 접근 경로
	        .toList();

	    return imagePaths;
	}
}
