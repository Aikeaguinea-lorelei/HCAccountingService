package com.hardcore.accounting.dao;

import com.hardcore.accounting.dao.mapper.UserInfoMapper;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class UserInfoDAOTest {
    @Mock
    private UserInfoMapper userInfoMapper;
    private UserInfoDAO userInfoDAO;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);  // 这行是为了让mock操作能被识别出来
        // new 一个需要被测试的DAO,传入mock的Mapper
        userInfoDAO = new UserInfoDAOImpl(userInfoMapper);
    }
    // @Test底下测试的是里面的函数
    @Test
    public void testGetUserInfoById(){
        // Arrange  getUserInfoById()中传入的是Long Id,因此此处做出Id的数据.
        val userId = 100L;
        // 再做出需要返回的数据UserInfo
        val username = "hardcore";
        val password = "hardcore";
        val createTime = LocalDate.now();
        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();
        doReturn(userInfo).when(userInfoMapper).getUserInfoByUserId(userId);
        // Act
        val result = userInfoDAO.getUserInfoById(userId);
        // Assert: 断言userInfo和result返回值相等
        assertEquals(userInfo,result);
        verify(userInfoMapper).getUserInfoByUserId(userId);
    }
}
