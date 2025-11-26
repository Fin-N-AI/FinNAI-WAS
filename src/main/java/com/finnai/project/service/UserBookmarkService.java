package com.finnai.project.service;

import com.finnai.project.dto.UserBookmarksDto;
import com.finnai.project.dto.UserDto;

public interface UserBookmarkService {



    UserBookmarksDto getBookmarks (UserDto dto);
    UserBookmarksDto deleteBookmarks (String name);


}
