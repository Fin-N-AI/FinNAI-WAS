package com.finnai.project.controller;


import com.finnai.project.dto.UserBookmarksDto;
import com.finnai.project.dto.UserDto;
import com.finnai.project.response.GlobalApiResponse;
import com.finnai.project.response.SuccessCode;
import com.finnai.project.service.UserBookmarkService;
import com.finnai.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserControllerApi {


    private final UserService userService;
    private final UserBookmarkService userBookUserService;

    @PutMapping("/{id}")
    public GlobalApiResponse<UserDto> updateUser (@PathVariable Long id){
        UserDto userDto = userService.updateUserById(id);
        return GlobalApiResponse.success(SuccessCode.UPDATED, userDto);

    }
    @GetMapping("/{id}")
    public GlobalApiResponse<UserDto> getUser (@PathVariable Long id){

        UserDto userDto = userService.getUserById(id);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, userDto);
    }
    @DeleteMapping("/{id}")
    public GlobalApiResponse<UserDto> deleteUser (@PathVariable Long id){
        UserDto userDto = userService.deleteUserById(id);

        return GlobalApiResponse.success(SuccessCode.REMOVED, userDto);

    }

    @GetMapping("/bookmarks")
    public GlobalApiResponse<UserBookmarksDto>  getUserBookmarks (@RequestBody UserDto dto){

        UserBookmarksDto bookmarksDto = userBookUserService.getBookmarks(dto);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, bookmarksDto);
    }

    @DeleteMapping("bookmarks")
    public GlobalApiResponse<UserBookmarksDto>  deleteUserBookmarks (@RequestBody String name){

        UserBookmarksDto userBookmarksDto = userBookUserService.deleteBookmarks(name);

        return GlobalApiResponse.success(SuccessCode.REMOVED, userBookmarksDto);
    }
}
