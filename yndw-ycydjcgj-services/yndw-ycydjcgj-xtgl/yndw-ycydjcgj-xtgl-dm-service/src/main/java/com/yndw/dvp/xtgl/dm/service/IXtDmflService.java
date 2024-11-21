package com.yndw.dvp.xtgl.dm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/9
 */
public interface IXtDmflService extends ISuperService<XtDmfl> {
    XtDmfl saveOrUpdate(SecurityUser securityUser, XtDmfl entity) throws Exception;

    int deleteById(String bh);

    XtDmfl getById(String bh);

    List<XtDmfl> findList(XtDmflQueryParam queryParam);

    Page<XtDmfl> findPage(XtDmflQueryParam queryParam);

    int updateZtdm(SecurityUser securityUser, XtDmfl entity);

}
