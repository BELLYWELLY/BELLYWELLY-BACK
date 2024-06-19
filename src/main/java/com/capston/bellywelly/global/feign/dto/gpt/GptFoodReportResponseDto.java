package com.capston.bellywelly.global.feign.dto.gpt;

import lombok.Getter;

@Getter
public class GptFoodReportResponseDto {

	private int status;
	private DietBestWorstDto data;
}
