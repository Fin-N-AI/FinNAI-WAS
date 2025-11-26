package com.finnai.project.service;

import com.finnai.project.dto.UserDto;

public interface UserService {

    UserDto updateUser (UserDto dto);
    UserDto deleteUser (UserDto dto);
    UserDto getUserById (Long id);
}
