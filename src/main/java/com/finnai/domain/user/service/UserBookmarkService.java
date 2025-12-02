package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.bookmark.request.GetUserBookmarkReq;
import com.finnai.domain.user.dto.bookmark.response.DeleteUserBookmarksResp;
import com.finnai.domain.user.dto.bookmark.response.GetUserBookmarksResp;

public interface UserBookmarkService {

    GetUserBookmarksResp getBookmarks (GetUserBookmarkReq dto);
    DeleteUserBookmarksResp deleteBookmarks (String name);

}
