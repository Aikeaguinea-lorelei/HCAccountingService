package com.hardcore.accounting.dao;

import com.hardcore.accounting.dao.mapper.UserInfoMapper;
import org.springframework.stereotype.Repository;
import com.hardcore.accounting.model.persistence.UserInfo;

@Repository
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
    public void createNewUser(String username, String password){
    }
}
