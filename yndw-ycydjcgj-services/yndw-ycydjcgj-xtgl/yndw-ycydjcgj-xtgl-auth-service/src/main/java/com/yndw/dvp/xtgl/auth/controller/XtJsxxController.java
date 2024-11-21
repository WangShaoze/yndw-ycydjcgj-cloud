package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.dto.ZTreeDto;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.GnToJs;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxDto;
import com.yndw.dvp.xtgl.auth.factory.XtGnxxFactory;
import com.yndw.dvp.xtgl.auth.factory.XtJsxxFactory;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.queryParam.XtJsxxQueryParam;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;
import com.yndw.dvp.xtgl.auth.service.IXtGnxxService;
import com.yndw.dvp.xtgl.auth.service.IXtJsxxService;
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
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/jsxx")
@Api(tags = "角色管理")
public class XtJsxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtJsxxController.class);
    @Autowired
    private IXtJsxxService jsxxService;
    @Autowired
    private IXtGnxxService gnxxService;

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询角色")
    @ApiOperation(value = "查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtJsxxQueryParam", required = false)
    })
    public CommonResult<List<XtJsxxDto>> list(XtJsxxQueryParam queryParam) {
        try {
            return CommonResult.succeed(XtJsxxFactory.build(jsxxService.findList(queryParam)));
        } catch (Exception e) {
            logger.debug("查询角色异常：" + e);
            throw new BusinessException("查询角色异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新角色")
    @ApiOperation(value = "添加或更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "角色信息", dataType = "XtJsxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtJsxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtJsxx result = jsxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "角色成功") : CommonResult.failed(msg + "角色失败");
        } catch (Exception e) {
            logger.debug("添加或更新角色异常：" + e);
            throw new BusinessException("添加或更新角色异常");
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除角色")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "角色编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return jsxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除角色成功") : CommonResult.failed("删除角色失败");
        } catch (Exception e) {
            logger.debug("删除角色异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取角色")
    @ApiOperation(value = "获取角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "角色编号", dataType = "String", required = true)
    })
    public CommonResult<XtJsxx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtJsxx entity = jsxxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取角色成功") : CommonResult.failed("获取角色失败");
        } catch (Exception e) {
            logger.debug("获取角色异常：" + e);
            throw new BusinessException("获取角色异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新角色状态")
    @ApiOperation(value = "更新角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "角色信息", dataType = "XtJsxx", required = true)
    })
    public CommonResult<XtJsxx> updateZtdm(@LoginUser SecurityUser securityUser, @RequestBody XtJsxx entity) {
        try {
            return jsxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新角色状态成功") : CommonResult.failed("更新角色状态失败");
        } catch (Exception e) {
            logger.debug("更新角色状态异常：" + e);
            throw new BusinessException("更新角色状态异常");
        }
    }

    @PostMapping(value = "/setGnxxToJsxx")
    @AuditLog(operation = "角色分配功能")
    @ApiOperation(value = "角色分配功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "gwbh", value = "岗位编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "jsbhs", value = "多角色编号", dataType = "String", required = true)
    })
    public CommonResult<XtGwxx> setGnxxToJsxx(@RequestBody GnToJs entity) {
        try {
            jsxxService.setGnxxToJsxx(entity);
            return CommonResult.succeed("角色分配功能成功");
        } catch (Exception e) {
            logger.debug("角色分配功能异常：" + e);
            throw new BusinessException("角色分配功能异常");
        }
    }

    @GetMapping(value = "/getJsGnList")
    @AuditLog(operation = "获取角色已分配功能")
    @ApiOperation(value = "获取角色已分配功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "岗位信息", dataType = "XtGwxx", required = true)
    })
    public CommonResult<List<ZTreeDto>> getGwJsList(@LoginUser SecurityUser securityUser, @RequestParam String bh, @RequestParam String ssyybh) {
        try {
            String jsgns = jsxxService.getGnbhsByJsbh(bh);
            List<XtGnxx> gnxxList = gnxxService.findList(new XtGnxxQueryParam(null, ssyybh));
            return CommonResult.succeed(XtGnxxFactory.buildZTree(jsgns, gnxxList));
        } catch (Exception e) {
            logger.debug("获取角色已分配功能异常：" + e);
            throw new BusinessException("获取角色已分配功能异常");
        }
    }

    @GetMapping(value = "/jsxxCount")
    @AuditLog(operation = "查询角色信息总数、禁用数和停用数")
    @ApiOperation(value = "查询角色信息总数、禁用数和停用数")
    public CommonResult<XtJsxxCountDto> gwxxCount() {
        try {
            XtJsxxCountDto xtJsxxCountDto = jsxxService.jsxxCount();
            return (xtJsxxCountDto != null)?CommonResult.succeed(xtJsxxCountDto):CommonResult.failed("查询角色信息总数、禁用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询角色信息总数、禁用数和停用数异常：" + e);
            throw new BusinessException("查询角色信息总数、禁用数和停用数异常");
        }
    }
}
