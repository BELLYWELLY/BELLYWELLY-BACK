package com.capston.bellywelly.domain.report.service;

import static com.capston.bellywelly.global.SecurityUtil.*;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.report.dto.DefecationStressGraphDto;
import com.capston.bellywelly.domain.report.dto.DefecationStressReportResponseDto;
import com.capston.bellywelly.domain.report.dto.DietReportResponseDto;
import com.capston.bellywelly.domain.report.dto.MealListDto;
import com.capston.bellywelly.domain.report.entity.Report;
import com.capston.bellywelly.domain.report.entity.ReportDefecationStress;
import com.capston.bellywelly.domain.report.entity.ReportMeal;
import com.capston.bellywelly.domain.report.repository.ReportDefecationStressRepository;
import com.capston.bellywelly.domain.report.repository.ReportMealRepository;
import com.capston.bellywelly.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final ReportMealRepository reportMealRepository;
	private final ReportDefecationStressRepository reportDefecationStressRepository;

	public void createReport() {  // 일요일 시작 시간에 주간 레포트 자동으로 생성

		// 생성할 레포트 주인, 년, 월, 주차 정보
		Member member = getCurrentUser();
		LocalDate yesterday = LocalDate.now().minusDays(1);
		Integer year = yesterday.getYear();
		Integer month = yesterday.getMonthValue();
		Integer week = getWeekNumber(yesterday);

		// gpt에 일주일간 식사/배변/스트레스 데이터 보내서 총평, best5/worst5 음식 리스트와 설명, 배변 분석, 스트레스 분석 받기

		// Report, ReportMeal, ReportDefecationStress 생성
	}

	public DietReportResponseDto findDietReport(Integer year, Integer month, Integer week) {
		Member member = getCurrentUser();
		Report report = reportRepository.findByMemberAndYearAndMonthAndWeek(member, year, month, week)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 레포트가 존재하지 않습니다."));
		List<ReportMeal> reportMealList = reportMealRepository.findAllByReport(report);

		List<MealListDto> bestMealList = reportMealList.stream()
			.filter(ReportMeal::getIsBest)
			.map(this::convertToMealListDto).toList();

		List<MealListDto> worstMealList = reportMealList.stream()
			.filter(reportMeal -> !reportMeal.getIsBest())
			.map(this::convertToMealListDto).toList();

		return DietReportResponseDto.builder()
			.year(report.getYear()).month(report.getMonth()).week(report.getWeek())
			.feedback(report.getFeedback()).best(bestMealList).worst(worstMealList).build();
	}

	public MealListDto convertToMealListDto(ReportMeal reportMeal) {
		return MealListDto.builder()
			.mealName(reportMeal.getMeal().getMealName())
			.description(reportMeal.getDescription())
			.build();
	}

	public Integer getWeekNumber(LocalDate date) {  // date로부터 주차 수를 구하는 메서드
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		TemporalField weekOfMonth = weekFields.weekOfMonth();
		return date.get(weekOfMonth);
	}

	public DefecationStressReportResponseDto findDefecationStressReport(Integer year, Integer month, Integer week) {
		Member member = getCurrentUser();
		Report report = reportRepository.findByMemberAndYearAndMonthAndWeek(member, year, month, week)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 레포트가 존재하지 않습니다."));
		List<ReportDefecationStress> reportDefecationStressList =
			reportDefecationStressRepository.findAllByReport(report);

		List<Integer> defecationScoreList = reportDefecationStressList.stream()
			.map(ReportDefecationStress::getDefecationScore).toList();

		List<Integer> stressDegreeList = reportDefecationStressList.stream()
			.map(ReportDefecationStress::getStressDegree).toList();

		return DefecationStressReportResponseDto.builder()
			.year(report.getYear()).month(report.getMonth()).week(report.getWeek())
			.feedback(report.getFeedback())
			.graphDto(DefecationStressGraphDto.builder()
				.defecation(defecationScoreList).stress(stressDegreeList).build())
			.defecationAnalysis(report.getDefecationAnalysis())
			.stressAnalysis(report.getStressAnalysis()).build();
	}
}
