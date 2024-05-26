package com.capston.bellywelly.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capston.bellywelly.global.feign.dto.GptFeedbackRequestDto;
import com.capston.bellywelly.global.feign.dto.GptResponseDto;

@FeignClient(name = "chatGptReport", url = "https://model.bellywelly.kro.kr/report")
public interface GptReportClient {

	@PostMapping
	GptResponseDto getReportFeedback(@RequestBody GptFeedbackRequestDto requestDto);
}
