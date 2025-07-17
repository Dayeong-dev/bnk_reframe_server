package com.example.reframe.util;

import com.example.reframe.dto.DocumentDTO;
import com.example.reframe.entity.Document;

public class DocumentMapper {
	
	public Document toEntity(DocumentDTO documentDTO) {
		Document document = new Document();
		
		document.setDocumentId(documentDTO.getDocumentId());
		document.setTitle(documentDTO.getTitle());
		document.setFilename(documentDTO.getFilename());
		document.setProductType(documentDTO.getProductType());
		document.setDocumentType(documentDTO.getDocumentType());
		
		return document;
	}
	
	public DocumentDTO toDTO(Document document) {
		DocumentDTO documentDTO = new DocumentDTO();
		
		documentDTO.setDocumentId(document.getDocumentId());
		documentDTO.setTitle(document.getTitle());
		documentDTO.setFilename(document.getFilename());
		documentDTO.setProductType(document.getProductType());
		documentDTO.setDocumentType(document.getDocumentType());
		
		return documentDTO;
	}

}
