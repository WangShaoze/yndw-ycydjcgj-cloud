package com.yndw.dvp.xtgl.auth.service;

import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.GnToJs;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtJsgngx;
import com.yndw.dvp.xtgl.auth.queryParam.XtJsxxQueryParam;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;

import java.util.List;

public interface IXtJsxxService extends ISuperService<XtJsxx> {
    XtJsxx saveOrUpdate(SecurityUser securityUser, XtJsxx entity) throws Exception;

    int deleteById(String bh);

    XtJsxx getById(String bh);

    List<XtJsxx> findList(XtJsxxQueryParam queryParam);

    int updateZtdm(SecurityUser securityUser, XtJsxx entity);

    void setGnxxToJsxx(GnToJs gnToJs);

    String getGnbhsByJsbh(String jsbh);

    List<XtJsgngx> getJsgngxByJsbh(List<String> jsbhList);

    XtJsxxCountDto jsxxCount();
}
