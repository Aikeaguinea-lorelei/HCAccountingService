package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.dao.UserInfoDAO;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserInfoManagerTest {
    private UserInfoManager userInfoManager;

    @Mock
    private UserInfoDAO userInfoDAO;

    // Arrange步骤中要new新的函数
    @BeforeEach
    public void setUp() {
        // UserInfoManager的class中需要传入Dao和p2c,而dao中需要传入mapper
        // DAO可以用mock建一个假的,P2C可以用new传一个真实的
        MockitoAnnotations.initMocks(this);  // 这行是为了让22行对DAO的mock操作能被识别出来
        userInfoManager = new UserInfoManagerImpl(userInfoDAO, new UserInfoP2CConverter());
    }

    @org.junit.jupiter.api.Test
    public void testGetUserInfoByUserId() {
        // Arrange
        val userId = 1L;
        val username = "hardcore";
        val password = "hardcore";
        val createTime = LocalDate.now();
        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();
        // (mock创造出的一个假的逻辑): 当DAO调用userID时,返回UserInfo的信息
        doReturn(userInfo).when(userInfoDAO).getUserInfoById(userId);
        // Act
        val result = userInfoManager.getUserInfoByUserId(userId);

        // Assert
        // hasFieldOrPropertyWithValue: 判断对象含有某个属性或某个属性的值为指定值
        // 要引入正确路径的assertThat
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id",userId)
                .hasFieldOrPropertyWithValue("username",username)
                .hasFieldOrPropertyWithValue("password",password);

        // 验证是不是真的调用了一次userId
        verify(userInfoDAO).getUserInfoById(userId);
    }


    @Test
    public void testGetUserInfoByUserIdWithInvalidUserId() {
        // Arrange
        val userId = -1L;
        doReturn(null).when(userInfoDAO).getUserInfoById(userId);
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoManager.getUserInfoByUserId(userId));
        verify(userInfoDAO).getUserInfoById(eq(userId));
    }


    // 演示mockito用法:
    @Test
    public void testListAddMethod() {
        // Arrange: 创建一个假的list,然后在里面指定返回一些数据
        val mockedList = mock(List.class);

        // 这两种写法都可以
        when(mockedList.get(0)).thenReturn("hello world");
        doReturn("hello bitch").when(mockedList).get(1);

        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(1));

        // Assert: 执行
        // verify的第二个参数代表被调用几次,不写默认调用一次.never()表示不被调用
        // get(anyInt())代表调用任意参数.上面调了两次,所以times是2
        verify(mockedList).get(0);
        verify(mockedList).get(1);
        verify(mockedList, never()).get(114514);
        verify(mockedList, times(2)).get(anyInt());

        // 如果希望抛异常:
//        val mockedLists=mock(List.class);
//        doThrow(new RuntimeException()).when(mockedLists).clear();
//        mockedLists.clear();
    }
}