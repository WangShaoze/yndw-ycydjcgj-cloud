package com.yndw.dvp.xtgl.dm.controller;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.dm.dto.XtDmflDto;
import com.yndw.dvp.xtgl.dm.factory.XtDmflFactory;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;
import com.yndw.dvp.xtgl.dm.service.IXtDmflService;
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
 * 2020/7/9
 */

@RestController
@RequestMapping("/yndw/xtgl/dm/yndw-ycydjcgj-xtgl-dm-service/V1/dmfl")
@Api(tags = "代码分类管理")
public class XtDmflController {
    private static final Logger logger = LoggerFactory.getLogger(XtDmflController.class);
    @Autowired
    private IXtDmflService dmflService;

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询代码分类")
    @ApiOperation(value = "查询代码分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtDmflQueryParam", required = false)
    })
    public CommonResult<List<XtDmflDto>> list(XtDmflQueryParam queryParam) {
        try {
            return CommonResult.succeed(XtDmflFactory.build(dmflService.findList(queryParam)));
        } catch (Exception e) {
            logger.debug("查询代码异常：" + e);
            throw new BusinessException("查询代码异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新代码分类")
    @ApiOperation(value = "添加或更新代码分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "代码分类信息", dataType = "XtDmfl", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtDmfl entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtDmfl result = dmflService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "代码成功") : CommonResult.failed(msg + "代码失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("添加或更新代码异常：" + e);
            throw new BusinessException("添加或更新代码异常");
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除代码")
    @ApiOperation(value = "删除代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "代码编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return dmflService.deleteById(bh) > 0 ? CommonResult.succeed("删除代码成功") : CommonResult.failed("删除代码失败");
        } catch (Exception e) {
            logger.debug("删除代码异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取代码")
    @ApiOperation(value = "获取代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "代码编号", dataType = "String", required = true)
    })
    public CommonResult<XtDmfl> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtDmfl entity = dmflService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取代码成功") : CommonResult.failed("获取代码失败");
        } catch (Exception e) {
            logger.debug("获取代码异常：" + e);
            throw new BusinessException("获取代码异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新代码状态")
    @ApiOperation(value = "更新代码状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "代码信息", dataType = "XtDmfl", required = true)
    })
    public CommonResult<XtDmfl> updateZtdm(@LoginUser SecurityUser securityUser, XtDmfl entity) {
        try {
            return dmflService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新代码状态成功") : CommonResult.failed("更新代码状态失败");
        } catch (Exception e) {
            logger.debug("更新代码状态异常：" + e);
            throw new BusinessException("更新代码状态异常");
        }
    }
}
