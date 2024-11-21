package com.yndw.dvp.xtgl.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.log.mapper.XtLogMapper;
import com.yndw.dvp.xtgl.log.model.XtLog;
import com.yndw.dvp.xtgl.log.queryParam.XtLogQueryParam;
import com.yndw.dvp.xtgl.log.service.IXtLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/8
 */
@Slf4j
@Service
public class XtLogServiceImpl extends SuperServiceImpl<XtLogMapper, XtLog> implements IXtLogService {
    @Autowired
    private XtLogMapper logMapper;

    @Override
    public Page<XtLog> findPage(XtLogQueryParam queryParam) {
        QueryWrapper<XtLog> queryWrapper = buildQueryWapper(queryParam);
        return logMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryWrapper);
    }

    private QueryWrapper<XtLog> buildQueryWapper(XtLogQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtLog>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getKeyword())) {
                query.like("operation", queryParam.getKeyword());
            }
            if(StrUtil.isNotEmpty(queryParam.getTimestamp())){
                String[] dateArr = queryParam.getTimestamp().split(" - ");
                query.gt(StrUtil.isNotEmpty(dateArr[0]),"timestamp", dateArr[0]);
                query.lt(StrUtil.isNotEmpty(dateArr[1]),"timestamp", dateArr[1]);
            }
            query.orderByDesc("timestamp");
        }
        return query;
    }

    @Override
    public List<XtLog> findList(XtLogQueryParam queryParam) {
        QueryWrapper<XtLog> query = new QueryWrapper();
        query.eq(StrUtil.isNotEmpty(queryParam.getKeyword()), "operation", queryParam.getKeyword());
        if(StrUtil.isNotEmpty(queryParam.getTimestamp())){
            String[] dateArr = queryParam.getTimestamp().split(" - ");
            query.gt("timestamp", dateArr[0]);
            query.lt("timestamp", dateArr[1]);
        }

        return logMapper.selectList(query);
    }
}
