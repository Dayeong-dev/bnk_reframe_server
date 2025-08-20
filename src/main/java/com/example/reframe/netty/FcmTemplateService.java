package com.example.reframe.netty;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reframe.entity.fcm.FcmTemplate;
import com.example.reframe.repository.fcm.FcmTemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmTemplateService {

	private final FcmTemplateRepository templateRepository;

    public List<FcmTemplate> findAll() {
        return templateRepository.findAll();
    }

    public void save(FcmTemplate template) {
        templateRepository.save(template);
    }

    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    public FcmTemplate findById(Long id) {
        return templateRepository.findById(id).orElse(null);
    }
}
