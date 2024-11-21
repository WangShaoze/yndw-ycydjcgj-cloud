package com.yndw.dvp.xtgl.auth.service;

import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtZzxxQueryParam;

import java.util.List;

public interface IXtZzxxService extends ISuperService<XtZzxx> {
    XtZzxx saveOrUpdate(SecurityUser securityUser, XtZzxx entity) throws Exception;

    int deleteById(String bh);

    XtZzxx getById(String bh);

    List<XtZzxx> findList(SecurityUser loginUser, XtZzxxQueryParam queryParam);

    int updateZtdm(SecurityUser securityUser, XtZzxx entity);

    List<XtZzxx> zzxxList4Type(String type, SecurityUser securityUser);

    XtZzxxCountDto zzxxCount();

    List<XtZzxx> getZzByZzbhList(List<String> zzbhList);

}
