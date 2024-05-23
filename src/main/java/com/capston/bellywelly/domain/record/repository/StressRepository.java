package com.capston.bellywelly.domain.record.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.record.entity.Stress;

public interface StressRepository extends JpaRepository<Stress, Long> {

	List<Stress> findAllByMemberAndCreatedDateBetween(Member member, LocalDateTime time1, LocalDateTime time2);

	Boolean existsByMemberAndCreatedDateBetween(Member member, LocalDateTime time1, LocalDateTime time2);

	Optional<Stress> findByMemberAndCreatedDateBetween(Member member, LocalDateTime time1, LocalDateTime time2);
}
