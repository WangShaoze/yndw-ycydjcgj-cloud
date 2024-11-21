package com.yndw.dvp.xtgl.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.UserDto;
import com.yndw.dvp.xtgl.auth.mapper.UserMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtZzxxMapper;
import com.yndw.dvp.xtgl.auth.model.User;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryLikeParam;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryParam;
import com.yndw.dvp.xtgl.auth.service.UserService;
import com.yndw.dvp.xtgl.auth.utils.GenerateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yndw.dvp.xtgl.auth.factory.UserFactory.buildDto;

@Slf4j
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private XtZzxxMapper zzxxMapper;


    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(com.yndw.dvp.xtgl.auth.controller.XtCzyqxController.class);

    @Override
    public Page<UserDto> findPage(SecurityUser securityUser, UserQueryParam queryParam) {
        if (StringUtils.isEmpty(queryParam.getSjbhs())) {
            logger.error("UserServiceImpl: => findPage => \"无法获取该岗位对应的 上级编号路径 , 请检查是否有该权限！\"");
            throw new BusinessException("无法获取该岗位对应的 上级编号路径 , 请检查是否有该权限！");
        }
        if ((StringUtils.isNotEmpty(queryParam.getSuo_id()) && StringUtils.isEmpty(queryParam.getJu_id()))
                ||
                (StringUtils.isNotEmpty(queryParam.getSuo()) && StringUtils.isEmpty(queryParam.getJu()))) {
            // 查询供电所层面的数据
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
            userQueryWrapper.eq("suo_id", queryParam.getSuo_id());
            userQueryWrapper.orderByDesc("create_time");
            userQueryWrapper.orderByDesc("update_time");
            Page<User> userPage = userMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), userQueryWrapper);
            return buildDto(userPage);
        } else if ((StringUtils.isEmpty(queryParam.getSuo_id()) && StringUtils.isNotEmpty(queryParam.getJu_id()))
                ||
                (StringUtils.isEmpty(queryParam.getSuo()) && StringUtils.isNotEmpty(queryParam.getJu()))) {
            // 查询供电局层面的数据
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
            userQueryWrapper.eq("ju_id", queryParam.getJu_id());
            userQueryWrapper.orderByDesc("create_time");
            userQueryWrapper.orderByDesc("update_time");
            Page<User> userPage = userMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), userQueryWrapper);
            return buildDto(userPage);
        } else if (StringUtils.isEmpty(queryParam.getSuo_id()) && StringUtils.isEmpty(queryParam.getJu_id()) &&
                StringUtils.isEmpty(queryParam.getSuo()) && StringUtils.isEmpty(queryParam.getJu()) && (
                StringUtils.isNotEmpty(queryParam.getZzbh()) || StringUtils.isNotEmpty(queryParam.getZzmc())
        )) {
            // 查询供电局层面的数据
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
            userQueryWrapper.like("sjbhs", queryParam.getSjbhs()).or().like("sjbhs", queryParam.getZzbh());
            userQueryWrapper.orderByDesc("create_time");
            userQueryWrapper.orderByDesc("update_time");
            Page<User> userPage = userMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), userQueryWrapper);
            return buildDto(userPage);
        } else {
            logger.error("UserServiceImpl: => findPage => \"查询参数有误, 请检查是否有该权限！\"");
            throw new BusinessException("查询参数有误, 请检查是否有该权限！");
        }
    }

    @Override
    public Page<UserDto> searchByUserQueryLike(SecurityUser securityUser, UserQueryLikeParam queryParam) {
        // 获取当前用户的上级部门
        // gwbh      岗位编号 获取 上级编号路径
        XtZzxx entity = zzxxMapper.selectById(securityUser.getZzbh());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        // 获取组织编号和组织名称
        String zzbh = entity.getBh();
        String zzmc = entity.getZzmc();
        if (zzmc.contains("供电所")) {
            userQueryWrapper.eq("suo_id", zzbh);
        } else if (zzmc.contains("供电局") && !zzmc.contains("文山供电局")) {
            userQueryWrapper.eq("ju_id", zzbh);
        }

        if (StringUtils.isNotEmpty(queryParam.getUser_code_part()) && StringUtils.isNotEmpty(queryParam.getUser_name_part())) {
            userQueryWrapper.like("user_code", queryParam.getUser_code_part()).or().like("user_name", queryParam.getUser_name_part());
        } else if (StringUtils.isNotEmpty(queryParam.getUser_code_part())) {
            userQueryWrapper.like("user_code", queryParam.getUser_code_part());
        } else if (StringUtils.isNotEmpty(queryParam.getUser_name_part())) {
            userQueryWrapper.like("user_name", queryParam.getUser_name_part());
        }

        String sjbhs = entity.getSjbhs();  // 上级编号路径
        userQueryWrapper.like("sjbhs", sjbhs);
        userQueryWrapper.orderByDesc("create_time");
        userQueryWrapper.orderByDesc("update_time");
        Page<User> userPage = userMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), userQueryWrapper);
        return buildDto(userPage);
    }

    @Override
    public User saveOrUpdateUser(SecurityUser securityUser, User user) {
        if (StringUtils.isEmpty(user.getId())) {
            // 添加
            user.setId(GenerateUtils.generate12DigitId());
            user.setCreateId(securityUser.getBh());
            user.setCreateName(securityUser.getDlzh());
            // 查询出供电局
            String[] sjbhs = user.getSjbhs().split(",");
            String juId = sjbhs[sjbhs.length - 1];
            user.setJuId(juId);
            user.setJu(zzxxMapper.selectById(juId).getZzmc());
            userMapper.insertUser(user);
            logger.info("UserServiceImpl: => saveOrUpdateUser =>  烟叶user" + user.getId() + "添加成功！");
        } else {
            // 更新用户
            user.setUpdateId(securityUser.getBh());
            user.setUpdateName(securityUser.getDlzh());
            userMapper.updateUser(user);
            logger.info("UserServiceImpl: => saveOrUpdateUser =>  烟叶user" + user.getId() + "更新成功！");
        }
        return user;
    }

    @Override
    public int deleteUserById(String id) {
        return userMapper.removeById(id);
    }
}
