package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.xtgl.auth.dto.UserDto;
import com.yndw.dvp.xtgl.auth.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    public static UserDto build(User entity) {
        UserDto dto = new UserDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }

    public static List<UserDto> build(List<User> entityList) {
        List<UserDto> dtoList = new ArrayList<>();
        for (User entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    public static Page<UserDto> buildDto(Page<User> entityPage) {
        Page<UserDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }
}
