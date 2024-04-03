package com.capston.bellywelly.domain.record.service;

import static com.capston.bellywelly.global.SecurityUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.record.dto.DefecationRequestDto;
import com.capston.bellywelly.domain.record.dto.DietRecordRequestDto;
import com.capston.bellywelly.domain.record.dto.DietRecordResponseDto;
import com.capston.bellywelly.domain.record.dto.HomeResponseDto;
import com.capston.bellywelly.domain.record.dto.StressRequestDto;
import com.capston.bellywelly.domain.record.entity.Defecation;
import com.capston.bellywelly.domain.record.entity.Diet;
import com.capston.bellywelly.domain.record.entity.DietMeal;
import com.capston.bellywelly.domain.record.entity.StoolColor;
import com.capston.bellywelly.domain.record.entity.StoolScale;
import com.capston.bellywelly.domain.record.entity.Stress;
import com.capston.bellywelly.domain.record.repository.DefecationRepository;
import com.capston.bellywelly.domain.record.repository.DietMealRepository;
import com.capston.bellywelly.domain.record.repository.StressRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

	private final DietService dietService;
	private final DietMealRepository dietMealRepository;
	private final MealService mealService;
	private final StressService stressService;
	private final StressRepository stressRepository;
	private final DefecationRepository defecationRepository;
	private final DefecationService defecationService;

	public DietRecordResponseDto createDietRecord(DietRecordRequestDto requestDto) {
		Member member = getCurrentUser();

		Diet diet = dietService.createDiet(member, requestDto);

		mealService.findMealList(requestDto.getMeal())
			.forEach(meal -> dietMealRepository.save(DietMeal.builder().diet(diet).meal(meal).build()));

		return DietRecordResponseDto.builder()
			.diet(diet)
			.meal(requestDto.getMeal())
			.fodmapList(mealService.findLowOrHighFodmap(diet))
			.nutrient(mealService.sumNutrientComponent(diet))
			.build();
	}

	public void createStressRecord(StressRequestDto requestDto) {
		Member member = getCurrentUser();
		stressRepository.save(
			Stress.builder()
				.member(member)
				.degree(requestDto.getStress())
				.build()
		);
	}

	public void createDefecationRecord(DefecationRequestDto requestDto) {
		Member member = getCurrentUser();
		StoolScale form = StoolScale.from(requestDto.getForm());
		Integer urgency = requestDto.getUrgency();
		StoolColor color = StoolColor.from(requestDto.getColor());
		Integer satisfaction = requestDto.getSatisfaction();
		Integer duration = requestDto.getDuration();

		defecationRepository.save(
			Defecation.builder()
				.member(member)
				.form(form)
				.urgency(urgency)
				.color(color)
				.satisfaction(satisfaction)
				.duration(duration)
				.score(defecationService.calculateScore(form, urgency, color, satisfaction, duration))
				.build()
		);
	}

	public DietRecordResponseDto findDietRecord(LocalDate date, int mealtime) {
		Diet diet = dietService.findDiet(date, mealtime);

		return DietRecordResponseDto.builder()
			.diet(diet)
			.meal(mealService.findMealnameList(diet))
			.fodmapList(mealService.findLowOrHighFodmap(diet))
			.nutrient(mealService.sumNutrientComponent(diet))
			.build();
	}

	public HomeResponseDto getDailyRecord() {
		Member member = getCurrentUser();
		LocalDate today = LocalDate.now();
		LocalDateTime startOfToady = today.atTime(0, 0, 0);
		LocalDateTime endOfToady = today.atTime(23, 59, 59, 999999999);

		return HomeResponseDto.builder()
			.diet(dietService.getDailyDietInfo(member, startOfToady, endOfToady))
			.defecation(defecationService.getDailyDefecationInfo(member, startOfToady, endOfToady))
			.stress(stressService.getStressInfoInThisWeek(member, today))
			.build();
	}
}
