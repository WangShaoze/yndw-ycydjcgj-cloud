package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxTreeDto;
import com.yndw.dvp.xtgl.auth.factory.XtZzxxFactory;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtZzxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtGwxxService;
import com.yndw.dvp.xtgl.auth.service.IXtZzxxService;
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
import java.util.Map;

/**
 * Create By Carlos
 * 2020/6/12
 */
@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/zzxx")
@Api(tags = "组织管理")
public class XtZzxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtZzxxController.class);
    @Autowired
    private IXtZzxxService zzxxService;
    @Autowired
    private IXtGwxxService gwxxService;

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询组织")
    @ApiOperation(value = "查询组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtZzxxQueryParam", required = false)
    })
    public CommonResult<List<XtZzxxTreeDto>> list(@LoginUser SecurityUser loginUser, XtZzxxQueryParam queryParam) {
        try {
            return CommonResult.succeed(XtZzxxFactory.build(zzxxService.findList(loginUser,queryParam)));
        } catch (Exception e) {
            logger.debug("查询组织异常：" + e);
            throw new BusinessException("查询组织异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新组织")
    @ApiOperation(value = "添加或更新组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "组织信息", dataType = "XtZzxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtZzxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtZzxx result = zzxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "组织成功") : CommonResult.failed(msg + "组织失败");
        } catch (Exception e) {
            logger.debug("添加或更新组织异常：" + e);
            throw new BusinessException("添加或更新组织异常");
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除组织")
    @ApiOperation(value = "删除组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "组织编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return zzxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除组织成功") : CommonResult.failed("删除组织失败");
        } catch (Exception e) {
            logger.debug("删除组织异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取组织")
    @ApiOperation(value = "获取组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "组织编号", dataType = "String", required = true)
    })
    public CommonResult<XtZzxx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtZzxx entity = zzxxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取组织成功") : CommonResult.failed("获取组织失败");
        } catch (Exception e) {
            logger.debug("获取组织异常：" + e);
            throw new BusinessException("获取组织异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新组织状态")
    @ApiOperation(value = "更新组织状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "组织信息", dataType = "XtZzxx", required = true)
    })
    public CommonResult<XtZzxx> updateZtdm(@LoginUser SecurityUser securityUser, @RequestBody XtZzxx entity) {
        try {
            return zzxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新组织状态成功") : CommonResult.failed("更新组织状态失败");
        } catch (Exception e) {
            logger.debug("更新组织状态异常：" + e);
            throw new BusinessException("更新组织状态异常");
        }
    }

    @GetMapping(value = "/zzxxList4Type")
    @AuditLog(operation = "查询组织")
    @ApiOperation(value = "查询组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtZzxxQueryParam", required = false)
    })
    public CommonResult<List<XtZzxxTreeDto>> zzxxList4Type(@LoginUser SecurityUser securityUser, @RequestParam("type") String type) {
        try {
            return CommonResult.succeed(XtZzxxFactory.build(zzxxService.zzxxList4Type(type, securityUser)));
        } catch (Exception e) {
            logger.debug("查询组织异常：" + e);
            throw new BusinessException("查询组织异常");
        }
    }


    @GetMapping(value = "/zzxxCount")
    @AuditLog(operation = "查询组织信息总数、启用数和停用数")
    @ApiOperation(value = "查询组织信息总数、启用数和停用数")
    public CommonResult<XtZzxxCountDto> gwxxCount() {
        try {
            XtZzxxCountDto xtZzxxCountDto = zzxxService.zzxxCount();
            return (xtZzxxCountDto != null)?CommonResult.succeed(xtZzxxCountDto):CommonResult.failed("查询组织信息总数、启用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询组织信息总数、启用数和停用数异常：" + e);
            throw new BusinessException("查询组织信息总数、启用数和停用数异常");
        }
    }

    @GetMapping(value = "/getZzByZzbhList")
    @AuditLog(operation = "查询组织信息")
    @ApiOperation(value = "查询组织信息")
    public List<XtZzxx> getZzByZzbhList(@RequestParam("zzbhStr") List<String> zzbhStr) {
        try {
            return zzxxService.getZzByZzbhList(zzbhStr);
        } catch (Exception e) {
            logger.debug("查询组织信息异常：" + e);
            throw new BusinessException("查询组织信息异常");
        }
    }

}
