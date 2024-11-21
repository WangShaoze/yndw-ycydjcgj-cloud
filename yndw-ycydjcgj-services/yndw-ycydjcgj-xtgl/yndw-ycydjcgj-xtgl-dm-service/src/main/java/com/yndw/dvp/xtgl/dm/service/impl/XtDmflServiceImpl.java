package com.yndw.dvp.xtgl.dm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.dm.mapper.XtDmflMapper;
import com.yndw.dvp.xtgl.dm.mapper.XtDmflmxMapper;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import com.yndw.dvp.xtgl.dm.queryParam.XtDmflQueryParam;
import com.yndw.dvp.xtgl.dm.service.IXtDmflService;
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
public class XtDmflServiceImpl extends SuperServiceImpl<XtDmflMapper, XtDmfl> implements IXtDmflService {
    @Autowired
    private XtDmflMapper dmflMapper;
    private final static String LOCK_KEY_DLZH = "dmflbm:";
    @Autowired
    private DistributedLock lock;
    @Autowired
    private XtDmflmxMapper dmflmxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtDmfl saveOrUpdate(SecurityUser securityUser, XtDmfl entity) throws Exception {
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setZtdm(ZtdmConstant.NORMAL.getValue());
        } else {
            entity.setCzrbh(securityUser.getBh());
        }
        String dmflbm = entity.getDmflbm();
        boolean result = super.saveOrUpdateIdempotency(entity, lock
                , LOCK_KEY_DLZH + dmflbm, new QueryWrapper<XtDmfl>().eq("dmflbm", dmflbm)
                , "登录账号{" + dmflbm + "}已存在");
        return result ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        XtDmfl dmfl = dmflMapper.selectById(bh);
        dmflmxMapper.delete(new QueryWrapper<XtDmflmx>().eq("dmflbm", dmfl.getDmflbm()));
        return dmflMapper.deleteById(bh);
    }

    @Override
    public XtDmfl getById(String bh) {
        return dmflMapper.selectById(bh);
    }

    @Override
    public List<XtDmfl> findList(XtDmflQueryParam queryParam) {
        QueryWrapper<XtDmfl> queryWrapper = buildQueryWapper(queryParam);
        return dmflMapper.selectList(queryWrapper);
    }

    @Override
    public Page<XtDmfl> findPage(XtDmflQueryParam queryParam) {
        QueryWrapper<XtDmfl> queryWrapper = buildQueryWapper(queryParam);
        return dmflMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryWrapper);
    }

    private QueryWrapper<XtDmfl> buildQueryWapper(XtDmflQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtDmfl>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getDmflmc())) {
                query.like("dmflmc", queryParam.getDmflmc());
            }
            query.orderByDesc(queryParam.getSort());
        }
        return query;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtDmfl entity) {
        entity.setCzrbh(securityUser.getBh());
        return dmflMapper.updateZtdm(entity);
    }
}
