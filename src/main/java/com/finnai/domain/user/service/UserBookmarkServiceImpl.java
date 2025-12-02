package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.bookmark.request.GetUserBookmarkReq;
import com.finnai.domain.user.dto.bookmark.response.DeleteUserBookmarksResp;
import com.finnai.domain.user.dto.bookmark.response.GetUserBookmarksResp;
import org.springframework.stereotype.Service;

@Service
public class UserBookmarkServiceImpl implements UserBookmarkService{
    @Override
    public GetUserBookmarksResp getBookmarks(GetUserBookmarkReq dto) {
        return null;
    }

    @Override
    public DeleteUserBookmarksResp deleteBookmarks(String name) {
        return null;
    }
}
