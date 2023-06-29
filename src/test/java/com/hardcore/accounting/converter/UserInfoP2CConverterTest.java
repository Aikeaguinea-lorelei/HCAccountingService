package com.hardcore.accounting.converter;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoP2CConverterTest {
    private UserInfoP2CConverter userInfoP2CConverter = new UserInfoP2CConverter();
    // 测试里面的两个函数
    @Test
    void testDoForward(){
        // Arrange
        val userId = 100L;
        val username = "hardcore";
        val password = "hardcore";
        val createTime  = LocalDate.now();
        val userInfoInPersistence = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();
        // Act
        val result = userInfoP2CConverter.convert(userInfoInPersistence);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id",userId)
                .hasFieldOrPropertyWithValue("username",username)
                .hasFieldOrPropertyWithValue("password",password);
    }
    @Test
    void testDoBackward(){
        // Arrange
        val userId = 100L;
        val username = "hardcore";
        val password = "hardcore";
        val userInfoCommon = com.hardcore.accounting.model.common.UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
        // Act (反向操作时别忘了reverse())
        val result = userInfoP2CConverter.reverse().convert(userInfoCommon);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id",userId)
                .hasFieldOrPropertyWithValue("username",username)
                .hasFieldOrPropertyWithValue("password",password)
                .hasFieldOrPropertyWithValue("createTime",null);
    }
}
