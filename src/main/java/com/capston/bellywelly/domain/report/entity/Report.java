package com.capston.bellywelly.domain.report.entity;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.global.BaseTimeEntity;

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
public class Report extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false)
	private Integer year;

	@Column(nullable = false)
	private Integer month;

	@Column(nullable = false)
	private Integer week;

	@Column(nullable = false)
	private String feedback;

	@Column(nullable = false)
	private String defecationAnalysis;

	@Column(nullable = false)
	private String stressAnalysis;

	@Builder
	public Report(Member member, Integer year, Integer month, Integer week,
		String feedback, String defecationAnalysis, String stressAnalysis) {
		this.year = year;
		this.member = member;
		this.month = month;
		this.week = week;
		this.feedback = feedback;
		this.defecationAnalysis = defecationAnalysis;
		this.stressAnalysis = stressAnalysis;
	}
}
