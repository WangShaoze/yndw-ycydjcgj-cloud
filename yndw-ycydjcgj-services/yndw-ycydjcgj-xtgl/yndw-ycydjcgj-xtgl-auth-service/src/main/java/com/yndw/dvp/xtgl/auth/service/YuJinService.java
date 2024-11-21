package com.yndw.dvp.xtgl.auth.service;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.model.YuJin;

public interface YuJinService extends ISuperService<YuJin> {

    YuJin findYuJin();

    YuJin updateYuJin(SecurityUser securityUser, String yiJiVal, String erJiVal);
}
