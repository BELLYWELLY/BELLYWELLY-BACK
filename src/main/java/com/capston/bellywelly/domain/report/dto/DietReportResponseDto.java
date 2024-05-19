package com.capston.bellywelly.domain.report.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietReportResponseDto {

	private Integer year;
	private Integer month;
	private Integer week;
	private String feedback;
	private List<MealListDto> best;
	private List<MealListDto> worst;
}
