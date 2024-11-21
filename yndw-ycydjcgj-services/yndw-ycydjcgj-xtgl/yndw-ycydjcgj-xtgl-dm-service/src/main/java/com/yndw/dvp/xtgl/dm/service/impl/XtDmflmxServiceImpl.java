package com.yndw.dvp.xtgl.dm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.dm.mapper.XtDmflMapper;
import com.yndw.dvp.xtgl.dm.mapper.XtDmflmxMapper;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflmxQueryParam;
import com.yndw.dvp.xtgl.dm.service.IXtDmflService;
import com.yndw.dvp.xtgl.dm.service.IXtDmflmxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Slf4j
@Service
public class XtDmflmxServiceImpl extends SuperServiceImpl<XtDmflmxMapper, XtDmflmx> implements IXtDmflmxService {
    @Autowired
    private XtDmflmxMapper dmflmxMapper;
    private final static String LOCK_KEY = "dmflbm:dmbm:";
    @Autowired
    private DistributedLock lock;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtDmflmx saveOrUpdate(SecurityUser securityUser, XtDmflmx entity) throws Exception {
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setZtdm(ZtdmConstant.NORMAL.getValue());
        } else {
            entity.setCzrbh(securityUser.getBh());
        }
        if (StrUtil.isBlank(entity.getSjbh())) {
            entity.setSjbh(CommonConstant.TREE_TOP_PID);
        }
        String dmflbm = entity.getDmflbm();
        String dmbm = entity.getDmbm();
        boolean result = super.saveOrUpdateIdempotency(entity, lock
                , LOCK_KEY + dmflbm, new QueryWrapper<XtDmflmx>().eq("dmflbm", dmflbm).eq("dmbm", dmbm)
                , "代码明细{" + dmbm + "}下已存在");
        return result ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        return dmflmxMapper.deleteById(bh);
    }

    @Override
    public XtDmflmx getById(String bh) {
        return dmflmxMapper.selectById(bh);
    }

    @Override
    public List<XtDmflmx> findList(XtDmflmxQueryParam queryParam) {
        QueryWrapper<XtDmflmx> queryWrapper = buildQueryWapper(queryParam);
        return dmflmxMapper.selectList(queryWrapper);
    }

    @Override
    public List<XtDmflmx> findMenuBySjbh(String sjbh) {

        return dmflmxMapper.findMenuBySjbh(sjbh);
    }

    private QueryWrapper<XtDmflmx> buildQueryWapper(XtDmflmxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtDmflmx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getDmflbm())) {
                query.eq("dmflbm", queryParam.getDmflbm());
            }
            if (StrUtil.isNotEmpty(queryParam.getDmmc())) {
                query.like("dmmc", queryParam.getDmmc());
            }
            if (StrUtil.isNotEmpty(queryParam.getSjbh())) {
                query.like("sjbh", queryParam.getSjbh());
            }
            query.orderByAsc("xssxh");
        }
        return query;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtDmflmx entity) {
        entity.setCzrbh(securityUser.getBh());
        return dmflmxMapper.updateZtdm(entity);
    }
}
