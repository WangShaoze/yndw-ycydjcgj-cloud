package com.yndw.dvp.xtgl.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.utils.ExcelUtil;
import com.yndw.dvp.xtgl.log.dto.SysLogExcelDto;
import com.yndw.dvp.xtgl.log.factory.SysLogExcelFactory;
import com.yndw.dvp.xtgl.log.model.XtLog;
import com.yndw.dvp.xtgl.log.queryParam.XtLogQueryParam;
import com.yndw.dvp.xtgl.log.service.IXtLogService;
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

/**
 * Create By Carlos
 * 2020/7/8
 */
@RestController
@RequestMapping("/yndw/xtgl/log/yndw-ycydjcgj-xtgl-log-service/V1/log")
@Api(tags = "业务日志系统")
public class XtLogController {
    private static final Logger logger = LoggerFactory.getLogger(XtLogController.class);
    @Autowired
    private IXtLogService logService;
    @ApiOperation(value = "业务日志列表")
    @GetMapping(value = "/page")
    public PageResult<XtLog> page(XtLogQueryParam queryParam) {
        try {
            Page<XtLog> page = logService.findPage(queryParam);
            return PageResult.succeed(page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询业务日志异常：" + e);
            throw new BusinessException("查询业务日志异常");
        }
    }

    @PostMapping("/exportLog")
    public void exportLog(XtLogQueryParam queryParam, HttpServletResponse response) {
        try {
            List<XtLog> LogList = logService.findList(queryParam);

            //导出操作
            ExcelUtil.exportExcel(SysLogExcelFactory.buildExcelDto(LogList), "业务日志", "业务日志", SysLogExcelDto.class, "业务日志", response);
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("导出日志异常：" + e);
            throw new BusinessException("导出日志异常");
        }

    }
}
