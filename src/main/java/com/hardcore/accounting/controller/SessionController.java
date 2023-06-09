package com.hardcore.accounting.controller;

import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.service.UserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * sessionController作用: 设定login功能的url
 *
 * Login with username and password.
 */
@RestController
@RequestMapping("v1.0/session")
// value: 对于传入api的说明
@Api(value = "Session management API")
public class SessionController {
    private static final String SUCCESS = "success";
    private static final String STATUS = "status";
    private static final String USERNAME = "username";
    private static final int OK = 200;
    private static final int NOT_FOUND = 400;
    private final UserInfoManager userInfoManager;

    @Autowired
    public SessionController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    // api的返回值是下面定义的Map
    @ApiOperation(value = "user login API", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully login"),
            @ApiResponse(code = NOT_FOUND, message = "The related user was not found")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")


    // login: 将传入的数据信息放进响应体当中
    public Map<String, String> login(@RequestBody @NotNull UserInfo userInfo) {
        val response = new HashMap<String, String>();
        userInfoManager.login(userInfo.getUsername(), userInfo.getPassword());
        response.put(STATUS, SUCCESS);
        response.put(USERNAME, userInfo.getUsername());
        return response;
    }
}
