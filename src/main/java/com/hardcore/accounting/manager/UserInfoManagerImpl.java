package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.exception.InvalidParameterException;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.model.common.UserInfo;
import com.hardcore.accounting.dao.UserInfoDAO;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
// 调用刚建好的接口
public class UserInfoManagerImpl implements UserInfoManager{
    public static final int HASH_ITERATIONS = 1000;
    // 把写好的Dao和Converter转换器都注入进来 (dao是数据库层到c层的,converter是c2s层的)
    // 然后放进构造器
    private final UserInfoDAO userInfoDAO;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoManagerImpl(final UserInfoDAO userInfoDAO,final UserInfoP2CConverter userInfoP2CConverter){
        this.userInfoDAO=userInfoDAO;
        this.userInfoP2CConverter=userInfoP2CConverter;
    }
    // DAO是p层的数据,所以经过处理后的userInfo也是P层的.然后用P2CConverter将数据转化为C层的
    @Override
    public UserInfo getUserInfoByUserId(Long userId){
        // val作用: 自动进行类型推断
        // Optional.ofNullable(): 允许传入id内容为空
        // .orElseThrow(): 如果传入id为空,那么丢出一个异常,调用NotFound的异常处理
        // UserInfo是从DAO中取出的userId
        val userInfo = Optional.ofNullable(userInfoDAO.getUserInfoById(userId))
                .orElseThrow(()->new ResourceNotFoundException(String.format("user %s is not found",userId)));
        return userInfoP2CConverter.convert(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        val userInfo = Optional.ofNullable(userInfoDAO.getUserInfoByUserName(username))
                .orElseThrow(()->new ResourceNotFoundException(String.format("username %s is not found",username)));
        return userInfoP2CConverter.convert(userInfo);
    }

    // 登录程序
    @Override
    public void login(String username, String password) {
        // Get subject: 拿到当前用户
        val subject = SecurityUtils.getSubject();
        // Construct token: 拿到用户键入的用户名密码
        val usernamePasswordToken = new UsernamePasswordToken(username, password);

        //login: 登录
        subject.login(usernamePasswordToken);
    }

    // 注册程序
    @Override
    public UserInfo register(String username, String password) {
        // 首先判断该username是否已被注册过
        val userInfo = userInfoDAO.getUserInfoByUserName(username);
        if(userInfo != null){
            // userInfo是从数据层中拿到的username.如果它不为空,就代表已经被注册过,就抛异常.
            throw new InvalidParameterException(String.format("the user %s was registered.",username));
        }
        // 排除异常可能性以后对user数据加盐处理
        String salt = UUID.randomUUID().toString();  // 盐
        // 处理后的password是原password加上盐  toBase64()就是toString()的意思.  1000代表迭代次数.加盐操作要迭代1000次
        String encryptedPassword = new Sha256Hash(password,salt, HASH_ITERATIONS).toBase64();
        // 把密码加盐后的各项数据作为新的UserInfo,传入DAO当中
        val newUserInfo = com.hardcore.accounting.model.persistence.UserInfo.builder()
                .username(username)
                .password(encryptedPassword)
                .salt(salt)
                .createTime(LocalDate.now())
                .build();
        userInfoDAO.createNewUser(newUserInfo);
        return userInfoP2CConverter.convert(newUserInfo);   // 将加密过后的数据转换为c层后,进行return
    }
}
