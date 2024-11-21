package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.factory.XtGwxxFactory;
import com.yndw.dvp.xtgl.auth.factory.XtJsxxFactory;
import com.yndw.dvp.xtgl.auth.feign.IMallYyxxService;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGwxxQueryParam;
import com.yndw.dvp.xtgl.auth.queryParam.XtJsxxQueryParam;
import com.yndw.dvp.xtgl.auth.queryParam.XtYyxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtGwxxService;
import com.yndw.dvp.xtgl.auth.service.IXtJsxxService;
import com.yndw.dvp.xtgl.auth.service.IXtYyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/gwxx")
@Api(tags = "岗位管理")
public class XtGwxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtGwxxController.class);
    @Autowired
    private IXtGwxxService gwxxService;
    @Autowired
    private IXtJsxxService jsxxService;
    @Autowired
    private IMallYyxxService yyxxService;

    @Autowired
    private IXtYyxxService xtYyxxService;

    @GetMapping(value = "/page")
    @AuditLog(operation = "查询岗位")
    @ApiOperation(value = "查询岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGwxxQueryParam", required = false)
    })
    public PageResult<XtGwxxDto> page(XtGwxxQueryParam queryParam) {
        try {
            return PageResult.succeed(XtGwxxFactory.build(gwxxService.findPage(queryParam)));
        } catch (Exception e) {
            logger.debug("查询岗位异常：" + e);
            throw new BusinessException("查询岗位异常");
        }
    }

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询岗位")
    @ApiOperation(value = "查询岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGwxxQueryParam", required = false)
    })
    public CommonResult<List<XtGwxxDto>> list(XtGwxxQueryParam queryParam) {
        try {
            return CommonResult.succeed(XtGwxxFactory.build(gwxxService.findList(queryParam)));
        } catch (Exception e) {
            logger.debug("查询岗位异常：" + e);
            throw new BusinessException("查询岗位异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新岗位")
    @ApiOperation(value = "添加或更新岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "岗位信息", dataType = "XtGwxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtGwxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtGwxx result = gwxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "岗位成功") : CommonResult.failed(msg + "岗位失败");
        } catch (Exception e) {
            logger.debug("添加或更新岗位异常：" + e);
            throw new BusinessException("添加或更新岗位异常");
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除岗位")
    @ApiOperation(value = "删除岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "岗位编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return gwxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除功能成功") : CommonResult.failed("删除功能失败");
        } catch (Exception e) {
            logger.debug("删除岗位异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取岗位")
    @ApiOperation(value = "获取岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "岗位编号", dataType = "String", required = true)
    })
    public CommonResult<XtGwxx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtGwxx entity = gwxxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取岗位成功") : CommonResult.failed("获取岗位失败");
        } catch (Exception e) {
            logger.debug("获取岗位异常：" + e);
            throw new BusinessException("获取岗位异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新岗位状态")
    @ApiOperation(value = "更新岗位状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "岗位信息", dataType = "XtGwxx", required = true)
    })
    public CommonResult<XtGwxx> updateZtdm(@LoginUser SecurityUser securityUser, @RequestBody XtGwxx entity) {
        try {
            return gwxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新岗位状态成功") : CommonResult.failed("更新岗位状态失败");
        } catch (Exception e) {
            logger.debug("更新岗位状态异常：" + e);
            throw new BusinessException("更新岗位状态异常");
        }
    }

    @PutMapping(value = "/setJsxxToGwxx")
    @AuditLog(operation = "岗位分配角色")
    @ApiOperation(value = "岗位分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "gwbh", value = "岗位编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "jsbhs", value = "多角色编号", dataType = "String", required = true)
    })
    public CommonResult<XtGwxx> setJsxxToGwxx(@LoginUser SecurityUser securityUser, @RequestParam String gwbh, @RequestParam String jsbhs) {
        try {
            gwxxService.setJsxxToGwxx(gwbh, jsbhs);
            return CommonResult.succeed("岗位分配角色成功");
        } catch (Exception e) {
            logger.debug("岗位分配角色异常：" + e);
            throw new BusinessException("岗位分配角色异常");
        }
    }

    @GetMapping(value = "/getGwjsList")
    @AuditLog(operation = "获取岗位已分配角色")
    @ApiOperation(value = "获取岗位已分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "bh", value = "岗位信息", dataType = "XtGwxx", required = true)
    })
    public CommonResult<List<YyUITreeDto>> getGwJsList(@RequestParam String bh) {
        try {
            String gwyys = gwxxService.getYybhsByGwbh(bh);
            String gwjss = gwxxService.getJsbhsByGwbh(bh);
            List<XtJsxx> jsxxList = jsxxService.findList(new XtJsxxQueryParam());
            List<XtYyxx> yyxxList = xtYyxxService.findList(new XtYyxxQueryParam());
            return CommonResult.succeed(XtJsxxFactory.buildJsxxTree(gwyys, gwjss, jsxxList, yyxxList));
        } catch (Exception e) {
            logger.debug("获取岗位已分配角色异常：" + e);
            throw new BusinessException("获取岗位已分配角色异常");
        }
    }

    @GetMapping(value = "/gwxxCount")
    @AuditLog(operation = "查询岗位信息总数、启用数和停用数")
    @ApiOperation(value = "查询岗位信息总数、启用数和停用数")
    public CommonResult<XtGwxxCountDto> gwxxCount() {
        try {
            XtGwxxCountDto xtGwxxCountDto = gwxxService.gwxxCount();
            return (xtGwxxCountDto != null)?CommonResult.succeed(xtGwxxCountDto):CommonResult.failed("查询岗位信息总数、启用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询岗位信息总数、启用数和停用数异常：" + e);
            throw new BusinessException("查询岗位信息总数、启用数和停用数异常");
        }
    }
}
