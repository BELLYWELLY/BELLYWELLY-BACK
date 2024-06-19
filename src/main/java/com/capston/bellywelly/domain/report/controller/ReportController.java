package com.capston.bellywelly.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capston.bellywelly.domain.report.dto.DefecationStressReportResponseDto;
import com.capston.bellywelly.domain.report.dto.DietReportResponseDto;
import com.capston.bellywelly.domain.report.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "레포트", description = "레포트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;

	@Operation(summary = "식단 레포트 조회")
	@GetMapping("/diet")
	@ResponseStatus(HttpStatus.OK)
	public DietReportResponseDto findDietReportByWeek(@RequestParam Integer year, @RequestParam Integer month,
		@RequestParam Integer week) {
		return reportService.findDietReport(year, month, week);
	}

	@Operation(summary = "배변/스트레스 레포트 조회")
	@GetMapping("/defecation")
	@ResponseStatus(HttpStatus.OK)
	public DefecationStressReportResponseDto findDefecationStressReportByWeek(
		@RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer week) {
		return reportService.findDefecationStressReport(year, month, week);
	}
}
