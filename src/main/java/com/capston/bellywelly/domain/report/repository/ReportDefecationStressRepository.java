package com.capston.bellywelly.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capston.bellywelly.domain.report.entity.Report;
import com.capston.bellywelly.domain.report.entity.ReportDefecationStress;

public interface ReportDefecationStressRepository extends JpaRepository<ReportDefecationStress, Long> {

	List<ReportDefecationStress> findAllByReport(Report report);
}
