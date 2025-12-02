package com.finnai.domain.user.dto.bookmark.response;

import java.util.List;

public record GetUserBookmarksResp(String username, List<String> bookmarks) {

}
