package com.capston.bellywelly.domain.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.capston.bellywelly.domain.member.dto.LoginResponseDto;
import com.capston.bellywelly.domain.member.entity.Member;
import com.capston.bellywelly.domain.member.repository.MemberRepository;
import com.capston.bellywelly.global.feign.client.KakaoTokenClient;
import com.capston.bellywelly.global.feign.client.KakaoUserClient;
import com.capston.bellywelly.global.feign.dto.KakaoAccountDto;
import com.capston.bellywelly.global.feign.dto.KakaoTokenRequestDto;
import com.capston.bellywelly.global.feign.dto.KakaoTokenResponseDto;
import com.capston.bellywelly.global.feign.dto.KakaoUserResponseDto;
import com.capston.bellywelly.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoTokenClient kakaoTokenClient;
	private final KakaoUserClient kakaoUserClient;
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.client-secret}")
	private String clientSecret;

	public LoginResponseDto login(String code, String redirectUri) {
		// 카카오에 인가 코드를 보내 토큰 발급받기
		String kakaoAccessToken = getKakaoToken(code, redirectUri);
		// 카카오에 토큰을 보내 사용자 정보 받기
		KakaoUserResponseDto kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);
		// 카카오 회원번호(=member pk)로 가입 여부 확인
		Member member = createOrGetMember(kakaoUserInfo);
		// jwt token 생성 후 리턴
		return LoginResponseDto.builder()
			.name(member.getName())
			.accessToken(jwtTokenProvider.createAccessToken(member))
			.build();
	}

	public String getKakaoToken(String code, String redirectUri) {
		KakaoTokenResponseDto responseDto = kakaoTokenClient.getAccessToken(
			KakaoTokenRequestDto.builder()
				.grant_type("authorization_code")
				.client_id(clientId)
				.redirect_uri(redirectUri)
				.code(code)
				.client_secret(clientSecret)
				.build()
		);
		return responseDto.getAccess_token();
	}

	public KakaoUserResponseDto getKakaoUserInfo(String kakaoAccessToken) {
		String authorization = "Bearer " + kakaoAccessToken;
		return kakaoUserClient.getUserInfo(authorization);
	}

	public Member createOrGetMember(KakaoUserResponseDto responseDto) {
		Optional<Member> optionalMember = memberRepository.findById(responseDto.getId());
		if (optionalMember.isEmpty()) {
			KakaoAccountDto kakaoAccountDto = responseDto.getKakao_account();
			return memberRepository.save(
				Member.builder()
					.memberId(responseDto.getId())
					.name(kakaoAccountDto.getProfile().getNickname())
					.build()
			);
		}
		return optionalMember.get();
	}
}

