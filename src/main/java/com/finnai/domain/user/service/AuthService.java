package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.auth.response.AuthPasswordVerificationResp;
import com.finnai.domain.user.dto.auth.response.AuthRefreshTokenResp;
import com.finnai.domain.user.dto.auth.response.AuthSignUpResp;
import com.finnai.domain.user.dto.auth.request.AuthRefreshTokenReq;

public interface AuthService {

    AuthSignUpResp signUp (String email, String password);

    AuthRefreshTokenResp refresh (AuthRefreshTokenReq dto) ;

    AuthPasswordVerificationResp passwordVerify(String password);
}
