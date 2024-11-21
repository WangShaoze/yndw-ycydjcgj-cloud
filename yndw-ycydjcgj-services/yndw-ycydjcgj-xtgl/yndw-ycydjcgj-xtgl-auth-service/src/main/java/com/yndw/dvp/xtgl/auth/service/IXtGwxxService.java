package com.yndw.dvp.xtgl.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.queryParam.XtGwxxQueryParam;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;

import java.util.List;

public interface IXtGwxxService extends ISuperService<XtGwxx> {
    XtGwxx saveOrUpdate(SecurityUser securityUser, XtGwxx entity) throws Exception;

    int deleteById(String bh);

    XtGwxx getById(String bh);

    List<XtGwxx> findList(XtGwxxQueryParam queryParam);

    Page<XtGwxx> findPage(XtGwxxQueryParam queryParam);

    int updateZtdm(SecurityUser securityUser, XtGwxx entity);

    void setJsxxToGwxx(String gwbh, String jsbhs);

    String getJsbhsByGwbh(String gwbh);

    String getYybhsByGwbh(String gwbh);

    List<String> getJsbhListByGwbh(String gwbh);

    XtGwxxCountDto gwxxCount();
}
