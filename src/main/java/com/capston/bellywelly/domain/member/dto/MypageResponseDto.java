package com.capston.bellywelly.domain.member.dto;

import com.capston.bellywelly.domain.report.dto.WeekDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MypageResponseDto {

	private String name;
	private WeekDto week;
}
