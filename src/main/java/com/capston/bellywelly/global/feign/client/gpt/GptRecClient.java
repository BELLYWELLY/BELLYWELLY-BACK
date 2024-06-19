package com.capston.bellywelly.global.feign.client.gpt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capston.bellywelly.global.feign.dto.gpt.GptDietRequestDto;
import com.capston.bellywelly.global.feign.dto.gpt.GptResponseDto;

@FeignClient(name = "chatGptRecommendation", url = "https://model.bellywelly.kro.kr/recommend")
public interface GptRecClient {

	@PostMapping
	GptResponseDto getRecommendedDiet(@RequestBody GptDietRequestDto requestDto);
}
