package com.hardcore.accounting.manager;

import com.hardcore.accounting.model.common.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoByUserId(Long userId);
    UserInfo getUserInfoByUserName(String username);
    void login(String username, String password);
    UserInfo register(String username, String password);
}
