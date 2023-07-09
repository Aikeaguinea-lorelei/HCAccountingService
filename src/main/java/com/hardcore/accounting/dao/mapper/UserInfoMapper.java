package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * mapper层直接和数据库打交道.各种操作是向数据库中存取数据
 * **/
@Mapper
public interface UserInfoMapper {
    // 这样在url中写localhost:8080/v1/users/1,就会在页面显示一条id为1的数据
    @Select("SELECT id,username,password,create_time,update_time FROM hcas_userinfo WHERE id=#{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);

    @Select("SELECT id,username,password,salt,create_time,update_time FROM hcas_userinfo WHERE username=#{username}")
    UserInfo getUserInfoByUserName(@Param("username") String username);

    @Options(useGeneratedKeys = true, keyProperty = "id")  // 代表每个key是自增的
    @Insert("INSERT into hcas_userinfo(username,password,salt,create_time) VALUES (#{username,#{password},#{salt},#{createTime})")
    void createNewUser(UserInfo userInfo);
}
