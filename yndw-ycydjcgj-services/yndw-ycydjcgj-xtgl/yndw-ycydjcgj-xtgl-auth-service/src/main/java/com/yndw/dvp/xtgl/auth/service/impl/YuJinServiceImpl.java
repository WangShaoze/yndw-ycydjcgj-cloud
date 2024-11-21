package com.yndw.dvp.xtgl.auth.service.impl;

import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.mapper.YuJinMapper;
import com.yndw.dvp.xtgl.auth.model.YuJin;
import com.yndw.dvp.xtgl.auth.service.YuJinService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class YuJinServiceImpl extends SuperServiceImpl<YuJinMapper, YuJin> implements YuJinService {
    private static final Logger logger = LoggerFactory.getLogger(com.yndw.dvp.xtgl.auth.controller.XtCzyqxController.class);

    @Autowired
    private YuJinMapper yuJinMapper;


    @Override
    public YuJin findYuJin() {
        return yuJinMapper.getYuJinEntity();
    }

    @Override
    public YuJin updateYuJin(SecurityUser securityUser, String yiJiVal, String erJiVal) {
        String id = securityUser.getBh();
        String userName = securityUser.getDlzh();
        YuJin yuJin = new YuJin();
        yuJin.setId(1);
        yuJin.setUpdateId(id);
        yuJin.setUpdateName(userName);
        yuJin.setYjyjz(yiJiVal);
        yuJin.setErjyjz(erJiVal);
        System.out.println("yuJin:"+yuJin);
        yuJinMapper.updateByEntity(yuJin);
        System.out.println("hear");
        YuJin yuJinNew = yuJinMapper.getYuJinEntity();
        System.out.println("hear");
        logger.info("YuJinServiceImpl => updateYuJin 更新成功 => yuJinNew \n {}", yuJinNew.toString());
        return yuJinNew;
    }
}
