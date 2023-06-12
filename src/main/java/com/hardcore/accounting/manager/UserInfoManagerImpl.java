package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.model.common.UserInfo;
import com.hardcore.accounting.dao.UserInfoDAO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 调用刚建好的接口
public class UserInfoManagerImpl implements UserInfoManager{
    // 把写好的Dao和Converter转换器都注入进来 (dao是数据库层到c层的,converter是c2s层的)
    // 然后放进构造器
    private final UserInfoDAO userInfoDAO;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoManagerImpl(final UserInfoDAO userInfoDAO,final UserInfoP2CConverter userInfoP2CConverter){
        this.userInfoDAO=userInfoDAO;
        this.userInfoP2CConverter=userInfoP2CConverter;
    }
    @Override
    // 因为是manager层,所以调用的是common中的UserInfo实现
    public UserInfo getUserInfoByUserId(Long userId){
        val userInfo=userInfoDAO.getUserInfoById(userId);  // val作用: 自动进行类型推断
        return userInfoP2CConverter.convert(userInfo);
    }
}
