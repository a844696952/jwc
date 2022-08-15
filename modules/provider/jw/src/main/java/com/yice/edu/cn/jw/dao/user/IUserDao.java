package com.yice.edu.cn.jw.dao.user;

import com.yice.edu.cn.common.pojo.general.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserDao {

    User findUserById(@Param("id") String id);
}
