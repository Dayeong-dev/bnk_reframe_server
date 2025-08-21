package com.example.reframe.util;

import com.example.reframe.dto.DocumentDTO;
import com.example.reframe.entity.Document;

public class DocumentMapper {
	
	public Document toEntity(DocumentDTO documentDTO) {
		if(documentDTO == null) {
			return null;
		}
		
		Document document = new Document();
		
		document.setDocumentId(document.getDocumentId());
		document.setTitle(documentDTO.getTitle());
		document.setFilename(documentDTO.getFilename());
		document.setProductType(documentDTO.getProductType());
		document.setDocumentType(documentDTO.getDocumentType());
		document.setRegdate(documentDTO.getRegDate());
		document.setModDate(documentDTO.getModDate());
		
		return document;
	}
	
	public DocumentDTO toDTO(Document document) {
		if(document == null) {
			return null;
		}
		
		DocumentDTO documentDTO = new DocumentDTO();
		
		documentDTO.setDocumentId(document.getDocumentId());
		documentDTO.setTitle(document.getTitle());
		documentDTO.setFilename(document.getFilename());
		documentDTO.setProductType(document.getProductType());
		documentDTO.setDocumentType(document.getDocumentType());
		documentDTO.setRegDate(document.getRegdate());
		documentDTO.setModDate(document.getModDate());
		
		return documentDTO;
	}
}
