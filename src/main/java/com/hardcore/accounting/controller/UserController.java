package com.hardcore.accounting.controller;

import com.hardcore.accounting.converter.c2s.UserInfoC2SConverter;
import com.hardcore.accounting.exception.InvalidParameterException;
import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.service.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("v1.0/users")
@Slf4j  // 作用: 操作在idea中的控制台中打印的日志信息(log)

public class UserController {
    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;

    @Autowired  // autowired作用:让spring完成bean自动装配的工作。
    public UserController(final UserInfoManager userInfoManager, final UserInfoC2SConverter userInfoC2SConverter) {
        this.userInfoManager = userInfoManager;
        this.userInfoC2SConverter = userInfoC2SConverter;
    }

    // (最外层)  URL中输入user的id之后,通过manager（P2C）层读取用户信息,然后把数据通过C2S转化为S层
    @GetMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserInfo> getUserInfoByUserId(@PathVariable("id") @NotNull Long userId) {
        log.debug("Get user info by user id {}", userId);
        // <= 0代表userId为空,就丢出异常.调用异常包
        if (userId <= 0L) {
            throw new InvalidParameterException(String.format("The user id %s is invalid", userId));
        }
        // ResponseEntity代表整个HTTP响应,包括状态码status，标头head和正文body
        // ResponseEntity的内容在GlobalExceptionHandler包里被实现
        val userInfo = userInfoManager.getUserInfoByUserId(userId);  // 此处userInfo是C层的数据
        val userInfoToReturn = userInfoC2SConverter.convert(userInfo);  // userInfoToReturn: 通过C2S转成S层数据
        assert userInfoToReturn != null;
        return ResponseEntity.ok(userInfoToReturn);
    }
}
