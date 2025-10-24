package com.example.reframe.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyVisitDTO {

	  private String date;
	  private long count;
}
