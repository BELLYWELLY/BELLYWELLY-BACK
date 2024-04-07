package com.capston.bellywelly.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capston.bellywelly.domain.report.service.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "레포트", description = "레포트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createReport() {
		reportService.createReport();
	}
}