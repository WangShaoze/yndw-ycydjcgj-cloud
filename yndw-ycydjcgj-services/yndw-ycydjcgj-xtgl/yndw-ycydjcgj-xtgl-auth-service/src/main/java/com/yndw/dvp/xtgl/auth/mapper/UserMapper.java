package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends SuperMapper<User> {
    void insertUser(@Param("user") User user);
    void updateUser(@Param("user") User user);
    int removeById(@Param("id") String id);
}
