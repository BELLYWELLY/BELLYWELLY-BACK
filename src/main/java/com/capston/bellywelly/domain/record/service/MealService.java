package com.capston.bellywelly.domain.record.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capston.bellywelly.domain.record.dto.FodmapListDto;
import com.capston.bellywelly.domain.record.dto.NutrientDto;
import com.capston.bellywelly.domain.record.dto.NutrientValueGraphDto;
import com.capston.bellywelly.domain.record.entity.Diet;
import com.capston.bellywelly.domain.record.entity.DietMeal;
import com.capston.bellywelly.domain.record.entity.Meal;
import com.capston.bellywelly.domain.record.repository.DietMealRepository;
import com.capston.bellywelly.domain.record.repository.MealRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealService {

	private final MealRepository mealRepository;
	private final DietMealRepository dietMealRepository;

	public List<Meal> findMealList(List<String> mealNameList) {
		return mealNameList.stream()
			.map(mealRepository::findByMealName)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.toList();
	}

	public FodmapListDto findLowOrHighFodmap(Diet diet) {
		List<DietMeal> dietMealList = dietMealRepository.findAllByDiet(diet);
		List<String> lowFodmapList = new ArrayList<>();
		List<String> highFodmapList = new ArrayList<>();
		for (DietMeal dietMeal : dietMealList) {
			Meal meal = dietMeal.getMeal();
			if (meal == null) {
				continue;
			}
			if (meal.getIsLowFodmap()) {
				lowFodmapList.add(meal.getMealName());
			} else {
				highFodmapList.add(meal.getMealName());
			}
		}

		diet.updateLowFodmapCount(lowFodmapList.size());
		diet.updateHighFodmapCount(highFodmapList.size());

		return FodmapListDto.builder().lowFodmap(lowFodmapList).highFodmap(highFodmapList).build();
	}

	public NutrientDto sumNutrientComponent(Diet diet) {
		List<DietMeal> dietMealList = dietMealRepository.findAllByDiet(diet);
		Float totalFructose = (float)0;
		Float totalSucrose = (float)0;
		Float totalLactose = (float)0;
		Float totalMaltose = (float)0;
		Float totalFiber = (float)0;
		for (DietMeal dietMeal : dietMealList) {
			Meal meal = dietMeal.getMeal();
			if (meal == null) {
				continue;
			}
			totalFructose += meal.getFructose();
			totalSucrose += meal.getSucrose();
			totalLactose += meal.getLactose();
			totalMaltose += meal.getMaltose();
			totalFiber += meal.getFiber();
		}

		List<Integer> graphList = getNutrientRatio(totalFructose, totalSucrose, totalLactose, totalMaltose, totalFiber);
		return NutrientDto.builder()
			.fructose(NutrientValueGraphDto.builder().value(totalFructose).graph(graphList.get(0)).build())
			.sucrose(NutrientValueGraphDto.builder().value(totalSucrose).graph(graphList.get(1)).build())
			.lactose(NutrientValueGraphDto.builder().value(totalLactose).graph(graphList.get(2)).build())
			.maltose(NutrientValueGraphDto.builder().value(totalMaltose).graph(graphList.get(3)).build())
			.fiber(NutrientValueGraphDto.builder().value(totalFiber).graph(graphList.get(4)).build())
			.build();
	}

	public List<String> findMealnameList(Diet diet) {
		List<DietMeal> dietMealList = dietMealRepository.findAllByDiet(diet);
		// DietMeal에서 getMeal() 호출 시 null 반환 가능성 있음
		List<Meal> mealList = dietMealList.stream().map(DietMeal::getMeal).toList();
		return mealList.stream()
			.filter(Objects::nonNull)
			.map(Meal::getMealName).toList();
	}

	public List<Integer> getNutrientRatio(Float fructose, Float sucrose, Float lactose, Float maltose, Float fiber) {
		List<Float> nutrientList = Arrays.asList(fructose, sucrose, lactose, maltose, fiber);
		Float max = Collections.max(nutrientList);
		if (max <= 0) {
			return Arrays.asList(0, 0, 0, 0, 0);
		} else {
			return nutrientList.stream().map(nutrient -> (int)(nutrient / max * 100)).collect(Collectors.toList());
		}
	}

	public List<Boolean> findIsLowFodmapList(List<String> mealNameList) {
		List<Meal> mealList = findMealList(mealNameList);
		return mealList.stream()
			.filter(Objects::nonNull)
			.map(Meal::getIsLowFodmap).toList();
	}
}
