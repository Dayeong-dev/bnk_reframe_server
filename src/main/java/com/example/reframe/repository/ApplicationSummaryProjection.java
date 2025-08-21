package com.example.reframe.repository;

public interface ApplicationSummaryProjection {
    Long getTotal();
    Long getInProgress();
    Long getCompleted();
    Long getCanceled();

}

