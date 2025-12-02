package com.finnai.domain.user.dto.bookmark.response;

import java.util.List;

public record DeleteUserBookmarksResp(String username, List<String> bookmarks) {

}
