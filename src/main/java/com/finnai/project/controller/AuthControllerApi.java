package com.finnai.project.controller;

import com.finnai.project.dto.AuthPasswordVerificationDto;
import com.finnai.project.dto.AuthRefreshTokenRequestDto;
import com.finnai.project.dto.AuthRefreshTokenResponseDto;
import com.finnai.project.dto.AuthSignUpDto;
import com.finnai.project.response.GlobalApiResponse;
import com.finnai.project.response.SuccessCode;
import com.finnai.project.service.AuthPasswordVerificationService;
import com.finnai.project.service.AuthRefreshTokenService;
import com.finnai.project.service.AuthSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerApi {

    private final AuthSignUpService authSignUpService;
    private final AuthRefreshTokenService authRefreshTokenService;
    private final AuthPasswordVerificationService authPasswordVerificationService;


    @PostMapping("/sing-up")
    public GlobalApiResponse<AuthSignUpDto> authSingUp (@RequestBody AuthSignUpDto dto) {
        AuthSignUpDto createdUserDto = authSignUpService.signUp(dto.email(), dto.password());

        return GlobalApiResponse.success(SuccessCode.CREATED, createdUserDto );
    }

    @PostMapping("/refresh")
    public GlobalApiResponse<AuthRefreshTokenResponseDto> refresh (@RequestBody AuthRefreshTokenRequestDto dto){

        AuthRefreshTokenResponseDto refreshedToken = authRefreshTokenService.refresh(dto);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, refreshedToken );

    }

    @PostMapping("/password/verification")
    public GlobalApiResponse<AuthPasswordVerificationDto>  passwordVerify (@RequestBody AuthPasswordVerificationDto dto){

        AuthPasswordVerificationDto verifiedPassword =  authPasswordVerificationService.passwordVerify(dto.password());
        return GlobalApiResponse.success(SuccessCode.SUCCESS, verifiedPassword);
    }




}
