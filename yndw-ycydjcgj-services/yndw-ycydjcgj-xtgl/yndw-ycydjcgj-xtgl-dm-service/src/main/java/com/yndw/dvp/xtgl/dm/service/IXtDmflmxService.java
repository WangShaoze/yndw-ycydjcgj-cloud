package com.yndw.dvp.xtgl.dm.service;

import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflmxQueryParam;

import java.util.List;
import java.util.Set;

/**
 * Create By Carlos
 * 2020/7/9
 */
public interface IXtDmflmxService extends ISuperService<XtDmflmx> {
    XtDmflmx saveOrUpdate(SecurityUser securityUser, XtDmflmx entity) throws Exception;

    int deleteById(String bh);

    XtDmflmx getById(String bh);

    List<XtDmflmx> findList(XtDmflmxQueryParam queryParam);

    List<XtDmflmx> findMenuBySjbh(String sjbh);

    int updateZtdm(SecurityUser securityUser, XtDmflmx entity);

}
