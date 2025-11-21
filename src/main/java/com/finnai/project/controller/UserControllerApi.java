package com.finnai.project.controller;


import com.finnai.project.dto.UserBookmarksDto;
import com.finnai.project.dto.UserDto;
import com.finnai.project.service.UserBookmarksInterface;
import com.finnai.project.service.UserInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserControllerApi {


    private final UserInterface userInterface;
    private final UserBookmarksInterface userBookUserInterface;

    @PutMapping("")
    public void update (@RequestBody UserDto dto){

    }
    @GetMapping("")
    public UserDto getUser (@RequestBody UserDto dto){
        return userInterface.getUser(dto);
    }
    @DeleteMapping("")
    public UserDto deleteUser (@RequestBody UserDto dto){
        return userInterface.deleteUser(dto);
    }

    @GetMapping("/bookmarks")
    public UserBookmarksDto getUserBookmarks (@RequestBody UserDto dto){
        return userBookUserInterface.getBookmarks(dto);

    }

    @DeleteMapping("bookmarks")
    public UserBookmarksDto deleteUserBookmarks (@RequestBody String name){
        return userBookUserInterface.deleteBookmarks(name);
    }
}
