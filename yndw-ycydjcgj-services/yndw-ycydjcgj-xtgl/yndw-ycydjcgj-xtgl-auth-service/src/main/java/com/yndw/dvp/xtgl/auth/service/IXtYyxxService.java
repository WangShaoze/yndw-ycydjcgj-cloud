package com.yndw.dvp.xtgl.auth.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtYyxxQueryParam;

import java.util.List;

public interface IXtYyxxService extends ISuperService<XtYyxx> {
    XtYyxx saveOrUpdate(SecurityUser securityUser, XtYyxx entity) throws Exception;

    List<XtYyxx> findList(XtYyxxQueryParam queryParam);

    Page<XtYyxx> queryPage(XtYyxxQueryParam queryParam);

    Page<XtYyxx> WsqQueryPage(XtYyxxQueryParam queryParam);

    int deleteById(String bh);

    int updateZtdm(SecurityUser securityUser, XtYyxx entity);

    int updateAvatar(SecurityUser securityUser, XtYyxx entity);

    List<XtYyxx> getYyxxNum(String sjbh);

    XtYyxxCountDto yyxxCount();

    List<XtYyFlCountDto> yyFlCount(String yyztdm);

    List<JsfYyxxCountDto> getjsfYyxxNum();

    List<CjfYyxxCountDto> getcjfYyxxNum();

    List<XtYyxx> findAuthYyxxList(String czybh);
}
