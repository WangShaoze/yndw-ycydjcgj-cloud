package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.XtGgtzxxDto;
import com.yndw.dvp.xtgl.auth.mapper.XtCzyxxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtGgtzxxMapper;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.model.XtGgtzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGgtzxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import com.yndw.dvp.xtgl.auth.service.IXtGgtzxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create By Liangkh
 * 2021/4/23
 */
@Slf4j
@Service
public class XtGgtzxxServiceImpl extends SuperServiceImpl<XtGgtzxxMapper, XtGgtzxx> implements IXtGgtzxxService {

    @Autowired
    private XtGgtzxxMapper ggtzxxMapper;
    @Autowired
    private XtCzyxxMapper czyxxMapper;


    @Override
    public List<XtGgtzxx> findList(XtGgtzxxQueryParam queryParam) {
        QueryWrapper<XtGgtzxx> queryWrapper = buildQueryWapper(queryParam);
        return ggtzxxMapper.selectList(queryWrapper);
    }

    @Override
    public Page<XtGgtzxx> findPage(SecurityUser securityUser, XtGgtzxxQueryParam queryParam) {
        String startData = null;
        String endData = null;
        if (StrUtil.isNotEmpty(queryParam.getCzsj())) {
            String[] dateArr = queryParam.getCzsj().split(" - ");
            startData = dateArr[0];
            endData = dateArr[1];
        }

        ggtzxxMapper.updateAllCurrentUser(securityUser.getBh());
        return ggtzxxMapper.queryPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), startData, endData, queryParam, securityUser.getBh());
    }

    @Override
    public Page<XtGgtzxx> pageList(SecurityUser securityUser, XtGgtzxxQueryParam queryParam) {
        QueryWrapper<XtGgtzxx> query = buildQueryWapper(queryParam);

        ggtzxxMapper.updateAllCurrentUser(securityUser.getBh());

        return ggtzxxMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), query);
    }

    @Override
    public XtGgtzxx put(SecurityUser securityUser, XtGgtzxx entity, List<String> ids) throws Exception {
        int result = 0;
        result = ggtzxxMapper.insertRelation(entity.getBh(), ids);
        if (result > 0) {
            entity.setCzrbh(securityUser.getBh());
            result = ggtzxxMapper.updateById(entity);
        }
        return result > 0 ? entity : null;
    }

    private QueryWrapper<XtGgtzxx> buildQueryWapper(XtGgtzxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtGgtzxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getCzsj())) {
                String[] dateArr = queryParam.getCzsj().split(" - ");
                query.gt(StrUtil.isNotEmpty(dateArr[0]), "czsj", dateArr[0]);
                query.lt(StrUtil.isNotEmpty(dateArr[1]), "czsj", dateArr[1]);
            }
            if (StrUtil.isNotEmpty(queryParam.getJjdj())) {
                query.eq("jjdj", queryParam.getJjdj());
            }
            if (StrUtil.isNotEmpty(queryParam.getGgzt())) {
                query.eq("ggzt", queryParam.getGgzt());
            }
        }
        query.orderByAsc("ggzt");
        query.orderByAsc("jjdj");
        query.orderByDesc("czsj");
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtGgtzxx saveOrUpdate(SecurityUser securityUser, XtGgtzxx entity) throws Exception {
        int result = 0;
        if (StrUtil.isBlank((entity.getBh()))) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setFbzt("0");
            entity.setGgzt("0");
            XtCzyxx xtCzyxx = czyxxMapper.selectById(securityUser.getBh());
            entity.setCjrmc(xtCzyxx.getCzymc());
            result = ggtzxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            result = ggtzxxMapper.updateById(entity);
        }

        return result > 0 ? entity : null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByID(String bh) {
        return ggtzxxMapper.deleteById(bh);
    }

    @Override
    public XtGgtzxx read(SecurityUser securityUser, XtGgtzxx entity) throws Exception {
        int result = 0;
        result = ggtzxxMapper.read(entity.getBh(), securityUser.getBh());
        return result > 0 ? entity : null;
    }


}
