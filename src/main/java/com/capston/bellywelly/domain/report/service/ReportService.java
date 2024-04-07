package com.capston.bellywelly.domain.report.service;

import static com.capston.bellywelly.global.SecurityUtil.*;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.report.repository.ReportDefecationStressRepository;
import com.capston.bellywelly.domain.report.repository.ReportMealRepository;
import com.capston.bellywelly.domain.report.repository.ReportRepository;
import com.capston.bellywelly.global.feign.client.ChatgptReportClient;
import com.capston.bellywelly.global.feign.dto.chatgpt.ChatgptReportRequestDto;
import com.capston.bellywelly.global.feign.dto.chatgpt.ChatgptReportResponseDto;
import com.capston.bellywelly.global.feign.dto.chatgpt.MessageDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final ReportMealRepository reportMealRepository;
	private final ReportDefecationStressRepository reportDefecationStressRepository;
	private final ChatgptReportClient chatgptReportClient;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.secret-key}")
	private String openAiKey;

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

	public Integer getWeekNumber(LocalDate date) {  // date로부터 주차 수를 구하는 메서드
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		TemporalField weekOfMonth = weekFields.weekOfMonth();
		return date.get(weekOfMonth);
	}

	public ChatgptReportResponseDto getChatCompletion() {  // gpt 채팅 결과 받아오는 메서드
		return chatgptReportClient.getComment(
			"Bearer " + openAiKey,
			ChatgptReportRequestDto.builder()
				.model(model)
				.messages(List.of(MessageDto.builder()
					.role("user")
					.content("(임시) 일주일 식단, 배변, 스트레스 기록 데이터")
					.build()
				))
				.build());
	}
}