package com.hardcore.accounting.dao;

import com.hardcore.accounting.dao.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;
import com.hardcore.accounting.model.persistence.UserInfo;

@Repository
@Slf4j
public class UserInfoDAOImpl implements UserInfoDAO{
    private final UserInfoMapper userInfoMapper;

    public UserInfoDAOImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }


    @Override
    public UserInfo getUserInfoById(Long id){
        return userInfoMapper.getUserInfoByUserId(id);
    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        return userInfoMapper.getUserInfoByUserName(username);
    }

    // 创建新user: 实现register功能
    @Override
    public void createNewUser(UserInfo userInfo){
        userInfoMapper.createNewUser(userInfo);
    }
}
