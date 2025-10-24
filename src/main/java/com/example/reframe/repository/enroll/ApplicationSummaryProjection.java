package com.example.reframe.repository.enroll;

public interface ApplicationSummaryProjection {
    Long getTotal();
    Long getInProgress();
    Long getCompleted();
    Long getCanceled();

}

