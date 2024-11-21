package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.common.core.utils.IdGenerator;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.mapper.*;
import com.yndw.dvp.xtgl.auth.model.*;
import com.yndw.dvp.xtgl.auth.queryParam.XtGwxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtGwxxService;
import com.yndw.dvp.xtgl.auth.service.IXtJsxxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Slf4j
@Service
public class XtGwxxServiceImpl extends SuperServiceImpl<XtGwxxMapper, XtGwxx> implements IXtGwxxService {
    @Autowired
    private XtGwxxMapper gwxxMapper;
    @Autowired
    private XtZzxxMapper zzxxMapper;

    @Autowired
    private XtJsgngxMapper jsgngxMapper;
    @Autowired
    private XtGwjsgxMapper gwjsgxMapper;
    @Autowired
    private XtCzyxxMapper czyxxMapper;
    @Autowired
    private XtCzyqxMapper czyqxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtGwxx saveOrUpdate(SecurityUser securityUser, XtGwxx entity) throws Exception {
        int result = 0;
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setGwztdm(ZtdmConstant.NORMAL.getValue());
            result = gwxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            result = gwxxMapper.updateById(entity);
        }
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        if (gwxxMapper.deleteGwjsgxs(bh) >= 0){
            return gwxxMapper.deleteById(bh);
        }
        return -1;
    }

    @Override
    public XtGwxx getById(String bh) {
        return gwxxMapper.selectById(bh);
    }

    @Override
    public List<XtGwxx> findList(XtGwxxQueryParam queryParam) {
        QueryWrapper<XtGwxx> queryWrapper = buildQueryWapper(queryParam);
        return gwxxMapper.selectList(queryWrapper);
    }

    @Override
    public Page<XtGwxx> findPage(XtGwxxQueryParam queryParam) {
        QueryWrapper<XtGwxx> queryWrapper = buildQueryWapper(queryParam);
        return gwxxMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryWrapper);
    }

    private QueryWrapper<XtGwxx> buildQueryWapper(XtGwxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtGwxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getGwmc())) {
                query.like("gwmc", queryParam.getGwmc());
            }

            if (StrUtil.isNotEmpty(queryParam.getType()) && StrUtil.equals(queryParam.getType(), "2")) {
                if (StrUtil.isNotEmpty(queryParam.getZzbh())) {
                    List<XtZzxx> list = zzxxMapper.selectList(new QueryWrapper<XtZzxx>().like("sjbhs", "," + queryParam.getZzbh() + ","));
                    XtZzxx zzxx = new XtZzxx();
                    zzxx.setBh(queryParam.getZzbh());
                    list.add(zzxx);
                    List<String> zzbhs = new ArrayList<>();
                    list.parallelStream().forEach(z -> zzbhs.add(z.getBh()));
                    query.in("zzbh", zzbhs);
                }
            } else {
                if (StrUtil.isNotEmpty(queryParam.getZzbh())) {
                    query.eq("zzbh", queryParam.getZzbh());
                }
            }
            query.orderByDesc(queryParam.getSort());
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtGwxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return gwxxMapper.updateZtdm(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setJsxxToGwxx(String gwbh, String jsbhs) {
        XtGwxx gwxx = gwxxMapper.selectById(gwbh);
        if (gwxx == null) {
            throw new IllegalArgumentException("岗位不存在");
        }

        gwjsgxMapper.deleteGwxxJsxx(gwbh, null);

        if (!StrUtil.isEmpty(jsbhs)) {
            List<String> jsbhList = StrUtil.split(jsbhs, ',');
            //新增岗位角色关系
            jsbhList.forEach(jsbh -> gwjsgxMapper.saveGwxxJsxx(gwbh, jsbh));

            czyqxMapper.deleteCzyqxByJsbhs(jsbhList);
            //获取角色绑定的功能信息
            List<XtGnxx> gnxxList = new ArrayList<XtGnxx>();
            for ( String jsbh: jsbhList) {
                gnxxList.addAll(jsgngxMapper.getGnxxByJsbh(jsbh));
            }
            //获取角色绑定的功能关系
            List<XtJsgngx> jsgngxList = jsgngxMapper.getJsgngxByJsbh(jsbhList);
            //获取岗位下所有操作员信息
            List<XtCzyxx> czyxxList = czyxxMapper.getCzyxxByGwbh(gwbh);
            //为该岗位下操作员分配相应功能
//            XtCzyqx czyqx = new XtCzyqx();
//            for (XtCzyxx czyxx: czyxxList){
//                for (XtGnxx gnxx: gnxxList){
//                    czyqx.setCzybh(czyxx.getBh());
//                    czyqx.setGnbh(gnxx.getBh());
//                    for (XtJsgngx jsgngx: jsgngxList) {
//                        if (jsgngx.getGnbh().equals(gnxx.getBh())){
//                            czyqx.setJsbh(jsgngx.getJsbh());
//                            break;
//                        }
//                    }
//                    czyqx.setUsecount(0);
//                    czyqx.setYybh(gnxx.getSsyybh());
//                    czyqxMapper.insert(czyqx);
//                }
//            }
        }
    }

    @Override
    public String getJsbhsByGwbh(String gwbh) {
        XtGwxx gwxx = gwxxMapper.selectById(gwbh);
        if (gwxx == null) {
            throw new IllegalArgumentException("岗位不存在");
        }

        List<String> jsbhList = gwjsgxMapper.findJsbhsByGwbh(gwbh);

        if (!jsbhList.isEmpty()) {
            return "," + CollectionUtil.join(jsbhList, ",") + ",";
        }
        return "";
    }

    @Override
    public String getYybhsByGwbh(String gwbh) {
        XtGwxx gwxx = gwxxMapper.selectById(gwbh);
        if (gwxx == null) {
            throw new IllegalArgumentException("岗位不存在");
        }

        List<String> yybhList = gwjsgxMapper.findYybhsByGwbh(gwbh);

        if (!yybhList.isEmpty()) {
            return "," + CollectionUtil.join(yybhList, ",") + ",";
        }
        return "";
    }

    @Override
    public List<String> getJsbhListByGwbh(String gwbh) {
        XtGwxx gwxx = gwxxMapper.selectById(gwbh);
        if (gwxx == null) {
            throw new IllegalArgumentException("岗位不存在");
        }

        List<String> jsbhList = gwjsgxMapper.findJsbhsByGwbh(gwbh);;
        return jsbhList;
    }

    @Override
    public XtGwxxCountDto gwxxCount() {
        XtGwxxCountDto xtGwxxCountDto = gwxxMapper.gwxxCount();
        return xtGwxxCountDto;
    }
}
