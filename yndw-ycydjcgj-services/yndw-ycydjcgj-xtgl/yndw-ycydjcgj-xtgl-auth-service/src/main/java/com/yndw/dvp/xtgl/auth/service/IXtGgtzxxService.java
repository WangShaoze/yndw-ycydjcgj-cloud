package com.yndw.dvp.xtgl.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.model.XtGgtzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGgtzxxQueryParam;

/**
 * Create By Liangkh
 * 2021/4/23
 */

import java.util.List;

public interface IXtGgtzxxService extends ISuperService<XtGgtzxx> {

    List<XtGgtzxx> findList(XtGgtzxxQueryParam queryParam);

    Page<XtGgtzxx> findPage(SecurityUser securityUser, XtGgtzxxQueryParam queryParam);

    Page<XtGgtzxx> pageList(SecurityUser securityUser, XtGgtzxxQueryParam queryParam);

    XtGgtzxx saveOrUpdate(SecurityUser securityUser, XtGgtzxx ggtzxx) throws Exception;

    XtGgtzxx put(SecurityUser securityUser, XtGgtzxx ggtzxx, List<String> ids) throws Exception;

    int deleteByID(String bh);

    XtGgtzxx read(SecurityUser securityUser, XtGgtzxx entity) throws Exception;


}
