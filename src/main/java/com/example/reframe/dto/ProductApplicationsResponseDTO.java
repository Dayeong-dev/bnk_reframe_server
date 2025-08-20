package com.example.reframe.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductApplicationsResponseDTO {

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ProductInfo {
        private Long id;
        private String name;
        private String category;
        private BigDecimal minRate;
        private BigDecimal maxRate;
        private Integer period;
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class Counts {
        private long total;
        private long started;
        private long closed;
        private long canceled;
    }

    private ProductInfo product;
    private Counts counts;
    private long recent7d;
    private List<ApplicationRowDTO> applications;
}