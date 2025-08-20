package com.example.reframe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminProductListItemDto {
		
	private Long   productId;
    private String name;
    private String purpose;
    private String category;
    private Integer period;          // ← 엔티티 타입과 일치시켜 주세요 (Integer 가정)
    private Long   totalCount;       // count()
    private Long   inProgressCount;  // sum(case ...)
    private Long   completedCount;        // sum(case ...)
    private Long   canceledCount; 
    
    public AdminProductListItemDto(
            Long productId,
            String name,
            String purpose,
            String category,
            Integer period,          // ← 여기 타입이 핵심
            Long totalCount,
            Long inProgressCount,
            Long completedCount,
            Long canceledCount
    ) {
        this.productId = productId;
        this.name = name;
        this.purpose = purpose;
        this.category = category;
        this.period = period;
        this.totalCount = totalCount;
        this.inProgressCount = inProgressCount;
        this.completedCount = completedCount;
        this.canceledCount = canceledCount;
    }
    
    private static Integer parseIntOrNull(String s) {
        try { return s == null ? null : Integer.valueOf(s); }
        catch (NumberFormatException e) { return null; }
    }
}
