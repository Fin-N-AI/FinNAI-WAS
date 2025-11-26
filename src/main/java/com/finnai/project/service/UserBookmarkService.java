package com.finnai.project.service;

import com.finnai.project.dto.AuthSignUpDto;
import com.finnai.project.dto.UserBookmarksDto;
import com.finnai.project.dto.UserDto;

public interface UserBookmarksInterface {



    UserBookmarksDto getBookmarks (UserDto dto);
    UserBookmarksDto deleteBookmarks (String name);


}
