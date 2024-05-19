package com.capston.bellywelly.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealListDto {

	private String mealName;
	private String description;
}
