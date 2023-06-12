package com.hardcore.accounting.controller;

import com.hardcore.accounting.converter.c2s.UserInfoC2SConverter;
import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.service.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/users")
@Slf4j  // 作用: 操作在idea中的控制台中打印的日志信息(log)

public class UserController {
    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;

    @Autowired  // autowired作用:让spring完成bean自动装配的工作。
    public UserController(final UserInfoManager userInfoManager,final UserInfoC2SConverter userInfoC2SConverter){
        this.userInfoManager=userInfoManager;
        this.userInfoC2SConverter=userInfoC2SConverter;
    }

    @GetMapping("/{id}")
    public UserInfo getUserInfoByUserId(@PathVariable("id") Long userId){
        log.debug("Get user Info by user id {}",userId);
        com.hardcore.accounting.model.common.UserInfo userInfo = userInfoManager.getUserInfoByUserId(userId);
        return userInfoC2SConverter.convert(userInfo);
    }
}
