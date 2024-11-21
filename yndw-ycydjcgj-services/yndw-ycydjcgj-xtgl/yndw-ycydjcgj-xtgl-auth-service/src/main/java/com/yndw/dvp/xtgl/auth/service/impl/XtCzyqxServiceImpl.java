package com.yndw.dvp.xtgl.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.auth.dto.CzyqxDto;
import com.yndw.dvp.xtgl.auth.dto.GnTopDto;
import com.yndw.dvp.xtgl.auth.dto.MyAppDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.feign.IMallTbkService;
import com.yndw.dvp.xtgl.auth.feign.SecurityMenuService;
import com.yndw.dvp.xtgl.auth.feign.ToolJenkinsService;
import com.yndw.dvp.xtgl.auth.feign.XtglFileService;
import com.yndw.dvp.xtgl.auth.mapper.*;
import com.yndw.dvp.xtgl.auth.model.*;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtCzyqxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/6/12
 *
 * @author carlos
 */
@Slf4j
@Transactional
@Service
public class XtCzyqxServiceImpl extends SuperServiceImpl<XtCzyqxMapper, XtCzyqx> implements IXtCzyqxService {

    @Autowired
    private XtCzyqxMapper czyqxMapper;
    @Autowired
    private XtGnxxMapper gnxxMapper;
    @Autowired
    private IMallTbkService tbkService;
    @Autowired
    private XtglFileService xtglFileService;
    @Autowired
    private ToolJenkinsService toolJenkinsService;

    @Override
    public List<CzyqxDto> getCzyqxbyCzybh(String czybh) {
        return czyqxMapper.getTopYy6(czybh);
    }

    @Override
    public Page<CzyqxDto> getAllYyByUsecount(SecurityUser securityUser, int page, int size) {

        QueryWrapper q = new QueryWrapper();
        q.select("yybh,SUM(usecount) usecount");
        if (securityUser!=null && securityUser.getBh()!=""){
            q.eq("czybh",securityUser.getBh());
        }
        q.groupBy("yybh");
        return this.page(new Page<>((page - 1) * size, size), q);
    }

    @Override
    public Page<MyAppDto> getMyApp(String czybh, int page, int size) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("COUNT(DISTINCT czybh) yyrs,yybh,czybh");
        queryWrapper.groupBy("yybh");
        queryWrapper.eq("czybh", czybh);
        return this.page(new Page<>((page - 1) * size, size),queryWrapper);
    }

    @Override
    public String getGnbhsByCzybh(String czybh) {
        List<String> gnbhList = czyqxMapper.getGnbhsByCzybh(czybh);

        if (!gnbhList.isEmpty()) {
            return "," + CollectionUtil.join(gnbhList, ",") + ",";
        }
        return "";
    }

    @Override
    public String getGnbhsByGwbh(String gwbh) {
        List<String> gnbhList = czyqxMapper.getGnbhsByGwbh(gwbh);

        if (!gnbhList.isEmpty()) {
            return "," + CollectionUtil.join(gnbhList, ",") + ",";
        }
        return "";
    }

    @Override
    public String getYybhsByCzybh(String czybh) {
        List<String> gnbhList = czyqxMapper.getYybhsByCzybh(czybh);

        if (!gnbhList.isEmpty()) {
            return "," + CollectionUtil.join(gnbhList, ",") + ",";
        }
        return "";
    }

    @Override
    public List<XtGnxx> findList(XtGnxxQueryParam queryParam) {
        QueryWrapper<XtGnxx> queryWrapper = buildQueryWapper(queryParam);
        return gnxxMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setGnxxToCzyqxx(String czybh, List<YyUITreeDto> gnxxList) {
        czyqxMapper.deleteCzyGnxx(czybh, "0");
        YyUITreeDto gnxx = null;
        String gnbh = "";
        String yybh = "";
        for (int i = 0; i < gnxxList.size(); i++) {
            gnxx = gnxxList.get(i);
            if(StringUtils.isNotEmpty(gnxx.getSsyyid())){
                gnbh = gnxx.getId();
                yybh = gnxx.getSsyyid();
                czyqxMapper.saveCzyGnxx(czybh, gnbh, yybh);
            }
        }
    }

    @Override
    public void addGn(String czybh, String gnbh, String gwbh) {
        String jsbh1 = getJsbh(gwbh);
        if (!StrUtil.isEmpty(jsbh1)) {
            List<String> jsbhs = StrUtil.split(jsbh1, ',');
            jsbhs.forEach(jsbh -> czyqxMapper.gnadd(czybh, jsbh, gnbh));
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setJsGnxxToCzyqxx(String jsbh, String gnbhs) {
        czyqxMapper.deleteCzyqxGnxx(jsbh, null);
        String czybh = getCzybh(jsbh);
        if (!StrUtil.isEmpty(czybh)) {
            List<String> czybhs = StrUtil.split(czybh, ',');
            if (!StrUtil.isEmpty(gnbhs)) {
                List<String> gnbhList = StrUtil.split(gnbhs, ',');
                for (int i = 0; i < czybhs.size(); i++) {
                    String tmpCzybh = czybhs.get(i);
                    gnbhList.forEach(gnbh -> czyqxMapper.saveCzyqxGnxx(jsbh, gnbh, tmpCzybh));
                }
            }
        }
    }

    @Override
    public List<SecurityMenu> findGnxxByCzybh(String czybh, String gnlxdm) {
        List<XtGnxx> gnxxList = null;
        gnxxList = czyqxMapper.findGnxxByCzybh(czybh, gnlxdm);
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
    public int saveCzyqx(XtCzyqx param) {
        return czyqxMapper.saveCzyqx(param);
    }

    public String getCzybh(String jsbh) {
        List<String> czybhList = czyqxMapper.getCzybh(jsbh);
        if (!czybhList.isEmpty()) {
            return CollectionUtil.join(czybhList, ",");
        }
        return "";
    }


    public String getJsbh(String gwbh) {
        List<String> czybhList = czyqxMapper.getJsbh(gwbh);
        if (!czybhList.isEmpty()) {
            return CollectionUtil.join(czybhList, ",");
        }
        return "";
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
    public List<XtGnxx> findMenuList(XtGnxxQueryParam queryParam) {
        QueryWrapper<XtGnxx> queryWrapper = buildWapper("1");
        return gnxxMapper.selectList(queryWrapper);
    }


    private QueryWrapper<XtGnxx> buildWapper(String gnlxdm) {
        QueryWrapper query = new QueryWrapper<XtGnxx>();
        if (StrUtil.isNotEmpty(gnlxdm)) {
            query.eq("gnlxdm", gnlxdm);
            query.orderByAsc("djmksxh");
        }
        return query;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<GnTopDto> getGnTopten() {
        List<GnTopDto> gnTopDtos = czyqxMapper.gnTopten();
        for (int i = 0; i < gnTopDtos.size(); i++) {
            GnTopDto gnTopDto = gnTopDtos.get(i);
            String tbbh = gnTopDto.getTbbh();
            Xttbk xttbk = tbkService.getTbByYhbh(tbbh);
            String tbbm = xttbk.getTbbm();
            gnTopDto.setTbbm(tbbm);
        }
        return gnTopDtos;
    }


    @Override
    public CommonResult uploadComponent(SecurityUser loginUser, MultipartFile file, String fjfz, String hj, String groupId, String artifactId, String version) {
        Map<String, Object> fileResult = xtglFileService.upload(loginUser, file, fjfz);
        String wjmcAndWjbh = fileResult.get("data").toString();
        Map<String, Object> create = toolJenkinsService.uploadComponent("maven-" + wjmcAndWjbh.split("&&")[1] + "-job-upload", wjmcAndWjbh.split("&&")[1], hj, wjmcAndWjbh.split("&&")[0], groupId, artifactId, version);
        Map<String, Object> active = new HashMap<>();
        if ("0".equals(create.get("code").toString())) {
            active = toolJenkinsService.doJob("maven-" + wjmcAndWjbh.split("&&")[1] + "-job-upload");
        }

        return "0".equals(active.get("code").toString()) ? CommonResult.succeed("maven-" + wjmcAndWjbh.split("&&")[0] + "-job-upload", "") : CommonResult.failed(active.get("msg").toString());

    }
}
