package com.example.reframe.entity.account;

public enum ProductType {
	DEMAND_FREE("입출금자유"),
	DEPOSIT("예금"),
	SAVINGS("적금");
	
	private final String display;
	
    ProductType(String display) {
    	this.display = display;
    }
    
    public String getDisplay() {
    	return display;
    }

    public static ProductType fromCategory(String category) {
        if(category == null) return null;
        
        String c = category == null ? null : category.replaceAll("\\s+", " ").trim();
        
        return switch (c) {
            case "예금" -> DEPOSIT;
            case "적금" -> SAVINGS;
            case "입출금", "입출금자유" -> DEMAND_FREE;
            default -> throw new IllegalArgumentException("해당 카테고리는 지원되지 않습니다: " + category);
        };
    }
}
