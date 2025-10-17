package com.example.reframe.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.example.reframe.dto.DocumentDTO;
import com.example.reframe.service.DocumentService;

@Controller
@RequestMapping("/admin")
public class AdminDocumentController {

	private final DocumentService documentService;

	private String TERM_PATH = System.getProperty("user.dir") + "/uploads/terms/";
	private String MANUAL_PATH = System.getProperty("user.dir") + "/uploads/manuals/";

	public AdminDocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@GetMapping("/document/list")
	public String getDocumentList() {
		return "admin/document-list";
	}

	@GetMapping("/terms")
	public @ResponseBody ResponseEntity<List<DocumentDTO>> getTerms(@RequestParam(required = false, name = "search") String search) {
		List<DocumentDTO> termList = documentService.getTermList(search);

		return ResponseEntity.ok(termList);
	}

	@GetMapping("/manuals")
	public @ResponseBody ResponseEntity<List<DocumentDTO>> getManuals(@RequestParam(required = false, name = "search") String search) {
		List<DocumentDTO> manualList = documentService.getManualList(search);

		return ResponseEntity.ok(manualList);
	}

	@GetMapping("/document/download/{documentId}")
	public @ResponseBody ResponseEntity<Resource> downloadFile(@PathVariable("documentId") Integer documentId) {
	    DocumentDTO document = documentService.getById(documentId);

	    if(document == null) {
	    	return ResponseEntity.notFound().build();
	    }

	    String filename = document.getFilename() + ".pdf";
	    Path path;

	    switch (document.getDocumentType()) {
	        case "T" -> path = Paths.get(TERM_PATH + filename);
	        case "M" -> path = Paths.get(MANUAL_PATH + filename);
	        default -> {
	            return ResponseEntity.badRequest().build();
	        }
	    }

	    Resource resource = new FileSystemResource(path);

	    if (!resource.exists()) {
	        return ResponseEntity.notFound().build();
	    }


	    String encodedFileName = UriUtils.encode(filename, StandardCharsets.UTF_8);

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" + encodedFileName + "\"")
	            .body(resource);
	}

	@GetMapping("/document/form")
	public String documentForm(DocumentDTO document) {

		return "admin/document-form";
	}

	@PostMapping("/document/upload")
	public String uploadDocument(@RequestParam("file") MultipartFile file, DocumentDTO documentDTO, RedirectAttributes rttr) {

		try {
            if (!file.getOriginalFilename().endsWith(".pdf")) {
            	rttr.addFlashAttribute("msg", "PDF 파일만 업로드 가능합니다.");
                return "redirect:/document/form";
            }

            File uploadDir = new File(documentDTO.getDocumentType().equals("T") ? TERM_PATH : MANUAL_PATH);
            if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

            String fileName = System.currentTimeMillis() + "_" + documentDTO.getTitle().replaceAll("[^a-zA-Z0-9가-힣]", "_");
            File destination = new File(uploadDir, fileName + ".pdf");

            file.transferTo(destination);

            try(PDDocument document = PDDocument.load(destination)) {
	            int pageCount = document.getNumberOfPages();
				PDFRenderer pdfRenderer = new PDFRenderer(document);

				for(int i = 0; i < pageCount; i++) {
					BufferedImage imageObj = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
					File outputfile = new File(uploadDir + "/" + fileName + "_" + (i + 1) + ".jpg");

					ImageIO.write(imageObj, "jpg", outputfile);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

	        documentDTO.setFilename(fileName);
            documentService.setDocument(documentDTO);

    		return "redirect:/admin/document/list";

        } catch (Exception e) {
            rttr.addFlashAttribute("msg", "업로드 실패: " + e.getMessage());
            return "redirect:/document/form";
        }
	}
}
