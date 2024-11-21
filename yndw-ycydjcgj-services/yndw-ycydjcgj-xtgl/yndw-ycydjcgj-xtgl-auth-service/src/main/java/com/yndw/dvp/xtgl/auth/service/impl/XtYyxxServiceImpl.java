package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.feign.SecurityMenuService;
import com.yndw.dvp.xtgl.auth.mapper.XtYyxxMapper;
import com.yndw.dvp.xtgl.auth.model.XtDmflmx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtYyxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtYyxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class XtYyxxServiceImpl extends SuperServiceImpl<XtYyxxMapper, XtYyxx> implements IXtYyxxService {

    @Autowired
    private XtYyxxMapper yyxxMapper;
    @Autowired
    private SecurityMenuService securityMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtYyxx saveOrUpdate(SecurityUser securityUser, XtYyxx entity) throws Exception {
        int result = 0;

        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setYyztdm("1");
            entity.setYybm(entity.getYwjc().toUpperCase(Locale.ROOT) + "-" + System.currentTimeMillis());
            result = yyxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            result = yyxxMapper.updateById(entity);
        }
        return result > 0 ? entity : null;
    }

    @Override
    public Page<XtYyxx> queryPage(XtYyxxQueryParam queryParam) {
        return yyxxMapper.queryPage(new Page<>(queryParam.getPage(), queryParam.getLimit()));
    }

    @Override
    public Page<XtYyxx> WsqQueryPage(XtYyxxQueryParam queryParam) {
        return yyxxMapper.WsqQueryPage(new Page<>(queryParam.getPage(), queryParam.getLimit()));
    }

    @Override
    public List<XtYyxx> findList(XtYyxxQueryParam queryParam) {
        QueryWrapper<XtYyxx> queryWrapper = buildQueryWapper(queryParam);
        return yyxxMapper.selectList(queryWrapper);
    }

    private QueryWrapper<XtYyxx> buildQueryWapper(XtYyxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtYyxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getYylb())) {
                query.eq("yylb", queryParam.getYylb());
            }
            query.orderByAsc("sxh");
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        return yyxxMapper.deleteById(bh);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtYyxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return yyxxMapper.updateYyztdm(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAvatar(SecurityUser securityUser, XtYyxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return yyxxMapper.updateAvatar(entity);
    }

    @Override
    public List<XtYyxx> getYyxxNum(String sjbh) {
        List<XtDmflmx> xtDmflmxList = securityMenuService.findMenuBySjbh(sjbh);
        List<String> xtDm = new ArrayList<String>();
        for (XtDmflmx xtDmflmx1 : xtDmflmxList) {
            xtDm.add(xtDmflmx1.getDmbm());
        }
        return yyxxMapper.getYyxxNum(xtDm);
    }

    @Override
    public XtYyxxCountDto yyxxCount() {
        XtYyxxCountDto xtYyxxCountDto = yyxxMapper.yyxxCount();
        return xtYyxxCountDto;
    }

    @Override
    public List<XtYyFlCountDto> yyFlCount(String yyztdm) {
        List<XtYyFlCountDto> xtYyFlCount = yyxxMapper.yyFlCount(yyztdm);
        return xtYyFlCount;
    }

    @Override
    public List<JsfYyxxCountDto> getjsfYyxxNum() {
        List<JsfYyxxCountDto> JsfYyxxCount = yyxxMapper.yyJsfyyCount();
        return JsfYyxxCount;
    }

    @Override
    public List<CjfYyxxCountDto> getcjfYyxxNum() {
        List<CjfYyxxCountDto> CjfYyxxCount = yyxxMapper.yyCjfyyCount();
        return CjfYyxxCount;
    }

    @Override
    public List<XtYyxx> findAuthYyxxList(String czybh) {
        return yyxxMapper.findAuthYyxxList(czybh);
    }
}
