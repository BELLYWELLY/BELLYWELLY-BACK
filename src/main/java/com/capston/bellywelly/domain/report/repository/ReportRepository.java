package com.capston.bellywelly.domain.report.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	Optional<Report> findByMemberAndYearAndMonthAndWeek(Member member, Integer year, Integer month, Integer week);
}
