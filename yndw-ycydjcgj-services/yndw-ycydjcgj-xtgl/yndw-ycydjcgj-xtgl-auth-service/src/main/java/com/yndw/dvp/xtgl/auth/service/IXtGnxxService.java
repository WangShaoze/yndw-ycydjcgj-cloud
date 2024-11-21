package com.yndw.dvp.xtgl.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface IXtGnxxService extends ISuperService<XtGnxx> {
    XtGnxx saveOrUpdate(SecurityUser securityUser, XtGnxx entity) throws Exception;

    int deleteById(String bh);

    XtGnxx getById(String bh);

    List<XtGnxx> findList(XtGnxxQueryParam queryParam);

    int updateZtdm(SecurityUser securityUser, XtGnxx entity);

    List<SecurityMenu> fingGnxxByJsbhs(SecurityUser czyxx, Set<String> jsbhs, String gnlxdm);

    List<SecurityMenu> findOwnGnxx(SecurityUser czyxx, Set<String> jsbhs, String gnlxdm);

    List<SecurityMenu> fingGnxxByCzybhs(SecurityUser czyxx, Set<String> czybhs, String gnlxdm);

    XtGnxxCountDto gnxxCount();

    XtSqGnxx getByYybh(String yybh, String czybh);

    Page<XtGnxx> myGnxx(SecurityUser securityUser, XtGnxxQueryParam queryParam);
    // 获取应用功能
    List<XtGnxx> gnxxByYybh(String yybh);
    // 获取当前用户已授权功能
    List<SecurityMenu> authGnxxByCzy(String czy);

}
