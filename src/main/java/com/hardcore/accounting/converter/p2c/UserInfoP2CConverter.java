package com.hardcore.accounting.converter.p2c;

import com.google.common.base.Converter;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component("userInfoP2CConverter")
@NoArgsConstructor  // 表示没有构造参数的构造器
public class UserInfoP2CConverter extends Converter<UserInfo,com.hardcore.accounting.model.common.UserInfo> {
    @Override
    protected com.hardcore.accounting.model.common.UserInfo doForward(UserInfo userInfo) {
        // UserInfo里面有什么参数就get()什么信息.因为这层不直接接触前端所以可以传递password数据
        return com.hardcore.accounting.model.common.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
    @Override
    protected UserInfo doBackward(com.hardcore.accounting.model.common.UserInfo userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .Username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
