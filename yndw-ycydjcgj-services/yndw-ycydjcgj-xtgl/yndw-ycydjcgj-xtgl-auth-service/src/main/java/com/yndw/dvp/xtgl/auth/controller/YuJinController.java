package com.yndw.dvp.xtgl.auth.controller;

import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.model.YuJin;
import com.yndw.dvp.xtgl.auth.service.YuJinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/ydlyzgl")
@Api(tags = "用电量阈值管理")
public class YuJinController {
    private static final Logger logger = LoggerFactory.getLogger(XtCzyqxController.class);

    @Autowired
    private YuJinService yuJinService;

    @PostMapping(value = "/updateYuJinZhi")
    @AuditLog(operation = "更新预警值")
    @ApiOperation(value = "更新预警值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false)
    })
    public CommonResult updateYuJinZhi(@LoginUser SecurityUser securityUser, @RequestBody Map<String, String> data) {
        try {
            String yiJiVal = data.get("yi_ji_val");
            String erJiVal = data.get("er_ji_val");
            YuJin result = yuJinService.updateYuJin(securityUser, yiJiVal, erJiVal);
            return result != null ? CommonResult.succeed(result, "阈值修改成功！") : CommonResult.failed("阈值修改失败！");
        } catch (Exception e) {
            logger.debug("更新预警值功能异常：" + e);
            throw new BusinessException("更新预警值功能异常");
        }
    }


    @PostMapping(value = "/searchYuJinZhi")
    @AuditLog(operation = "查询预警值")
    @ApiOperation(value = "查询预警值")
    public CommonResult<YuJin> searchYuJinZhi() {
        try {
            return CommonResult.succeed(yuJinService.findYuJin());
        } catch (Exception e) {
            logger.debug("查询预警值功能异常：" + e);
            throw new BusinessException("查询预警值功能异常");
        }
    }
}
