package com.example.reframe.dto.fortune;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductBrief {
    private Long productId;
    private String name;
    private String category;
    private String summary;
}
