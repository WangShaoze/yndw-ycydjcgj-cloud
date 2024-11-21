package com.yndw.dvp.oauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.queryParam.OAuthLoginLogQueryParam;

import java.util.List;

public interface IOAuthLoginLogService extends ISuperService<OAuthLoginLog> {
    int saveLoginLog(OAuthLoginLog loginLog);
    Page<OAuthLoginLog> findPage(OAuthLoginLogQueryParam queryParam);

    List<OAuthLoginLog> findList(OAuthLoginLogQueryParam queryParam);
}