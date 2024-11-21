package com.yndw.dvp.xtgl.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUserDetails;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtCzyxxQueryParam;

import java.util.List;

public interface IXtCzyxxService extends ISuperService<XtCzyxx> {
    XtCzyxx saveOrUpdate(SecurityUser securityUser, XtCzyxx entity) throws Exception;

    int deleteById(String bh);

    XtCzyxx getById(String bh);

    XtCzyxxDto getMoreById(String bh);

    List<XtCzyxx> findList(XtCzyxxQueryParam queryParam);

    List<XtCzyxx> queryList4Xm(String bhStr);

    Page<XtCzyxx> findPage(XtCzyxxQueryParam queryParam);

    Page<XtCzyxxDto> queryPage(XtCzyxxQueryParam queryParam);

    int updateDlmm(SecurityUser securityUser, String bh, String oldDlmm, String newDlmm);

    boolean checkDefaultDlmm(SecurityUser securityUser);

    int updateZtdm(SecurityUser securityUser, XtCzyxx entity);

    int updateAvatar(SecurityUser securityUser, XtCzyxx entity);

    int lockCzy(String czybh, String isLock);

    int updateDlmmycwcs(String czybh);

    int updateDlmmycwcsToZero(String czybh);

    SecurityUser getUserByDlzh(String dlzh);

    SecurityUserDetails getUserDetailsByDlzh(String dlzh);

    SecurityUserDetails getUserDetailsByCzybh(String dlzh);

    XtCzyxx getByCzybh(String bh);

    boolean unlock(SecurityUser loginUser, String passwd);

    XtCzyxxCountDto czyxxCount();

    List<XtCzyxx> getCzyxxBybhList(List<String> bhList);

}
