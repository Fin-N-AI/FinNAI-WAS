package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.user.response.DeleteUserResp;
import com.finnai.domain.user.dto.user.response.GetUserResp;
import com.finnai.domain.user.dto.user.response.UpdateUserResp;

public interface UserService {

    UpdateUserResp updateUserById (Long id);
    DeleteUserResp deleteUserById (Long id);
    GetUserResp getUserById (Long id);

}
