package com.example.reframe.repository;

public interface ProductCountsRow {
	Long   getProductId();
    String getName();
    String getPurpose();
    String getCategory();
    String getPeriod();
    Long   getTotalCount();
    Long   getInProgressCount();
    Long   getDoneCount();
    Long   getCanceledCount();
}
