package com.example.reframe.service.enroll;

import com.example.reframe.repository.enroll.ProductAppRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductApplicationQueryService {

    private final ProductAppRepository repo;

    public ProductApplicationQueryService(ProductAppRepository repo) {
        this.repo = repo;
    }

    public long countApplicationsByProductId(Long productId) {
        return repo.countApplicationsByProductId(productId);
    }

    public long countDistinctUsersByProductId(Long productId) {
        return repo.countDistinctUsersByProductId(productId);
    }

    public Map<String, Long> countByStatus(Long productId) {
        Map<String, Long> map = new LinkedHashMap<>();
        List<Object[]> rows = repo.groupByStatus(productId);
        for (Object[] row : rows) {
            String status = Objects.toString(row[0], "UNKNOWN");
            Long cnt = ((Number) row[1]).longValue();
            map.put(status, cnt);
        }
        return map;
    }
}
