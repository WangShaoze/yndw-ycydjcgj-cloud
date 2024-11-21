package com.yndw.dvp.xtgl.dm.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.dm.dto.XtDmflDto;
import com.yndw.dvp.xtgl.dm.dto.XtDmflmxDto;
import com.yndw.dvp.xtgl.dm.factory.XtDmflFactory;
import com.yndw.dvp.xtgl.dm.factory.XtDmflmxFactory;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflmxQueryParam;
import com.yndw.dvp.xtgl.dm.service.IXtDmflService;
import com.yndw.dvp.xtgl.dm.service.IXtDmflmxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Create By Carlos
 * 2020/7/9
 */

@RestController
@RequestMapping("/yndw/xtgl/dm/yndw-ycydjcgj-xtgl-dm-service/V1/dmflmx")
@Api(tags = "代码分类管理")
public class XtDmflmxController {
    private static final Logger logger = LoggerFactory.getLogger(XtDmflmxController.class);
    @Autowired
    private IXtDmflmxService dmflmxService;

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询代码明细")
    @ApiOperation(value = "查询代码明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtDmflmxQueryParam", required = false)
    })
    public CommonResult<List<XtDmflmxDto>> list(XtDmflmxQueryParam queryParam) {
        try {
            return CommonResult.succeed(XtDmflmxFactory.build(dmflmxService.findList(queryParam)));
        } catch (Exception e) {
            logger.debug("查询代码异常：" + e);
            throw new BusinessException("查询代码异常");
        }
    }

    @GetMapping(value = "/findMenuBySjbh")
    @AuditLog(operation = "查询代码明细")
    @ApiOperation(value = "查询代码明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtDmflmxQueryParam", required = false)
    })
    public List<XtDmflmx> findMenuBySjbh(String sjbh) {
        try {
            return dmflmxService.findMenuBySjbh(sjbh);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询代码异常：" + e);
            throw new BusinessException("查询代码异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新代码明细")
    @ApiOperation(value = "添加或更新代码明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "代码明细信息", dataType = "XtDmflmx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtDmflmx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtDmflmx result = dmflmxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "代码明细成功") : CommonResult.failed(msg + "代码明细失败");
        } catch (Exception e) {
            logger.debug("添加或更新代码明细异常：" + e);
            throw new BusinessException("添加或更新代码明细异常:" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除代码明细")
    @ApiOperation(value = "删除代码明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "代码编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return dmflmxService.deleteById(bh) > 0 ? CommonResult.succeed("删除代码明细成功") : CommonResult.failed("删除代码明细失败");
        } catch (Exception e) {
            logger.debug("删除代码明细异常：" + e);
            throw new BusinessException("删除明细异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取代码明细")
    @ApiOperation(value = "获取代码明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "代码编号", dataType = "String", required = true)
    })
    public CommonResult<XtDmflmx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtDmflmx entity = dmflmxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取代码成功明细") : CommonResult.failed("获取代码明细失败");
        } catch (Exception e) {
            logger.debug("获取代码明细异常：" + e);
            throw new BusinessException("获取代码明细异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新代码明细状态")
    @ApiOperation(value = "更新代码明细状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "代码明细信息", dataType = "XtDmflmx", required = true)
    })
    public CommonResult<XtDmflmx> updateZtdm(@LoginUser SecurityUser securityUser, XtDmflmx entity) {
        try {
            return dmflmxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新代码明细状态成功") : CommonResult.failed("更新代码状态失败");
        } catch (Exception e) {
            logger.debug("更新代码明细状态异常：" + e);
            throw new BusinessException("更新代码明细状态异常");
        }
    }
}
