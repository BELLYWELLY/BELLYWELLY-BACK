package com.capston.bellywelly.domain.report.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ReportDefecationStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportDefecationStressId;

	@ManyToOne
	@JoinColumn(name = "report_id")
	private Report report;

	@Column(nullable = false)
	private Integer defecationScore;

	@Column(nullable = false)
	private Integer stressDegree;

	@Column(nullable = false, length = 16)
	private String dayOfWeek;

	@Builder
	public ReportDefecationStress(Report report, Integer defecationScore, Integer stressDegree, String dayOfWeek) {
		this.report = report;
		this.defecationScore = defecationScore;
		this.stressDegree = stressDegree;
		this.dayOfWeek = dayOfWeek;
	}
}
