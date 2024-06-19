package com.capston.bellywelly.global.feign.client.gpt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capston.bellywelly.global.feign.dto.gpt.GptDefecationStressRequestDto;
import com.capston.bellywelly.global.feign.dto.gpt.GptDietRequestDto;
import com.capston.bellywelly.global.feign.dto.gpt.GptFeedbackRequestDto;
import com.capston.bellywelly.global.feign.dto.gpt.GptFoodReportResponseDto;
import com.capston.bellywelly.global.feign.dto.gpt.GptResponseDto;

@FeignClient(name = "chatGptReport", url = "https://model.bellywelly.kro.kr/report")
public interface GptReportClient {

	@PostMapping
	GptResponseDto getReportFeedback(@RequestBody GptFeedbackRequestDto requestDto);

	@PostMapping("/defecation")
	GptResponseDto getDefecationAnalysis(@RequestBody GptDefecationStressRequestDto requestDto);

	@PostMapping("/stress")
	GptResponseDto getStressAnalysis(@RequestBody GptDefecationStressRequestDto requestDto);

	@PostMapping("/food")
	GptFoodReportResponseDto getDietAnalysis(@RequestBody GptDietRequestDto requestDto);
}
