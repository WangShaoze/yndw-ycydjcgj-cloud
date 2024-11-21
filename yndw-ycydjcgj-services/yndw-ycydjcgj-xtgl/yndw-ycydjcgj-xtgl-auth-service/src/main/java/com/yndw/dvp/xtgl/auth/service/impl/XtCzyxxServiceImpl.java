package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.pattern.CronPatternUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.lock.DistributedLock;
import com.yndw.dvp.common.core.model.SecurityRole;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.model.SecurityUserDetails;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.constant.GnxxLxdmConstant;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.mapper.*;
import com.yndw.dvp.xtgl.auth.model.*;
import com.yndw.dvp.xtgl.auth.queryParam.XtCzyxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtCzyqxService;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import com.yndw.dvp.xtgl.auth.service.IXtGwxxService;
import com.yndw.dvp.xtgl.auth.service.IXtJsxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/6/12
 *
 * @author carlos
 */
@Slf4j
@Service
@RefreshScope
public class XtCzyxxServiceImpl extends SuperServiceImpl<XtCzyxxMapper, XtCzyxx> implements IXtCzyxxService {
    private final static String LOCK_KEY_DLZH = "dlzh:";
    private final static String PWD_STRENGTH = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,30}";
    @Value("${dvp.czyxx.dlmmycwcs}")
    private int dlmmycwcs;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IXtJsxxService jsxxService;
    @Autowired
    private IXtGwxxService gwxxService;
    @Autowired
    private XtCzyqxMapper czyqxMapper;
    @Autowired
    private DistributedLock lock;
    @Autowired
    private XtCzyxxMapper czyxxMapper;
    @Autowired
    private XtGwjsgxMapper gwjsgxMapper;
    @Autowired
    private XtJsgngxMapper jsgngxMapper;
    @Autowired
    private XtGwxxMapper gwxxMapper;
    @Autowired
    private XtZzxxMapper zzxxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public XtCzyxx saveOrUpdate(SecurityUser securityUser, XtCzyxx entity) throws Exception {
        boolean isSave;
        if (isSave = StrUtil.isBlank(entity.getBh())) {
            String reg = ".*(admin|root|administrator|superadmin|test|adminydtf).*";
            if (StrUtil.isNotBlank(entity.getDlzh())) {
                if (Pattern.matches(reg, entity.getDlzh().toLowerCase())) {
                    throw new BusinessException("账号不能以admin、root、test等命名");
                }
            }
            entity.setCjrbh(securityUser.getBh());
            entity.setCzrbh(securityUser.getBh());
            entity.setDlmm(passwordEncoder.encode(entity.getDlzh() + CommonConstant.DEF_USER_PASSWORD));
            entity.setDlmmzdcwcs(dlmmycwcs);
            entity.setCzyztdm(ZtdmConstant.NORMAL.getValue());
        } else {
            entity.setCzrbh(securityUser.getBh());
        }
        String dlzh = entity.getDlzh();
        String[] gwbh = StrUtil.split(entity.getGwbh(), ",");
        entity.setZzbh(gwxxMapper.selectById(gwbh[gwbh.length - 1]).getZzbh());
        entity.setGwbh(gwbh[gwbh.length - 1]);
        boolean result = super.saveOrUpdateIdempotency(entity, lock
                , LOCK_KEY_DLZH + dlzh, new QueryWrapper<XtCzyxx>().eq("dlzh", dlzh)
                , "登录账号{" + dlzh + "}已存在");
        if (result){
                //获取岗位绑定的角色
                List<String> jsbhList = gwxxService.getJsbhListByGwbh(entity.getGwbh());
                //获取角色功能列表
                List<XtJsgngx> gnbhList = jsxxService.getJsgngxByJsbh(jsbhList);
                //删除之前的操作员之前的权限
                czyqxMapper.deleteCzyGnxx(entity.getBh(),"1");

                //将岗位绑定的角色的拥有的功能权限写入
                String jsbh,czybh,gnbh;
                czybh = entity.getBh();
                for (XtJsgngx jsgngx : gnbhList) {
                    jsbh = jsgngx.getJsbh();
                    gnbh = jsgngx.getGnbh();
                    czyqxMapper.saveWhenSaveCzy(jsbh,czybh,gnbh);
                }
                return entity;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        if (czyxxMapper.deleteCzyqxs(bh) >= 0){
            return czyxxMapper.deleteById(bh);
        }
        return -1;
    }

    @Override
    public XtCzyxx getById(String bh) {
       XtCzyxx xtCzyxx = czyxxMapper.selectById(bh);
        String gwbh = xtCzyxx.getGwbh();
        XtGwxx xtGwxx = gwxxMapper.selectById(gwbh);
        String gwmc = xtGwxx.getGwmc();
        xtCzyxx.setGwmc(gwmc);
        return xtCzyxx;
    }

    @Override
    public XtCzyxxDto getMoreById(String bh) {
        return czyxxMapper.getMoreById(bh);
    }

    @Override
    public List<XtCzyxx> findList(XtCzyxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtCzyxx>();
        query.like("zzbh", queryParam.getZzbh());
        List list = czyxxMapper.selectList(query);
        for (Object obj : list) {
            XtCzyxx czyxx = (XtCzyxx) obj;
            XtZzxx xtZzxx = zzxxMapper.selectById(czyxx.getZzbh());
            XtGwxx xtGwxx = gwxxMapper.selectById(czyxx.getGwbh());
            czyxx.setZzmc(xtZzxx.getZzmc());
            czyxx.setGwmc(xtGwxx.getGwmc());
        }

        return list;
    }

    @Override
    public List<XtCzyxx> queryList4Xm(String bhStr) {
        String[] split = bhStr.split(",");
        return czyxxMapper.queryList4Xm(split);
    }

    @Override
    public Page<XtCzyxx> findPage(XtCzyxxQueryParam queryParam) {
        QueryWrapper<XtCzyxx> queryWrapper = buildQueryWapper(queryParam);
        return czyxxMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryWrapper);
    }

    @Override
    public Page<XtCzyxxDto> queryPage(XtCzyxxQueryParam queryParam) {
        return czyxxMapper.queryPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryParam);
    }

    private QueryWrapper<XtCzyxx> buildQueryWapper(XtCzyxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtCzyxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getCzymc())) {
                query.like("czymc", queryParam.getCzymc());
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
    public int updateZtdm(SecurityUser securityUser, XtCzyxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return czyxxMapper.updateZtdm(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAvatar(SecurityUser securityUser, XtCzyxx entity) {
        entity.setCzrbh(securityUser.getBh());
        return czyxxMapper.updateAvatar(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int lockCzy(String czybh, String isLock) {
        XtCzyxx czyxx = new XtCzyxx();
        czyxx.setBh(czybh);
        czyxx.setCzrbh(czybh);
        if (StrUtil.equals(isLock, "true")) {
            czyxx.setCzyztdm(ZtdmConstant.LOCK.getValue());
        } else if (StrUtil.equals(isLock, "false")) {
            czyxx.setCzyztdm(ZtdmConstant.NORMAL.getValue());
        } else {
            czyxx.setCzyztdm(ZtdmConstant.LOCK.getValue());
        }
        return czyxxMapper.updateZtdm(czyxx);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDlmmycwcs(String czybh) {
        XtCzyxx czyxx = czyxxMapper.selectById(czybh);
        if (czyxx.getDlmmycwcs() == 0) {
            return czyxxMapper.updateDlmmycwcsAndSj(czybh);
        }
        return czyxxMapper.updateDlmmycwcs(czybh);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDlmmycwcsToZero(String czybh) {
        return czyxxMapper.updateDlmmycwcsToZero(czybh);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDlmm(SecurityUser securityUser, String bh, String oldDlmm, String newDlmm) {
        XtCzyxx czyxx = czyxxMapper.selectById(bh);
        if (czyxx == null) {
            return -1;
        }
        if (StrUtil.isNotBlank(oldDlmm)) {
            if (!passwordEncoder.matches(oldDlmm, czyxx.getDlmm())) {
                throw new BusinessException("旧密码错误");
            }
        }
        if (StrUtil.isBlank(newDlmm)) {
            newDlmm = czyxx.getDlzh() + CommonConstant.DEF_USER_PASSWORD;
        }
        if (!ReUtil.isMatch(PWD_STRENGTH, newDlmm)) {
            throw new BusinessException("密码强度不符");
        }
        czyxx.setDlmm(passwordEncoder.encode(newDlmm));
        czyxx.setCzrbh(securityUser.getBh());
        return czyxxMapper.updateDlmm(czyxx);
    }

    @Override
    public boolean checkDefaultDlmm(SecurityUser securityUser) {
        XtCzyxx czyxx = czyxxMapper.selectById(securityUser.getBh());
        if (czyxx == null) {
            throw new BusinessException("非法账户");
        }
        return passwordEncoder.matches(czyxx.getDlzh() + CommonConstant.DEF_USER_PASSWORD, czyxx.getDlmm());
    }

    @Override
    public SecurityUser getUserByDlzh(String dlzh) {
        return czyxxMapper.getByDlzh(dlzh);
    }

    @Override
    public SecurityUserDetails getUserDetailsByDlzh(String dlzh) {
        return getUserDetails(czyxxMapper.getByDlzh(dlzh));
    }

    @Override
    public SecurityUserDetails getUserDetailsByCzybh(String bh) {
        return getUserDetails(czyxxMapper.getByBh(bh));
    }

    @Override
    public XtCzyxx getByCzybh(String bh) {
        return czyxxMapper.selectById(bh);
    }

    @Override
    public boolean unlock(SecurityUser loginUser, String passwd) {
        if (StrUtil.isEmpty(passwd)) {
            throw new BusinessException("密码为空！");
        }
        XtCzyxx czyxx = czyxxMapper.selectById(loginUser.getBh());
        boolean flag = passwordEncoder.matches(passwd, czyxx.getDlmm());
        return flag;
    }

    private SecurityUserDetails getUserDetails(SecurityUser securityUser) {
        if (securityUser != null) {
            SecurityUserDetails loginUserDetails = new SecurityUserDetails();
            BeanUtils.copyProperties(securityUser, loginUserDetails);
            List<SecurityRole> securityRoleList = new ArrayList<>();
            List<String> jsbhList = gwjsgxMapper.findJsbhsByGwbh(securityUser.getGwbh());
            for (String jsbh : jsbhList) {
                SecurityRole securityRole = new SecurityRole();
                securityRole.setBh(jsbh);
                securityRoleList.add(securityRole);
            }
            loginUserDetails.setSecurityRoleList(securityRoleList);

            if (!CollectionUtils.isEmpty(securityRoleList)) {
                Set<String> jsbhs = securityRoleList.parallelStream().map(SecurityRole::getBh).collect(Collectors.toSet());
                List<XtGnxx> gnxxs = jsgngxMapper.findGnxxByJsxxs(jsbhs, securityUser.getBh(), GnxxLxdmConstant.PERMISSION.getValue());
                if (!CollectionUtils.isEmpty(gnxxs)) {
                    Set<String> permissions = gnxxs.parallelStream().map(p -> p.getGnqxbz())
                            .collect(Collectors.toSet());
                    // 设置权限集合
                    loginUserDetails.setPermissions(permissions);
                }
            }
            return loginUserDetails;
        }
        return null;
    }

    @Override
    public XtCzyxxCountDto czyxxCount() {
        XtCzyxxCountDto xtCzyxxCountDto = czyxxMapper.czyxxCount();
        return xtCzyxxCountDto;
    }

    @Override
    public List<XtCzyxx> getCzyxxBybhList(List<String> bhList) {
        return czyxxMapper.selectList(new QueryWrapper<XtCzyxx>().in("bh",bhList));
    }
}
