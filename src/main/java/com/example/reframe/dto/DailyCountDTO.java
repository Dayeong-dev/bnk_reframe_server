package com.example.reframe.dto;
import java.time.LocalDate;

import lombok.Getter;

@Getter
public class DailyCountDTO {
    private final String date; // "YYYY-MM-DD"
    private final long count;

    public DailyCountDTO(LocalDate date, long count) {
        this.date = date.toString();
        this.count = count;
    }
}