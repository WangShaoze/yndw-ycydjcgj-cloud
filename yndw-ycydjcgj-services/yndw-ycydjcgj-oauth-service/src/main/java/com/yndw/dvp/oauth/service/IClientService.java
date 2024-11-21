package com.yndw.dvp.oauth.service;

import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.oauth.model.Client;

import java.util.Map;

/**
 * 
 * <p>
 * 
 * 
 */
public interface IClientService extends ISuperService<Client> {
    CommonResult saveClient(Client clientDto) throws Exception;

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    void delClient(long id);
}
