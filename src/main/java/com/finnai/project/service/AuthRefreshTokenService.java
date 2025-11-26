package com.finnai.project.service;

import com.finnai.project.dto.AuthRefreshTokenRequestDto;
import com.finnai.project.dto.AuthRefreshTokenResponseDto;

public interface AuthRefreshTokenService {


    AuthRefreshTokenResponseDto refresh (AuthRefreshTokenRequestDto dto) ;


}
