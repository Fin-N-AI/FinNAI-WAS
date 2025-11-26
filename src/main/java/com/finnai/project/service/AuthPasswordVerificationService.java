package com.finnai.project.service;

import com.finnai.project.dto.AuthPasswordVerificationDto;

public interface AuthPasswordVerificationInterface {

    AuthPasswordVerificationDto passwordVerify(String password);

}
