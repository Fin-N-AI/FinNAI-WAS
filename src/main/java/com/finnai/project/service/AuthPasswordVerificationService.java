package com.finnai.project.service;

import com.finnai.project.dto.AuthPasswordVerificationDto;

public interface AuthPasswordVerificationService {

    AuthPasswordVerificationDto passwordVerify(String password);

}
