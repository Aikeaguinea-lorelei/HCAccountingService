package com.hardcore.accounting.converter.p2c;

import com.google.common.base.Converter;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component("userInfoP2CConverter")
@NoArgsConstructor  // 表示没有构造参数的构造器
@EqualsAndHashCode(callSuper = true)  // 这行的作用: 使两个对象值一致,但是父类不一致时,计算机不把二者当成两个相同的东西
public class UserInfoP2CConverter extends Converter<UserInfo,com.hardcore.accounting.model.common.UserInfo> {
    @Override
    protected com.hardcore.accounting.model.common.UserInfo doForward(@NotNull UserInfo userInfo) {
        // UserInfo里面有什么参数就get()什么信息.因为这层不直接接触前端所以可以传递password数据
        return com.hardcore.accounting.model.common.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .salt(userInfo.getSalt())
                .build();
    }
    @Override
    // @NotNull: 表示传入的数据一定不为空
    protected UserInfo doBackward(@NotNull com.hardcore.accounting.model.common.UserInfo userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
