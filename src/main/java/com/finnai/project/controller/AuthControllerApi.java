package com.finnai.project.controller;

import com.finnai.project.dto.AuthPasswordVerificationDto;
import com.finnai.project.dto.AuthRefreshTokenRequestDto;
import com.finnai.project.dto.AuthRefreshTokenResponseDto;
import com.finnai.project.dto.AuthSignUpDto;
import com.finnai.project.response.GlobalApiResponse;
import com.finnai.project.response.SuccessCode;
import com.finnai.project.service.AuthPasswordVerificationInterface;
import com.finnai.project.service.AuthRefreshTokenInterface;
import com.finnai.project.service.AuthSignUpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerApi {

    private AuthSignUpInterface authSignUpInterface;
    private AuthRefreshTokenInterface authRefreshTokenInterface;
    private AuthPasswordVerificationInterface authPasswordVerificationInterface;

//    public CompanySummaryDto summary (@PathVariable("id") int companyId) {
//
//        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
//
//        return companySummary.summary(companyId);
//
//
//    }

    @PostMapping("/sing-up")
    public GlobalApiResponse<AuthSignUpDto> authSingUp (@RequestBody AuthSignUpDto dto) {
        return GlobalApiResponse.success(SuccessCode.COMPANY_SUCCESS, authSignUpInterface.signUp(dto.email(), dto.password()))
    }

    @PostMapping("/refresh")
    public AuthRefreshTokenResponseDto refresh (@RequestBody AuthRefreshTokenRequestDto dto){
        return authRefreshTokenInterface.refresh(dto);

    }

    @PostMapping("/password/verification")
    public AuthPasswordVerificationDto passwordVerify (@RequestBody AuthPasswordVerificationDto dto){
        return authPasswordVerificationInterface.passwordVerify(dto.password());
    }




}
