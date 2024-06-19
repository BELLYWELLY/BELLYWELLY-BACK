package com.capston.bellywelly.global.feign.dto;

import lombok.Getter;

@Getter
public class GptFoodReportResponseDto {

	private int status;
	private DietBestWorstDto data;
}
