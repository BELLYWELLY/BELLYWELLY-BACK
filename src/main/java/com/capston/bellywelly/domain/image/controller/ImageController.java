package com.capston.bellywelly.domain.image.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capston.bellywelly.domain.image.service.S3Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "이미지", description = "이미지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

	private final S3Service s3Service;

	@Operation(summary = "S3 이미지 업로드")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createPresignedUrl(@RequestPart(value = "image") MultipartFile image) {
		return s3Service.upload(image);
	}
}
