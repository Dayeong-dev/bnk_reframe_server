package com.example.reframe.dto;

import lombok.Data;

@Data
public class AdminProductCountsDto {
    private long total;
    private long inProgress;
    private long done;
    private long cancel;

}