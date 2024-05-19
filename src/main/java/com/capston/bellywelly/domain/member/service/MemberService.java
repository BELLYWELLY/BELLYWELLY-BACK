package com.capston.bellywelly.domain.member.service;

import static com.capston.bellywelly.global.SecurityUtil.*;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.capston.bellywelly.domain.member.dto.MypageResponseDto;
import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.report.dto.WeekDto;
import com.capston.bellywelly.domain.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final ReportService reportService;

	public MypageResponseDto findMemberAndWeekInfo() {
		Member member = getCurrentUser();

		LocalDate today = LocalDate.now();
		Integer year = today.getYear();
		Integer month = today.getMonthValue();
		Integer week = reportService.getWeekNumber(today);

		return MypageResponseDto.builder()
			.name(member.getName())
			.week(WeekDto.builder().year(year).month(month).week(week).build())
			.build();
	}
}
