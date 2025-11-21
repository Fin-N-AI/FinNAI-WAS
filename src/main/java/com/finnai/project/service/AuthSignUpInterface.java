package com.finnai.project.service;

import com.finnai.project.dto.AuthSignUpDto;


public interface AuthSignUpInterface {

    AuthSignUpDto signUp (String email, String password);
}
