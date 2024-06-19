package com.capston.bellywelly.global.feign.dto.gpt;

import java.util.List;

import lombok.Getter;

@Getter
public class DietBestWorstDto {
	private List<FoodNameDescDto> best;
	private List<FoodNameDescDto> worst;
}
