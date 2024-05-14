package com.capston.bellywelly.domain.recommendation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capston.bellywelly.domain.recommendation.dto.GptRecRequestDto;
import com.capston.bellywelly.domain.recommendation.dto.GptRecResponseDto;
import com.capston.bellywelly.domain.record.entity.Diet;
import com.capston.bellywelly.domain.record.service.DietService;
import com.capston.bellywelly.domain.record.service.MealService;
import com.capston.bellywelly.global.feign.client.GptRecClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final GptRecClient gptRecClient;
	private final DietService dietService;
	private final MealService mealService;

	public GptRecResponseDto getRecommendation() {
		return gptRecClient.getRecommendedDiet(new GptRecRequestDto(getTodayMealList()));
	}

	public List<String> getTodayMealList() {
		List<Diet> todayDietList = dietService.findTodayDietList();
		if (todayDietList.isEmpty()) {
			return null;
		}
		List<String> todayMealList = new ArrayList<>();
		for (Diet diet : todayDietList) {
			List<String> mealNameList = mealService.findMealnameList(diet);
			todayMealList.addAll(mealNameList);
		}
		return todayMealList;
	}
}
