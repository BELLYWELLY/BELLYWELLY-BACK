package com.capston.bellywelly.global.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GptResponseDto {

	private int status;
	private String data;
}
