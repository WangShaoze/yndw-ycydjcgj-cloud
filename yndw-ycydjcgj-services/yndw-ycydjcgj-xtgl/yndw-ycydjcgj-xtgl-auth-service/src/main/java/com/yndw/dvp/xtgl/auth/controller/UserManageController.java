package com.yndw.dvp.xtgl.auth.controller;

import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.UserDto;
import com.yndw.dvp.xtgl.auth.model.User;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryLikeParam;
import com.yndw.dvp.xtgl.auth.queryParam.UserQueryParam;
import com.yndw.dvp.xtgl.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/ycckyhgl")
@Api(tags = "烟叶初考用户管理")
public class UserManageController {
    private static final Logger logger = LoggerFactory.getLogger(XtCzyqxController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/searchYYCKUser")
    @AuditLog(operation = "查询烟叶初考用户")
    @ApiOperation(value = "查询烟叶初考用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "queryParam", value = "过滤条件", dataType = "UserQueryParam", required = false)
    })
    public PageResult<UserDto> searchYYCKUser(@LoginUser SecurityUser securityUser, @RequestBody UserQueryParam queryParam) {
        if (securityUser == null) {
            throw new BusinessException("查询失败，请先登录！");
        }
        try {
            System.out.println("loginUser = " + securityUser);
            System.out.println("queryParam = " + queryParam.toString());
            return PageResult.succeed(userService.findPage(securityUser, queryParam));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询烟叶初考用户功能异常：" + e);
            throw new BusinessException("查询烟叶初考用户功能异常");
        }
    }


    @PostMapping(value = "/saveOrUpdateYYCKUser")
    @AuditLog(operation = "添加或更新烟叶初考用户")
    @ApiOperation(value = "添加或更新烟叶初考用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "user", value = "需要修改的User对象", dataType = "User", required = true)
    })
    public CommonResult<User> saveOrUpdateYYCKUser(@LoginUser SecurityUser securityUser, @RequestBody @Valid User user) {
        if (securityUser == null) {
            throw new BusinessException("更新失败，请先登录！");
        }
        try {
            System.out.println("securityUser:\n" + securityUser);
            System.out.println("user:\n" + user);
            return CommonResult.succeed(userService.saveOrUpdateUser(securityUser, user));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("添加或更新烟叶初考用户功能异常：" + e);
            throw new BusinessException("添加或更新烟叶初考用户功能异常");
        }
    }


    @PostMapping(value = "/deleteYYCKUser")
    @AuditLog(operation = "删除烟叶初考用户")
    @ApiOperation(value = "删除烟叶初考用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "id", value = "用户表中的唯一标识", dataType = "String", required = true)
    })
    public CommonResult deleteYYCKUser(@LoginUser SecurityUser securityUser, @RequestBody Map<String, String> id) {
        if (securityUser == null) {
            throw new BusinessException("删除失败，请先登录！");
        }
        try {
            return userService.deleteUserById(id.get("id")) > 0 ? CommonResult.succeed("删除成功") : CommonResult.failed("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("删除烟叶初考用户功能异常：" + e);
            throw new BusinessException("删除烟叶初考用户功能异常");
        }
    }

    @PostMapping(value = "/searchYYCKUserLike")
    @AuditLog(operation = "模糊查询烟叶初考用户")
    @ApiOperation(value = "模糊查询烟叶初考用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "queryParam", value = "过滤条件", dataType = "UserQueryLikeParam", required = false)
    })
    public PageResult<UserDto> searchYYCKUserLike(@LoginUser SecurityUser securityUser, @RequestBody UserQueryLikeParam queryParam) {
        if (securityUser == null) {   // 检查用户是否登录
            throw new BusinessException("删除失败，请先登录！");
        }
        try {
            return PageResult.succeed(userService.searchByUserQueryLike(securityUser, queryParam));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("烟叶初考用户模糊查询功能异常：" + e);
            throw new BusinessException("烟叶初考用户模糊查询功能异常");
        }
    }
}
