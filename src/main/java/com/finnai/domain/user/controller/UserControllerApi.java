package com.finnai.domain.user.controller;

import com.finnai.domain.user.dto.bookmark.request.GetUserBookmarkReq;
import com.finnai.domain.user.dto.bookmark.response.DeleteUserBookmarksResp;
import com.finnai.domain.user.dto.bookmark.response.GetUserBookmarksResp;
import com.finnai.domain.user.dto.user.response.DeleteUserResp;
import com.finnai.domain.user.dto.user.response.GetUserResp;
import com.finnai.domain.user.dto.user.response.UpdateUserResp;
import com.finnai.domain.user.service.UserBookmarkService;
import com.finnai.domain.user.service.UserService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
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
    public GlobalApiResponse<UpdateUserResp> updateUser(@PathVariable Long id) {
        UpdateUserResp userDto = userService.updateUserById(id);
        return GlobalApiResponse.success(SuccessCode.UPDATED, userDto);

    }

    @GetMapping("/{id}")
    public GlobalApiResponse<GetUserResp> getUser(@PathVariable Long id) {

        GetUserResp userDto = userService.getUserById(id);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, userDto);
    }

    @DeleteMapping("/{id}")
    public GlobalApiResponse<DeleteUserResp> deleteUser(@PathVariable Long id) {
        DeleteUserResp userDto = userService.deleteUserById(id);

        return GlobalApiResponse.success(SuccessCode.REMOVED, userDto);

    }

    @GetMapping("/bookmarks")
    public GlobalApiResponse<GetUserBookmarksResp> getUserBookmarks(@RequestBody GetUserBookmarkReq dto) {

        GetUserBookmarksResp bookmarksDto = userBookUserService.getBookmarks(dto);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, bookmarksDto);
    }

    @DeleteMapping("bookmarks")
    public GlobalApiResponse<DeleteUserBookmarksResp> deleteUserBookmarks(@RequestBody String name) {

        DeleteUserBookmarksResp userBookmarksDto = userBookUserService.deleteBookmarks(name);

        return GlobalApiResponse.success(SuccessCode.REMOVED, userBookmarksDto);
    }
}

