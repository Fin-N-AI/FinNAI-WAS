package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.auth.response.AuthPasswordVerificationResp;
import com.finnai.domain.user.dto.auth.response.AuthRefreshTokenResp;
import com.finnai.domain.user.dto.auth.response.AuthSignUpResp;
import com.finnai.domain.user.dto.auth.request.AuthRefreshTokenReq;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{
    @Override
    public AuthSignUpResp signUp(String email, String password) {
        return null;
    }

    @Override
    public AuthRefreshTokenResp refresh(AuthRefreshTokenReq dto) {
        return null;
    }

    @Override
    public AuthPasswordVerificationResp passwordVerify(String password) {
        return null;
    }
}
