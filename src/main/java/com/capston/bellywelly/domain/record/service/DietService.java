package com.capston.bellywelly.domain.record.service;

import static com.capston.bellywelly.global.SecurityUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.record.dto.DietInfoDto;
import com.capston.bellywelly.domain.record.dto.DietRecordRequestDto;
import com.capston.bellywelly.domain.record.entity.Diet;
import com.capston.bellywelly.domain.record.entity.DietMeal;
import com.capston.bellywelly.domain.record.entity.Mealtime;
import com.capston.bellywelly.domain.record.repository.DietMealRepository;
import com.capston.bellywelly.domain.record.repository.DietRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

	private final DietRepository dietRepository;
	private final DietMealRepository dietMealRepository;
	private final MealService mealService;

	public Diet createDiet(Member member, DietRecordRequestDto requestDto) {
		Mealtime mealtime = Mealtime.from(requestDto.getMealtime());
		if (!mealtime.equals(Mealtime.OTHER) // mealtime이 아침 or 점심 or 저녁이고
			&& dietRepository.existsByMemberAndCreatedDateBetweenAndMealtime(
			member, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59, 999999999),
			Mealtime.from(requestDto.getMealtime())) // 이미 해당 시간에 기록했을 때
		) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"해당 시간에 식단 기록이 이미 존재합니다. mealtime: " + requestDto.getMealtime());
		}

		Diet diet = dietRepository.save( // Diet 생성
			Diet.builder()
				.member(member)
				.image(requestDto.getImage())
				.mealtime(Mealtime.from(requestDto.getMealtime()))
				.build());

		mealService.findMealList(requestDto.getMeal()) // mealnameList로부터 mealList를 찾아오고
			.forEach(meal -> dietMealRepository.save(
				DietMeal.builder().diet(diet).meal(meal).build())); // mealList와 diet를 DietMeal에 삽입

		return diet;
	}

	public Diet findDiet(LocalDate date, int value) {
		// createdDate의 날짜가 date인 diet list 생성
		List<Diet> dietListByDate = dietRepository.findAllByMemberAndCreatedDateBetween(
			getCurrentUser(),
			date.atStartOfDay(),
			date.atTime(23, 59, 59, 999999999));
		// list가 비었으면 에러 발생
		if (dietListByDate.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				"해당 날짜에 식단 기록이 존재하지 않습니다. date: " + date);
		}
		// list 존재할 때
		if (value >= 4) {
			// value가 4 이상이면, mealtime=OTHER(기타)
			dietListByDate = dietListByDate.stream().filter(
				d -> d.getMealtime().equals(Mealtime.OTHER)
			).collect(Collectors.toList());
			// createdDate 오름차순으로 정렬
			dietListByDate.sort(Comparator.comparing(Diet::getCreatedDate));
			// 알맞은 인덱스의 diet 반환
			Diet diet = dietListByDate.get(value - 4);
			// diet가 null이면 not found error 발생
			if (diet == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"해당 시간에 식단 기록이 존재하지 않습니다. mealtime: " + value);
			}
			return diet;
		} else if (value >= 1) {
			// value가 1~3이면, 각 mealtime에 맞는 diet 반환
			return dietListByDate.stream()
				.filter(d -> d.getMealtime().equals(Mealtime.fromValue(value)))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
					"해당 시간에 식단 기록이 존재하지 않습니다. mealtime: " + value
						+ "(" + Mealtime.fromValue(value).name() + ")"));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mealtime은 1 이상이어야 합니다.");
		}
	}

	public DietInfoDto getDailyDietInfo(Member member, LocalDateTime startOfToday, LocalDateTime endOfToday) {
		List<Diet> dietList = dietRepository.findAllByMemberAndCreatedDateBetween(member, startOfToday, endOfToday);

		int lowFodmapCount = dietList.stream().mapToInt(Diet::getLowFodmapCount).sum();
		int highFodmapCount = dietList.stream().mapToInt(Diet::getHighFodmapCount).sum();
		int lowFodmapRatio = Math.round(((float)lowFodmapCount / (lowFodmapCount + highFodmapCount)) * 100);
		int highFodmapRatio = Math.round(((float)highFodmapCount / (lowFodmapCount + highFodmapCount)) * 100);

		return DietInfoDto.builder()
			.comment(getDailyDietComment(lowFodmapCount, highFodmapCount))
			.lowFodmapRatio(lowFodmapRatio)
			.highFodmapRatio((lowFodmapRatio + highFodmapRatio > 100) ? (highFodmapRatio - 1) : highFodmapRatio)
			.hasDiet(!dietList.isEmpty())
			.hasBreakfast(dietList.stream().anyMatch(diet -> diet.getMealtime().equals(Mealtime.BREAKFAST)))
			.hasLunch(dietList.stream().anyMatch(diet -> diet.getMealtime().equals(Mealtime.LUNCH)))
			.hasDinner(dietList.stream().anyMatch(diet -> diet.getMealtime().equals(Mealtime.DINNER)))
			.hasOther(dietList.stream().anyMatch(diet -> diet.getMealtime().equals(Mealtime.OTHER)))
			.build();
	}

	public String getDailyDietComment(Integer lowFodmapCount, Integer highFodmapCount) {
		if (lowFodmapCount.equals(highFodmapCount)) {
			return "soso";
		}
		return (lowFodmapCount > highFodmapCount) ? "good" : "bad";
	}

	public List<Diet> findDietListOfDay(LocalDate date) {
		Member member = getCurrentUser();
		return dietRepository.findAllByMemberAndCreatedDateBetween(member, date.atStartOfDay(),
			date.atTime(23, 59, 59, 999999999));
	}
}
