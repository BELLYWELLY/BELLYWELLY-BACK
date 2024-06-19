package com.capston.bellywelly.domain.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capston.bellywelly.domain.recommendation.service.RecommendationService;
import com.capston.bellywelly.global.feign.dto.gpt.GptResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "추천", description = "추천 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendations")
public class RecommendationController {

	private final RecommendationService recommendationService;

	@Operation(summary = "식단 추천")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GptResponseDto getDietRecommendation() {
		return recommendationService.getRecommendation();
	}
}
