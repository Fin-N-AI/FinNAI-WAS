package com.finnai.project.service;

import com.finnai.project.dto.UserDto;

public interface UserInterface {

    UserDto updateUser (UserDto dto);
    UserDto deleteUser (UserDto dto);
    UserDto getUser (UserDto dto);
}
