package com.capston.bellywelly.domain.report.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietReportResponseDto {

	private WeekDto week;
	private String feedback;
	private List<MealListDto> best;
	private List<MealListDto> worst;
}
