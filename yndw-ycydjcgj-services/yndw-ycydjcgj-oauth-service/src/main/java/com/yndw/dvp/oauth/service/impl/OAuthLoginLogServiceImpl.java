package com.yndw.dvp.oauth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.oauth.mapper.OAuthLoginLogMapper;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.queryParam.OAuthLoginLogQueryParam;
import com.yndw.dvp.oauth.service.IOAuthLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Slf4j
@Service
public class OAuthLoginLogServiceImpl extends SuperServiceImpl<OAuthLoginLogMapper,OAuthLoginLog> implements IOAuthLoginLogService {
    @Autowired
    private OAuthLoginLogMapper loginLogMapper;

    @Async
    @Override
    public int saveLoginLog(OAuthLoginLog loginLog){
        return loginLogMapper.insert(loginLog);
    }

    @Override
    public Page<OAuthLoginLog> findPage(OAuthLoginLogQueryParam queryParam) {
        QueryWrapper<OAuthLoginLog> query = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(queryParam.getDlsj())){
            String[] dateArr = queryParam.getDlsj().split(" - ");
            query.gt(StrUtil.isNotEmpty(dateArr[0]), "dlsj", dateArr[0]);
            query.lt(StrUtil.isNotEmpty(dateArr[1]),"dlsj", dateArr[1]);
        }
        query.like(StrUtil.isNotBlank(queryParam.getSearchword()), "czydlzh", queryParam.getSearchword()).or()
                .like(StrUtil.isNotBlank(queryParam.getSearchword()), "dllx", queryParam.getSearchword());
        query.orderByDesc("dlsj");
        return loginLogMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), query);
    }

    @Override
    public List<OAuthLoginLog> findList(OAuthLoginLogQueryParam queryParam) {
        QueryWrapper<OAuthLoginLog> query = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(queryParam.getDlsj())){
            String[] dateArr = queryParam.getDlsj().split(" - ");
            query.gt(StrUtil.isNotEmpty(dateArr[0]), "dlsj", dateArr[0]);
            query.lt(StrUtil.isNotEmpty(dateArr[1]),"dlsj", dateArr[1]);
        }
        query.eq(StrUtil.isNotEmpty(queryParam.getSearchword()), "czydlzh", queryParam.getSearchword());
        query.gt("dlsj", queryParam.getDlsj());
        return loginLogMapper.selectList(query);
    }
}
