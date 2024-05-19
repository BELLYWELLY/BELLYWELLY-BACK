package com.capston.bellywelly.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capston.bellywelly.domain.report.entity.Report;
import com.capston.bellywelly.domain.report.entity.ReportMeal;

public interface ReportMealRepository extends JpaRepository<ReportMeal, Long> {

	List<ReportMeal> findAllByReport(Report report);
}
