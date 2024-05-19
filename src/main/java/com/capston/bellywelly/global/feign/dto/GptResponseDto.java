package com.capston.bellywelly.global.feign.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GptResponseDto {

	private int status;
	private List<String> data;
}
