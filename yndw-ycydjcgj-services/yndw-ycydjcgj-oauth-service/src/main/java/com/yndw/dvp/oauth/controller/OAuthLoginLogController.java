package com.yndw.dvp.oauth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.utils.ExcelUtil;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.oauth.dto.OAuthLogExcelDto;
import com.yndw.dvp.oauth.factory.LoginLogFactory;
import com.yndw.dvp.oauth.service.IOAuthLoginLogService;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.queryParam.OAuthLoginLogQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/yndw/oauth/yndw-ycydjcgj-oauth-service/V1/loginLog")
@Api(tags = "登录日志系统")
public class OAuthLoginLogController {
    private static final Logger logger = LoggerFactory.getLogger(OAuthLoginLogController.class);
    @Autowired
    private IOAuthLoginLogService loginLogService;

    @ApiOperation(value = "查询登录日志")
    @AuditLog(operation = "查询登录日志")
    @GetMapping(value = "/page")
    public PageResult<OAuthLoginLog> page(OAuthLoginLogQueryParam queryParam) {
        try {
            Page<OAuthLoginLog> page = loginLogService.findPage(queryParam);
            return PageResult.succeed(page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询登录日志异常：" + e);
            throw new BusinessException("查询登录日志异常");
        }
    }

    @PostMapping("/exportLog")
    public void exportLog(OAuthLoginLogQueryParam queryParam, HttpServletResponse response) {
        try {
            List<OAuthLoginLog> oauthLogList = loginLogService.findList(queryParam);

            //导出操作
            ExcelUtil.exportExcel(LoginLogFactory.buildExcelDto(oauthLogList), "登录日志", "登录日志", OAuthLogExcelDto.class, "登录信息", response);
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("导出日志异常：" + e);
            throw new BusinessException("导出日志异常");
        }

    }
}