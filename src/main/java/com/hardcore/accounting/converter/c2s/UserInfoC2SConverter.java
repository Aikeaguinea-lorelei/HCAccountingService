package com.hardcore.accounting.converter.c2s;

import com.google.common.base.Converter;
import com.hardcore.accounting.model.common.UserInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component("userInfoC2SConverter")
@NoArgsConstructor
// 将service中的UserInfo与common中的UserInfo做互相转换
public class UserInfoC2SConverter extends Converter<UserInfo,com.hardcore.accounting.model.service.UserInfo> {
    // 这两层@Override代码是光标放"extends"上 -> alt+enter -> employment methods 自动生成的
    @Override
    protected com.hardcore.accounting.model.service.UserInfo doForward(UserInfo userInfo){
        // 这里面不要去对password信息进行直接build
        return com.hardcore.accounting.model.service.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
    @Override
    protected UserInfo doBackward(com.hardcore.accounting.model.service.UserInfo userInfo){
        // 丢出一个异常的话表示不支持这个方向的转换
        // throw new UnsupportedOperationException();
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
