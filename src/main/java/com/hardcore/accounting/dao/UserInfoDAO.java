package com.hardcore.accounting.dao;

import com.hardcore.accounting.model.persistence.UserInfo;

public interface UserInfoDAO {
    // 引入数据层的userInfo
    UserInfo getUserInfoById(Long id);
    UserInfo getUserInfoByUserName(String username);
    void createNewUser(UserInfo userInfo);
}
