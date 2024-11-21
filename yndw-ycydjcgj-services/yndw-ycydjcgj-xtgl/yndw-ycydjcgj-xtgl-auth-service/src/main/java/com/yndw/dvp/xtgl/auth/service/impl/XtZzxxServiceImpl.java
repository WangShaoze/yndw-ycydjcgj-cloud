package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.constant.ZzxxDwbzConstant;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxCountDto;
import com.yndw.dvp.xtgl.auth.mapper.XtCzyxxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtGwxxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtZzxxMapper;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtZzxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtZzxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Slf4j
@Service
public class XtZzxxServiceImpl extends SuperServiceImpl<XtZzxxMapper, XtZzxx> implements IXtZzxxService {
    @Autowired
    private DistributedLock lock;
    @Autowired
    private XtZzxxMapper zzxxMapper;
    @Autowired
    private XtCzyxxMapper czyxxMapper;
    @Autowired
    private XtGwxxMapper gwxxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtZzxx saveOrUpdate(SecurityUser securityUser, XtZzxx entity) throws Exception {
        int result = 0;

        if (StrUtil.equals(entity.getDwbz(), ZzxxDwbzConstant.COMPANY.getValue())) {
            entity.setSsdwbh(entity.getBh());
        } else {
            entity.setSsdwbh(entity.getSjbh());
        }
        if (StrUtil.isBlank(entity.getSjbh())) {
            entity.setSjbh(CommonConstant.TREE_TOP_PID);
            entity.setSjbhs(CommonConstant.TREE_TOP_PIDS);
        }
        XtZzxx sjzz = null;
        String oldSjbhs = CommonConstant.TREE_TOP_PIDS;
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setZzztdm(ZtdmConstant.NORMAL.getValue());
            sjzz = zzxxMapper.selectById(entity.getSjbh());
            if (sjzz != null) {
                oldSjbhs = entity.getSjbhs();
                entity.setSjbhs(sjzz.getSjbhs() + entity.getSjbh() + ",");
            }
            result = zzxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            sjzz = zzxxMapper.selectById(entity.getSjbh());
            if (sjzz != null) {
                oldSjbhs = entity.getSjbhs();
                entity.setSjbhs(sjzz.getSjbhs() + entity.getSjbh() + ",");
            }
            result = zzxxMapper.updateById(entity);
        }
        List<XtZzxx> list = zzxxMapper.selectList(new QueryWrapper<XtZzxx>().like("sjbhs", "," + entity.getBh() + ","));
        for (XtZzxx zzxx : list) {
            zzxx.setSjbhs(zzxx.getSjbhs().replace(oldSjbhs, entity.getSjbhs()));
            zzxxMapper.updateSjbhs(zzxx);
        }
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        //删除组织下岗位及其关联信息
        if (gwxxMapper.deleteGwjsgxsByZzbh(bh) >= 0){
            if (gwxxMapper.deleteByZzbh(bh) >= 0){
                //删除组织下操作员及其关联信息
                if (czyxxMapper.deleteCzyqxsByZzbh(bh) >= 0){
                    if (czyxxMapper.deleteByZzbh(bh) >= 0){
                        //删除组织
                        return zzxxMapper.deleteById(bh);
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public XtZzxx getById(String bh) {
        return zzxxMapper.selectById(bh);
    }

    @Override
    public List<XtZzxx> findList(SecurityUser loginUser, XtZzxxQueryParam queryParam) {
        QueryWrapper<XtZzxx> queryWrapper = buildQueryWapper(loginUser,queryParam);
        return zzxxMapper.selectList(queryWrapper);
    }

    private QueryWrapper<XtZzxx> buildQueryWapper(SecurityUser loginUser,XtZzxxQueryParam queryParam) {
        QueryWrapper<XtZzxx> query = new QueryWrapper<>();
        if (!loginUser.getGwbh().equals(CommonConstant.ADMIN_GW_BH) && !loginUser.getDlzh().equals(CommonConstant.ADMIN_USER_NAME)){
            if (StrUtil.isNotEmpty(loginUser.getZzbh())){
                query.and(wrapper -> wrapper.like("sjbhs",","+loginUser.getZzbh()+",").or().eq("bh", loginUser.getZzbh()));
            }
        }
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getSjbh())) {
                query.eq("sjbh", queryParam.getSjbh());
            }
            query.orderByAsc("xssxh");
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtZzxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return zzxxMapper.updateZtdm(entity);
    }

    @Override
    public List<XtZzxx> zzxxList4Type(String type, SecurityUser securityUser) {
        QueryWrapper wrapper = new QueryWrapper();
        if ("1".equals(type)) {
            wrapper.like("sjbhs", ",100,");
        } else if ("2".equals(type)) {
            if (CommonConstant.ADMIN_USER_NAME.equals(securityUser.getDlzh())) {
                wrapper.like("sjbhs", ",200,");
            } else {
                //查询登录人所在单位
                SecurityUser czy = czyxxMapper.getByBh(securityUser.getBh());
                wrapper.like("sjbhs", ",200,");
                wrapper.eq("bh", czy.getZzbh());
            }
        }
        return zzxxMapper.selectList(wrapper);
    }

    @Override
    public XtZzxxCountDto zzxxCount() {
        XtZzxxCountDto xtZzxxCountDto = zzxxMapper.zzxxCount();
        return xtZzxxCountDto;
    }

    @Override
    public List<XtZzxx> getZzByZzbhList(List zzbhList) {
        return zzxxMapper.selectList(new QueryWrapper<XtZzxx>().in("bh",zzbhList));
    }
}
