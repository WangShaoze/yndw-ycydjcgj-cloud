package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.factory.XtYyxxFactory;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtYyxxQueryParam;
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
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/yyxx")
@Api(tags = "应用管理")
public class XtYyxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtYyxxController.class);
    @Autowired
    private IXtYyxxService yyxxService;


    //已授权
    @GetMapping(value = "/page")
    @AuditLog(operation = "'查询应用:' + #queryParam")
    @ApiOperation(value = "查询应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtYyxxQueryParam", required = false)
    })
    public PageResult<XtYyxxDto> page(XtYyxxQueryParam queryParam) {
        try {
            Page<XtYyxx> page = yyxxService.queryPage(queryParam);
            return PageResult.succeed(XtYyxxFactory.buildDto(page));
        } catch (Exception e) {
            logger.debug("查询应用异常：" + e);
            throw new BusinessException("查询应用异常");
        }
    }

    //未授权
    @GetMapping(value = "/wsqPage")
    @AuditLog(operation = "'查询未授权应用:' + #queryParam")
    @ApiOperation(value = "查询未授权应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtYyxxQueryParam", required = false)
    })
    public PageResult<XtYyxxDto> WsqPage(XtYyxxQueryParam queryParam) {
        try {
            Page<XtYyxx> page = yyxxService.WsqQueryPage(queryParam);
            return PageResult.succeed(XtYyxxFactory.buildDto(page));
        } catch (Exception e) {
            logger.debug("查询应用异常：" + e);
            throw new BusinessException("查询应用异常");
        }
    }

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询应用")
    @ApiOperation(value = "查询应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtYyxxQueryParam", required = false)
    })
    public CommonResult<List<XtYyxxDto>> list(XtYyxxQueryParam queryParam) {

        try {
            List<XtYyxx> entityList = yyxxService.findList(queryParam);
            return CommonResult.succeed(XtYyxxFactory.build(entityList));
        } catch (Exception e) {
            logger.debug("查询功能异常：" + e);
            throw new BusinessException("查询功能异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新应用信息")
    @ApiOperation(value = "添加或更新应用信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "应用信息", dataType = "XtYyxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtYyxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtYyxx result = yyxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "应用成功") : CommonResult.failed(msg + "应用失败");
        } catch (Exception e) {
            logger.debug("添加或更新应用异常：" + e);
            throw new BusinessException("添加或更新应用异常：" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除功能")
    @ApiOperation(value = "删除功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "应用编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return yyxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除功能成功") : CommonResult.failed("删除功能失败");
        } catch (Exception e) {
            logger.debug("删除功能异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新应用状态")
    @ApiOperation(value = "更新应用状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统XtCzyxx自动获取", dataType = "", required = false),
            @ApiImplicitParam(name = "entity", value = "应用信息", dataType = "XtYyxx", required = true)
    })
    public CommonResult<XtYyxx> updateZtdm(@LoginUser SecurityUser securityUser, @RequestBody XtYyxx entity) {
        try {
            return yyxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新功能状态成功") : CommonResult.failed("更新功能状态失败");
        } catch (Exception e) {
            logger.debug("更新应用状态异常：" + e);
            throw new BusinessException("更新应用状态异常");
        }
    }

    @PutMapping(value = "/updateAvatar")
    @AuditLog(operation = "更新图标")
    @ApiOperation(value = "更新图标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "应用信息", dataType = "XtYyxx", required = true)
    })
    public CommonResult<XtYyxx> updateAvatar(@LoginUser SecurityUser securityUser, XtYyxx entity) {
        try {
            return yyxxService.updateAvatar(securityUser, entity) > 0 ? CommonResult.succeed("更新图标成功") : CommonResult.failed("更新图标失败");
        } catch (Exception e) {
            logger.debug("更新图标异常：" + e);
            throw new BusinessException("更新图标异常");
        }
    }

    @GetMapping(value = "/yyxxNum")
    @AuditLog(operation = "查询应用")
    @ApiOperation(value = "查询应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Num", value = "过滤条件", dataType = "XtYyxxQueryParam", required = false)
    })
    public CommonResult<List<XtYyxx>> getYyxxNum(String sjbh) {
        try {
            List<XtYyxx> xtYyxxList = yyxxService.getYyxxNum(sjbh);
            return CommonResult.succeed(xtYyxxList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询功能异常：" + e);
            throw new BusinessException("查询功能异常");
        }
    }

    @GetMapping(value = "/yyxxCount")
    @AuditLog(operation = "查询应用信息总数、启用数和停用数")
    @ApiOperation(value = "查询应用信息总数、启用数和停用数")
    public CommonResult<XtYyxxCountDto> gwxxCount() {
        try {
            XtYyxxCountDto xtYyxxCountDto = yyxxService.yyxxCount();
            return (xtYyxxCountDto != null)?CommonResult.succeed(xtYyxxCountDto):CommonResult.failed("查询应用信息总数、启用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询应用信息总数、启用数和停用数异常：" + e);
            throw new BusinessException("查询应用信息总数、启用数和停用数异常");
        }
    }

    @GetMapping(value = "/yyFlCount/{yyztdm}")
    @AuditLog(operation = "查询启动应用分类计数")
    @ApiOperation(value = "查询启动应用分类计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yyztdm", value = "应用状态代码", dataType = "String", required = true)
    })
    public CommonResult<List<XtYyFlCountDto>> yyFlCount(@PathVariable(value = "yyztdm", required = true) String yyztdm) {
        try {
            List<XtYyFlCountDto> xtYyFlCountlist = yyxxService.yyFlCount(yyztdm);
            return (xtYyFlCountlist != null)?CommonResult.succeed(xtYyFlCountlist):CommonResult.failed("查询应用分类计数失败");
        } catch (Exception e) {
            logger.debug("查询应用分类计数异常：" + e);
            throw new BusinessException("查询应用分类计数异常");
        }
    }
    @GetMapping(value = "/jsfyyxxNum")
    @AuditLog(operation = "查询启动应用分类计数")
    @ApiOperation(value = "查询启动应用分类计数")
    public CommonResult<List<JsfYyxxCountDto>> JsfyyCount() {
        try {
            List<JsfYyxxCountDto> jsfYyCountlist = yyxxService.getjsfYyxxNum();
           return (jsfYyCountlist != null)?CommonResult.succeed(jsfYyCountlist):CommonResult.failed("查询建设方应用分类计数失败");
        } catch (Exception e) {
            logger.debug("查询应用分类计数异常：" + e);
            throw new BusinessException("查询建设方应用分类计数异常");
        }
    }

    @GetMapping(value = "/cjfyyxxNum")
    @AuditLog(operation = "查询承建方应用分类计数")
    @ApiOperation(value = "查询承建方应用分类计数")
    public CommonResult<List<CjfYyxxCountDto>> cjfyyCount() {
        try {
            List<CjfYyxxCountDto> cjfYyCountlist = yyxxService.getcjfYyxxNum();
            return (cjfYyCountlist != null)?CommonResult.succeed(cjfYyCountlist):CommonResult.failed("查询承建方应用分类计数失败");
        } catch (Exception e) {
            logger.debug("查询应用分类计数异常：" + e);
            throw new BusinessException("查询承建方应用分类计数异常");
        }
    }
}
