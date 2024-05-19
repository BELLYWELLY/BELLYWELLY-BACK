package com.capston.bellywelly.domain.report.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefecationStressGraphDto {

	private List<Integer> defecation;
	private List<Integer> stress;
}
