package com.capston.bellywelly.global.feign.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.capston.bellywelly.global.feign.dto.kakao.KakaoUserResponseDto;

@FeignClient(name = "kakaoUser", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoUserClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KakaoUserResponseDto getUserInfo(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
