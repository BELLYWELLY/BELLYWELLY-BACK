package com.capston.bellywelly.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekDto {

	private Integer year;
	private Integer month;
	private Integer week;
}
