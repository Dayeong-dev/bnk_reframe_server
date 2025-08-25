package com.example.reframe.controller.fcm;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.reframe.dto.fcm.FcmTemplateDto;
import com.example.reframe.entity.fcm.FcmTemplate;
import com.example.reframe.repository.fcm.FcmTemplateRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/fcm/templates")
@RequiredArgsConstructor
public class FcmTemplateController {

	private final FcmTemplateRepository repository;

    @GetMapping
    public List<FcmTemplate> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public FcmTemplate create(@RequestBody FcmTemplateDto dto) {
    	System.out.println(".........Template dto ..." + dto);
        FcmTemplate entity = FcmTemplate.builder()
                .groupCode(dto.getGroupCode())
                .title(dto.getTitle())
                .body(dto.getBody())
                .cron(dto.getCron())
                .active(dto.isActive())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return repository.save(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FcmTemplate> get(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FcmTemplate> update(@PathVariable("id") Long id,
    										  @RequestBody FcmTemplateDto dto) {
    	FcmTemplate entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));

        entity.setGroupCode(dto.getGroupCode());
        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setCron(dto.getCron());
        entity.setActive(dto.isActive());
        entity.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/active")
    public List<FcmTemplate> getActiveTemplates() {
        return repository.findByActiveTrueOrderByUpdatedAtDesc();
    }
}