package com.capston.bellywelly.domain.record.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.record.dto.StressWeekDto;
import com.capston.bellywelly.domain.record.entity.Stress;
import com.capston.bellywelly.domain.record.repository.StressRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StressService {

	private final StressRepository stressRepository;

	public void createStress(Member member, Integer degree) {
		LocalDate today = LocalDate.now();
		if (stressRepository.existsByMemberAndCreatedDateBetween(member, today.atStartOfDay(),
			today.atTime(23, 59, 59, 999999999))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 오늘의 스트레스 기록이 존재합니다.");
		}
		stressRepository.save(
			Stress.builder()
				.member(member)
				.degree(degree)
				.build()
		);
	}

	public StressWeekDto getStressInfoInThisWeek(Member member, LocalDate date) {
		LocalDate startDateOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

		List<Stress> stressList = stressRepository.findAllByMemberAndCreatedDateBetween(member,
			startDateOfWeek.atStartOfDay(),
			startDateOfWeek.plusDays(6).atTime(23, 59, 59, 999999999)
		);

		Map<DayOfWeek, Integer> stressMap = stressList.stream()
			.collect(Collectors.toMap(
				stress -> stress.getCreatedDate().getDayOfWeek(),
				Stress::getDegree,
				(degree1, degree2) -> degree1));

		return StressWeekDto.builder()
			.sun(stressMap.getOrDefault(DayOfWeek.SUNDAY, null))
			.mon(stressMap.getOrDefault(DayOfWeek.MONDAY, null))
			.tue(stressMap.getOrDefault(DayOfWeek.TUESDAY, null))
			.wed(stressMap.getOrDefault(DayOfWeek.WEDNESDAY, null))
			.thu(stressMap.getOrDefault(DayOfWeek.THURSDAY, null))
			.fri(stressMap.getOrDefault(DayOfWeek.FRIDAY, null))
			.sat(stressMap.getOrDefault(DayOfWeek.SATURDAY, null))
			.build();
	}

	public Integer findStressDegree(Member member, LocalDate date) {
		return stressRepository.findByMemberAndCreatedDateBetween(member, date.atStartOfDay(),
				date.atTime(23, 59, 59, 999999999))
			.map(Stress::getDegree).orElse(0);
	}
}
