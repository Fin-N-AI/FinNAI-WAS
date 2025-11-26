package com.finnai.project.service;

import com.finnai.project.dto.AuthSignUpDto;


public interface AuthSignUpService {

    AuthSignUpDto signUp (String email, String password);
}
