package com.finnai.domain.user.controller;

import com.finnai.domain.user.dto.auth.request.AuthPasswordVerificationReq;
import com.finnai.domain.user.dto.auth.request.AuthRefreshTokenReq;
import com.finnai.domain.user.dto.auth.request.AuthSignUpReq;
import com.finnai.domain.user.dto.auth.response.AuthPasswordVerificationResp;
import com.finnai.domain.user.dto.auth.response.AuthRefreshTokenResp;
import com.finnai.domain.user.dto.auth.response.AuthSignUpResp;
import com.finnai.domain.user.service.AuthService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerApi {

    private final AuthService authService;

    @PostMapping("/sing-up")
    public GlobalApiResponse<AuthSignUpResp> authSingUp (@RequestBody AuthSignUpReq dto) {
        AuthSignUpResp createdUserDto = authService.signUp(dto.email(), dto.password());

        return GlobalApiResponse.success(SuccessCode.CREATED, createdUserDto );
    }


    @PostMapping("/refresh")
    public GlobalApiResponse<AuthRefreshTokenResp> refresh (@RequestBody AuthRefreshTokenReq dto){

        AuthRefreshTokenResp refreshedToken = authService.refresh(dto);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, refreshedToken );

    }

    @PostMapping("/password/verification")
    public GlobalApiResponse<AuthPasswordVerificationResp>  passwordVerify (@RequestBody AuthPasswordVerificationReq dto){

        AuthPasswordVerificationResp verifiedPassword =  authService.passwordVerify(dto.password());
        return GlobalApiResponse.success(SuccessCode.SUCCESS, verifiedPassword);
    }


}
