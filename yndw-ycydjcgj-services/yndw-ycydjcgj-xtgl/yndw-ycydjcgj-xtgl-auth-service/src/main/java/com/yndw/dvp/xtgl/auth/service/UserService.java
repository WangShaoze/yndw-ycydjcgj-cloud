package com.yndw.dvp.xtgl.auth.service;

import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.UserDto;
import com.yndw.dvp.xtgl.auth.model.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryLikeParam;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryParam;

public interface UserService extends ISuperService<User> {

    Page<UserDto> findPage(SecurityUser securityUser, UserQueryParam queryParam);
    Page<UserDto> searchByUserQueryLike(SecurityUser securityUser, UserQueryLikeParam queryParam);
    User saveOrUpdateUser(SecurityUser securityUser, User user);
    int deleteUserById(String id);


}
