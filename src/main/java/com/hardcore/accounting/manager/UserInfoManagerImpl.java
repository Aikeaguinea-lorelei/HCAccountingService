package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.model.common.UserInfo;
import com.hardcore.accounting.dao.UserInfoDAO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    // DAO是p层的数据,所以经过处理后的userInfo也是P层的.然后用P2CConverter将数据转化为C层的
    @Override
    public UserInfo getUserInfoByUserId(Long userId){
        // val作用: 自动进行类型推断
        // Optional.ofNullable(): 允许传入id内容为空
        // .orElseThrow(): 如果传入id为空,那么丢出一个异常,调用NotFound的异常处理
        val userInfo = Optional.ofNullable(userInfoDAO.getUserInfoById(userId))
                .orElseThrow(()->new ResourceNotFoundException(String.format("user %s is not found",userId)));
        return userInfoP2CConverter.convert(userInfo);
    }
}
