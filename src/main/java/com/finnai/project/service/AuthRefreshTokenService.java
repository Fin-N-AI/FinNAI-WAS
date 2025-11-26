package com.finnai.project.service;

import com.finnai.project.dto.AuthRefreshTokenRequestDto;
import com.finnai.project.dto.AuthRefreshTokenResponseDto;
import com.finnai.project.dto.AuthSignUpDto;

public interface AuthRefreshTokenInterface {


    AuthRefreshTokenResponseDto refresh (AuthRefreshTokenRequestDto dto) ;


}
