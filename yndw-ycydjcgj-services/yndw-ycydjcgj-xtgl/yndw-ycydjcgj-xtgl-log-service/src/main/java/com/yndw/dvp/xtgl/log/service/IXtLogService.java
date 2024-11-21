package com.yndw.dvp.xtgl.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.log.model.XtLog;
import com.yndw.dvp.xtgl.log.queryParam.XtLogQueryParam;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/8
 */
public interface IXtLogService extends ISuperService<XtLog> {
    Page<XtLog> findPage(XtLogQueryParam queryParam);

    List<XtLog> findList(XtLogQueryParam queryParam);
}
