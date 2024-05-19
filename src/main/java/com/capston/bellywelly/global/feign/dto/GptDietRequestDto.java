package com.capston.bellywelly.global.feign.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GptDietRequestDto {

	private List<String> content;

	public GptDietRequestDto(List<String> mealList) {
		this.content = mealList;
	}
}
