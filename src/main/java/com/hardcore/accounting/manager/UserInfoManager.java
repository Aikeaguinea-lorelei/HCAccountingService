package com.hardcore.accounting.manager;

import com.hardcore.accounting.model.common.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoByUserId(Long userId);
}
