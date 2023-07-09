package com.hardcore.accounting.config;

import com.hardcore.accounting.manager.UserInfoManager;
import lombok.val;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * realm: 直接和data打交道的一层.
 * 对login程序中用户输入的password和username进行筛选,筛选通过的话对其进行认证,未通过的话抛异常
 * **/
@Component
// AuthorizingRealm: 用于做权限认证
public class UserRealm extends AuthorizingRealm {
    private final UserInfoManager userInfoManager;

    @Autowired
    public UserRealm(UserInfoManager userInfoManager, HashedCredentialsMatcher matcher) {
        super(matcher);
        this.userInfoManager = userInfoManager;
    }

    // 然后对realm里面的authorz和authorn进行重写
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // AuthN: 作用是实现login的认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();

        val userInfo = userInfoManager.getUserInfoByUserName(username);

        // 对password进行筛选,不符合要求的话抛异常
        if(!password.equals("hardcore")){
            throw new IncorrectCredentialsException(String.format("The password is invalid for username %s",username));
        }
        // 将通过筛选后的username和password和salt做认证(从userInfo里面拿数据,比较安全)  (拿到的salt要从string类型转换为ByteSource类型)
        return new SimpleAuthenticationInfo(userInfo.getUsername(),
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                this.getName());
    }
}
