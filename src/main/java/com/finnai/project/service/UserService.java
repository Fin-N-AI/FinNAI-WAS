package com.finnai.project.service;

import com.finnai.project.dto.UserDto;

public interface UserService {

    UserDto updateUserById (Long id);
    UserDto deleteUserById (Long id);
    UserDto getUserById (Long id);
}
