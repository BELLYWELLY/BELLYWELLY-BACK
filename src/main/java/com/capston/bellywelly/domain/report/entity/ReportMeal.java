package com.capston.bellywelly.domain.report.entity;

import com.capston.bellywelly.domain.record.entity.Meal;

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
public class ReportMeal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportMealId;

	@ManyToOne
	@JoinColumn(name = "report_id")
	private Report report;

	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;

	@Column(nullable = false)
	private Boolean isBest;

	@Column(nullable = false)
	private String description;

	@Builder
	public ReportMeal(Report report, Meal meal, Boolean isBest, String description) {
		this.report = report;
		this.meal = meal;
		this.isBest = isBest;
		this.description = description;
	}
}
