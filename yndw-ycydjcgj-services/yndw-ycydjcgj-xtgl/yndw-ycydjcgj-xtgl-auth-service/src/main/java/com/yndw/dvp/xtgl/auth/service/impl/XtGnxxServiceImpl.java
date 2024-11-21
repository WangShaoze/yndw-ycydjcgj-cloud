package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.XtGnxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtGnxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtSqGnxx;
import com.yndw.dvp.xtgl.auth.mapper.XtCzyxxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtGnxxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtGwjsgxMapper;
import com.yndw.dvp.xtgl.auth.mapper.XtJsgngxMapper;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtJsgngx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtGnxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Slf4j
@Service
public class XtGnxxServiceImpl extends SuperServiceImpl<XtGnxxMapper, XtGnxx> implements IXtGnxxService {
    private final static String LOCK_KEY_GNLJDZ = "gnljdz:";
    @Autowired
    private DistributedLock lock;
    @Autowired
    private XtGnxxMapper gnxxMapper;
    @Autowired
    private XtJsgngxMapper jsgngxMapper;
    @Autowired
    private XtCzyxxMapper czyxxMapper;
    @Autowired
    private XtGwjsgxMapper gwjsgxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtGnxx saveOrUpdate(SecurityUser securityUser, XtGnxx entity) throws Exception {
        int result = 0;

        if (StrUtil.isBlank(entity.getSjbh())) {
            entity.setSjbh(CommonConstant.TREE_TOP_PID);
            entity.setSjbhs(CommonConstant.TREE_TOP_PIDS);
        }
        XtGnxx sjgn = null;
        String oldSjbhs = CommonConstant.TREE_TOP_PIDS;
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setGnztdm(ZtdmConstant.NORMAL.getValue());
            sjgn = gnxxMapper.selectById(entity.getSjbh());
            if (sjgn != null) {
                oldSjbhs = entity.getSjbhs();
                entity.setSjbhs(sjgn.getSjbhs() + entity.getSjbh() + ",");
            }
            result = gnxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            sjgn = gnxxMapper.selectById(entity.getSjbh());
            if (sjgn != null) {
                oldSjbhs = entity.getSjbhs();
                if (StrUtil.isEmpty(oldSjbhs)){
                    oldSjbhs = " ";
                }
                entity.setSjbhs(sjgn.getSjbhs() + entity.getSjbh() + ",");
            }
            result = gnxxMapper.updateById(entity);
        }
        List<XtGnxx> list = gnxxMapper.selectList(new QueryWrapper<XtGnxx>().like("sjbhs", "," + entity.getBh() + ","));
        for (XtGnxx gnxx : list) {
            gnxx.setSjbhs(gnxx.getSjbhs().replace(oldSjbhs, entity.getSjbhs()));
            gnxxMapper.updateSjbhs(gnxx);
        }
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        if (gnxxMapper.deleteCzyqxs(bh) >= 0){
            if (gnxxMapper.deleteJsgngxs(bh) >= 0){
                return gnxxMapper.deleteById(bh);
            }
        }
        return -1;
    }

    @Override
    public XtGnxx getById(String bh) {
        return gnxxMapper.selectById(bh);
    }

    @Override
    public List<XtGnxx> findList(XtGnxxQueryParam queryParam) {
        QueryWrapper<XtGnxx> queryWrapper = buildQueryWapper(queryParam);
        return gnxxMapper.selectList(queryWrapper);
    }

    private QueryWrapper<XtGnxx> buildQueryWapper(XtGnxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtGnxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getSjbh())) {
                query.eq("sjbh", queryParam.getSjbh());
            }
            if (StrUtil.isNotEmpty(queryParam.getSsyybh())) {
                query.eq("ssyybh", queryParam.getSsyybh());
            }
            query.orderByAsc("djmksxh");
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtGnxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return gnxxMapper.updateZtdm(entity);
    }

    @Override
    public List<SecurityMenu> fingGnxxByJsbhs(SecurityUser czyxx, Set<String> jsbhs, String gnlxdm) {
        List<XtGnxx> gnxxList = null;
        // jsbhs  安全用户列表
        // czyxx.getBh() 获取安全用户的 功能编号
        // gnlxdm       是获取菜单还是获取权限“这里传入的是菜单”

        System.out.println("czyxx:"+czyxx.getBh());  // 1
        System.out.println("jsbhs:"+jsbhs);  // [10001]
        System.out.println("gnlxdm:"+gnlxdm);  // 1
        gnxxList = jsgngxMapper.findGnxxByJsxxs(jsbhs, czyxx.getBh(), gnlxdm);
        List<SecurityMenu> gnxxList1 = new ArrayList<>();
        for (XtGnxx gnxx : gnxxList) {
            SecurityMenu gnxx1 = new SecurityMenu();
            gnxx1.setBh(gnxx.getBh());
            gnxx1.setSjbh(gnxx.getSjbh());
            gnxx1.setGnmc(gnxx.getGnmc());
            gnxx1.setGnljdz(gnxx.getGnljdz());
            gnxx1.setGnqqff(gnxx.getGnqqff());
            gnxx1.setGnqxbz(gnxx.getGnqxbz());
            gnxxList1.add(gnxx1);
        }
        return gnxxList1;
    }

    @Override
    public List<SecurityMenu> findOwnGnxx(SecurityUser czyxx, Set<String> jsbhs, String gnlxdm) {
        List<XtGnxx> gnxxList = null;
        gnxxList = jsgngxMapper.findOwnGnxx(jsbhs, czyxx.getBh(), gnlxdm);
        List<SecurityMenu> gnxxList1 = new ArrayList<>();
        for (XtGnxx gnxx : gnxxList) {
            SecurityMenu gnxx1 = new SecurityMenu();
            gnxx1.setBh(gnxx.getBh());
            gnxx1.setSjbh(gnxx.getSjbh());
            gnxx1.setGnmc(gnxx.getGnmc());
            gnxx1.setGnljdz(gnxx.getGnljdz());
            gnxx1.setGnqqff(gnxx.getGnqqff());
            gnxx1.setGnqxbz(gnxx.getGnqxbz());
            gnxxList1.add(gnxx1);
        }
        return gnxxList1;
    }

    @Override
    public List<SecurityMenu> fingGnxxByCzybhs(SecurityUser czyxx, Set<String> czybhs, String gnlxdm) {
        List<XtGnxx> gnxxList = null;
        gnxxList = czyxxMapper.findGnxxByCzyxxs(czybhs, gnlxdm);
        List<SecurityMenu> gnxxList1 = new ArrayList<>();
        for (XtGnxx gnxx : gnxxList) {
            SecurityMenu gnxx1 = new SecurityMenu();
            gnxx1.setBh(gnxx.getBh());
            gnxx1.setSjbh(gnxx.getSjbh());
            gnxx1.setGnmc(gnxx.getGnmc());
            gnxx1.setGnljdz(gnxx.getGnljdz());
            gnxx1.setGnqqff(gnxx.getGnqqff());
            gnxx1.setGnqxbz(gnxx.getGnqxbz());
            gnxxList1.add(gnxx1);
        }
        return gnxxList1;
    }

    @Override
    public XtGnxxCountDto gnxxCount() {
        XtGnxxCountDto xtGnxxCountDto = gnxxMapper.gnxxCount();
        return xtGnxxCountDto;
    }

    @Override
    public XtSqGnxx getByYybh(String yybh, String czybh) {
        List<XtGnxx> ysqgnxx = gnxxMapper.sqgnxxByYybh(yybh, czybh);
        List<XtGnxx> wsqgnxx= gnxxMapper.wsqgnxxByYybh(yybh, czybh);
        XtSqGnxx xtSqGnxx = new XtSqGnxx();
        xtSqGnxx.setYsqGnxx(ysqgnxx);
        xtSqGnxx.setWsqGnxx(wsqgnxx);
        return xtSqGnxx;
    }

    @Override
    public Page<XtGnxx> myGnxx(SecurityUser securityUser, XtGnxxQueryParam queryParam) {
        Page<XtGnxxDto> xtGnxxDtoPage = new Page<XtGnxxDto>(queryParam.getPage(),queryParam.getLimit());
        Page<XtGnxx> xtGnxxPage = gnxxMapper.myGnxx(xtGnxxDtoPage, securityUser.getBh());
        return xtGnxxPage;
    }

    @Override
    public List<XtGnxx> gnxxByYybh(String yybh) {
        Map<String,Object> map = new HashMap<>();
        map.put("ssyybh",yybh);
        return gnxxMapper.selectByMap(map);
    }

    // 获取当前用户已授权功能
    @Override
    public List<SecurityMenu> authGnxxByCzy(String czy) {
        // 根据操作员获取岗位
        XtCzyxx czyxx = czyxxMapper.selectById(czy);
        // 获取操作员角色
        List<String> jsbhList = gwjsgxMapper.findJsbhsByGwbh(czyxx.getGwbh());
        // 获取操作员功能编号
        List<XtJsgngx> jsgngxList = jsgngxMapper.getJsgngxByJsbh(jsbhList);
        // 获取功能地址
        List<SecurityMenu> gnxxList = new LinkedList<>();
        jsgngxList.stream().map(xtJsgngx -> {
            SecurityMenu securityGnxx = new SecurityMenu();
//            XtGnxx gnxx = gnxxMapper.selectById(xtJsgngx.getGnbh());
            Map map = new HashMap(){{put("bh",xtJsgngx.getGnbh());}};
            List<XtGnxx> gnxxs = gnxxMapper.selectByMap(map);
            for (XtGnxx gnxx : gnxxs){
                securityGnxx.setGnljdz(gnxx.getGnljdz());   // 功能链接地址
                securityGnxx.setGnqqff(gnxx.getGnqqff());  // 功能请求方法
                securityGnxx.setGnmc(gnxx.getGnmc());  // 功能名称
                gnxxList.add(securityGnxx);
            }
            return gnxxList;
        }).collect(Collectors.toList());
        return  gnxxList;
    }
}
