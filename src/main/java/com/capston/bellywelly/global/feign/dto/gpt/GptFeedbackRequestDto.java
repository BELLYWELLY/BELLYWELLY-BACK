package com.capston.bellywelly.global.feign.dto.gpt;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GptFeedbackRequestDto {

	private List<String> food;
	private List<Boolean> isLowFodmap;
	private List<Integer> defecation;
	private List<Integer> stress;
}
