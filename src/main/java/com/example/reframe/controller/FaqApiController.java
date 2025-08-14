package com.example.reframe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.entity.Faq;
import com.example.reframe.repository.FaqsRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqApiController {

    private final FaqsRepository faqsRepository;

    @GetMapping
    public Page<Faq> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "faqId,asc") String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "status", required = false) String status
    ) {
        String[] parts = sort.split(",");
        String sortProp = parts.length > 0 ? parts[0] : "faqId";
        Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1]))
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortProp));

        Specification<Faq> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (search != null && !search.isBlank()) {
                String like = "%" + search.trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("question")), like));
            }
            // ★ 조건이 없으면 TRUE 반환(전체)
            if (predicates.isEmpty()) return cb.conjunction();
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return faqsRepository.findAll(spec, pageable);
    }

    @GetMapping("/{id}")
    public Faq detail(@PathVariable(name = "id") Integer id) {
        return faqsRepository.findById(id).orElseThrow();
    }

    @GetMapping("/categories")
    public List<String> categories() {
        return faqsRepository.findAll().stream()
                .map(Faq::getCategory)
                .filter(s -> s != null && !s.isBlank())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
