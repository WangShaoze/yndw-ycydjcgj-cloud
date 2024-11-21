package com.yndw.dvp.oauth.service;

import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.oauth.model.TokenVo;

import java.util.Map;

/**
 * 
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId);
}
