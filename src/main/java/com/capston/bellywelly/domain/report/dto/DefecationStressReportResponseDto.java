package com.capston.bellywelly.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefecationStressReportResponseDto {

	private WeekDto week;
	private String feedback;
	private DefecationStressGraphDto graphDto;
	private String defecationAnalysis;
	private String stressAnalysis;
}
