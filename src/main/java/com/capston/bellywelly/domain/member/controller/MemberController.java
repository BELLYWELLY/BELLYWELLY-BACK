package com.capston.bellywelly.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capston.bellywelly.domain.member.dto.MypageResponseDto;
import com.capston.bellywelly.domain.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/mypage")
	@ResponseStatus(HttpStatus.OK)
	public MypageResponseDto getMypageInfo() {
		return memberService.findMemberAndWeekInfo();
	}
}
