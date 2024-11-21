package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.GnToJs;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxCountDto;
import com.yndw.dvp.xtgl.auth.mapper.XtJsgngxMapper;
import com.yndw.dvp.xtgl.auth.model.*;
import com.yndw.dvp.xtgl.auth.queryParam.XtJsxxQueryParam;
import com.yndw.dvp.xtgl.auth.mapper.XtJsxxMapper;
import com.yndw.dvp.xtgl.auth.service.IXtCzyqxService;
import com.yndw.dvp.xtgl.auth.service.IXtJsxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Slf4j
@Service
public class XtJsxxServiceImpl extends SuperServiceImpl<XtJsxxMapper, XtJsxx> implements IXtJsxxService {
    private final static String LOCK_KEY_DDZH = "ddzh:";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DistributedLock lock;
    @Autowired
    private XtJsxxMapper jsxxMapper;
    @Autowired
    private XtJsgngxMapper jsgngxMapper;

    @Autowired
    private IXtCzyqxService czyqxService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtJsxx saveOrUpdate(SecurityUser securityUser, XtJsxx entity) throws Exception {
        int result = 0;
        if (StrUtil.isBlank(entity.getSjbh())) {
            entity.setSjbh(CommonConstant.TREE_TOP_PID);
            entity.setSjbhs(CommonConstant.TREE_TOP_PIDS);
        }
        XtJsxx sjjs = null;
        String oldSjbhs = CommonConstant.TREE_TOP_PIDS;
        if (StrUtil.isBlank(entity.getBh())) {
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setJsztdm(ZtdmConstant.NORMAL.getValue());
            sjjs = jsxxMapper.selectById(entity.getSjbh());
            if (sjjs != null) {
                oldSjbhs = entity.getSjbhs();
                entity.setSjbhs(sjjs.getSjbhs() + entity.getSjbh() + ",");
            }
            result = jsxxMapper.insert(entity);
        } else {
            entity.setCzrbh(securityUser.getBh());
            sjjs = jsxxMapper.selectById(entity.getSjbh());
            if (sjjs != null) {
                oldSjbhs = entity.getSjbhs();
                entity.setSjbhs(sjjs.getSjbhs() + entity.getSjbh() + ",");
            }
            result = jsxxMapper.updateById(entity);
        }
        List<XtJsxx> list = jsxxMapper.selectList(new QueryWrapper<XtJsxx>().like("sjbhs", "," + entity.getBh() + ","));
        for (XtJsxx jsxx : list) {
            jsxx.setSjbhs(jsxx.getSjbhs().replace(oldSjbhs, entity.getSjbhs()));
            jsxxMapper.updateSjbhs(jsxx);
        }
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        if (jsxxMapper.deleteGwjsgxs(bh) >= 0) {
            if (jsxxMapper.deleteJsgngxs(bh) >= 0) {
                if (jsxxMapper.deleteCzyqxs(bh) >= 0) {
                    return jsxxMapper.deleteById(bh);
                }
            }
        }
        return -1;
    }

    @Override
    public XtJsxx getById(String bh) {
        return jsxxMapper.selectById(bh);
    }

    @Override
    public List<XtJsxx> findList(XtJsxxQueryParam queryParam) {
        QueryWrapper<XtJsxx> queryWrapper = buildQueryWapper(queryParam);
        return jsxxMapper.selectList(queryWrapper);
    }

    private QueryWrapper<XtJsxx> buildQueryWapper(XtJsxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtJsxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getSjbh())) {
                query.eq("sjbh", queryParam.getSjbh());
            }
            if (StrUtil.isNotEmpty(queryParam.getSsyybh())) {
                query.eq("ssyybh", queryParam.getSsyybh());
            }
            query.orderByAsc(queryParam.getSort());
        }
        return query;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZtdm(SecurityUser securityUser, XtJsxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return jsxxMapper.updateZtdm(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setGnxxToJsxx(GnToJs gnToJs) {
        // 获取角色编号
        String jsbh = gnToJs.getJsbh();
        // 获取功能编号字符串
        String gnbhs = gnToJs.getGnbhs();
        XtJsxx jsxx = jsxxMapper.selectById(jsbh);
        if (jsxx == null) {
            throw new IllegalArgumentException("角色不存在");
        }

        jsgngxMapper.deleteJsxxGnxx(jsbh, null);
        List<String> gnbhList = StrUtil.split(gnbhs, ',');
        if (!StrUtil.isEmpty(gnbhs)) {
            gnbhList.forEach(gnbh -> jsgngxMapper.saveJsxxGnxx(jsbh, gnbh));
        }
        gnbhList.forEach(gnbh->jsgngxMapper.yyxxBygnbh(gnbh));
    }

    @Override
    public String getGnbhsByJsbh(String jsbh) {
        XtJsxx jsxx = jsxxMapper.selectById(jsbh);
        if (jsxx == null) {
            throw new IllegalArgumentException("角色不存在");
        }

        List<String> gnbhList = jsgngxMapper.findGnbhsByJsbh(jsbh);

        if (!gnbhList.isEmpty()) {
            return CollectionUtil.join(gnbhList, ",") ;
        }
        return "";
    }
    @Override
    public List<XtJsgngx> getJsgngxByJsbh(List<String> jsbhList) {
        List<XtJsgngx> gnbhList = jsgngxMapper.getJsgngxByJsbh(jsbhList);
        return gnbhList;
    }

    @Override
    public XtJsxxCountDto jsxxCount() {
        XtJsxxCountDto xtJsxxCountDto = jsxxMapper.jsxxCount();
        return xtJsxxCountDto;
    }
}
