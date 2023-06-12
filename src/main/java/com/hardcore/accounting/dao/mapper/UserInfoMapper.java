package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    // 这样在url中写localhost:8080/v1/users/1,就会在页面显示一条id为1的数据
    @Select("SELECT id,username,password,create_time,update_time FROM hcas_userinfo WHERE id=#{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);
}
