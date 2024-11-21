package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.*;
import com.yndw.dvp.common.log.annotation.AuditLog;

import com.yndw.dvp.xtgl.auth.dto.XtGgtzxxDto;
import com.yndw.dvp.xtgl.auth.factory.XtGgtzxxFactory;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.model.XtGgtzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGgtzxxQueryParam;

import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import com.yndw.dvp.xtgl.auth.service.IXtGgtzxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Create By Liangkh
 * 2021/4/23
 */
@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/ggtzxx")
@Api(tags = "公告管理")
public class XtGgtzxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtGgtzxxController.class);
    @Autowired
    private IXtGgtzxxService ggtzxxService;
    @Autowired
    private IXtCzyxxService xtCzyxxService;

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询公告")
    @ApiOperation(value = "查询公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGgtzxxQueryParam", required = false)
    })
    public CommonResult<List<XtGgtzxx>> list(XtGgtzxxQueryParam queryParam) {
        try {
            List<XtGgtzxx> entityList = ggtzxxService.findList(queryParam);
            return CommonResult.succeed(entityList);
        } catch (Exception e) {
            logger.debug("查询公告异常：" + e);
            throw new BusinessException("查询公告异常");
        }
    }

    @GetMapping(value = "/pagelist")
    @AuditLog(operation = "查询公告")
    @ApiOperation(value = "查询公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGgtzxxQueryParam", required = false)
    })
    public PageResult<XtGgtzxxDto> pageList(@LoginUser SecurityUser securityUser, XtGgtzxxQueryParam queryParam) {
        try {
            Page<XtGgtzxx> page = ggtzxxService.pageList(securityUser, queryParam);
            return PageResult.succeed(XtGgtzxxFactory.buildDto(page));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询公告异常：" + e);
            throw new BusinessException("查询公告异常");
        }

    }

    @GetMapping(value = "/page")
    @AuditLog(operation = "查询公告")
    @ApiOperation(value = "查询公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGgtzxxQueryParam", required = false)
    })
    public PageResult<XtGgtzxxDto> page(@LoginUser SecurityUser securityUser, XtGgtzxxQueryParam queryParam) {
        try {
            Page<XtGgtzxx> page = ggtzxxService.findPage(securityUser, queryParam);
            return PageResult.succeed(XtGgtzxxFactory.buildDto(page));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询公告异常：" + e);
            throw new BusinessException("查询公告异常");
        }

    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新公告")
    @ApiOperation(value = "添加或更新公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "公告信息", dataType = "XtGgtzxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtGgtzxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtGgtzxx result = ggtzxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "添加成功") : CommonResult.failed(msg + "添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("添加或修改公告异常：" + e);
            throw new BusinessException("添加或修改公告异常");
        }
    }


    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除公告")
    @ApiOperation(value = "删除公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "公告编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return ggtzxxService.deleteByID(bh) > 0 ? CommonResult.succeed("删除公告成功") : CommonResult.failed("删除公告失败");
        } catch (Exception e) {
            logger.debug("删除公告异常：" + e);
            throw new BusinessException("删除公告");
        }
    }

    @PostMapping(value = "/put")
    @AuditLog(operation = "添加或更新公告")
    @ApiOperation(value = "添加或更新公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "公告信息", dataType = "XtGgtzxx", required = true)
    })
    public CommonResult put(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtGgtzxx entity) {
        try {


            List<String> ids = new ArrayList();
            List<XtCzyxx> lists = xtCzyxxService.list();
            for (XtCzyxx xtCzyxx : lists) {
                ids.add(xtCzyxx.getBh());
            }
            XtGgtzxx result = ggtzxxService.put(securityUser, entity, ids);
            if (result != null) {
                return CommonResult.succeed(result, "发布成功");
            } else {
                return CommonResult.succeed(result, "发布失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("发布公告异常：" + e);
            throw new BusinessException("发布异常");
        }
    }

    @PostMapping(value = "/read")
    @AuditLog(operation = "变更公告状态")
    @ApiOperation(value = "变更公告状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "公告信息", dataType = "XtGgtzxx", required = true)
    })
    public CommonResult read(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtGgtzxx entity) {
        try {

            XtGgtzxx result = ggtzxxService.read(securityUser, entity);
            return result != null ? CommonResult.succeed(result, "状态变更成功") : CommonResult.failed("状态变更失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("状态变更异常：" + e);
            throw new BusinessException("状态变更异常");
        }
    }


}
