package com.capston.bellywelly.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capston.bellywelly.domain.member.dto.LoginRequestDto;
import com.capston.bellywelly.domain.member.dto.LoginResponseDto;
import com.capston.bellywelly.domain.member.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
		return authService.login(requestDto.getCode());
	}
}