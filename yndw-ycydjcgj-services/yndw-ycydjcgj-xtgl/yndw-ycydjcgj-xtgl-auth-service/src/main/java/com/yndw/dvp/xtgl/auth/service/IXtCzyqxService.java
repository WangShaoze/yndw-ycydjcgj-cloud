package com.yndw.dvp.xtgl.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.auth.dto.CzyqxDto;
import com.yndw.dvp.xtgl.auth.dto.GnTopDto;
import com.yndw.dvp.xtgl.auth.dto.MyAppDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyqx;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IXtCzyqxService extends ISuperService<XtCzyqx> {

    List<CzyqxDto> getCzyqxbyCzybh(String czybh);

    Page<CzyqxDto> getAllYyByUsecount(SecurityUser securityUser, int page, int size);

    Page<MyAppDto> getMyApp(String czybh, int page, int size);

    String getGnbhsByCzybh(String czybh);

    String getGnbhsByGwbh(String gwbh);

    String getYybhsByCzybh(String czybh);

    List<XtGnxx> findList(XtGnxxQueryParam queryParam);

    void setGnxxToCzyqxx(String czybh, List<YyUITreeDto> gnxxList);

    void addGn(String czybh, String gnmc, String gwbh);

    void setJsGnxxToCzyqxx(String czybh, String gnbhs);

    List<GnTopDto> getGnTopten();

    List<SecurityMenu> findGnxxByCzybh(String czybh, String gnlxdm);

    int saveCzyqx(XtCzyqx param);

    /**
     * 以下将移植到zdygnz相关类下
     */
    List<XtGnxx> findMenuList(XtGnxxQueryParam queryParam);

    CommonResult uploadComponent(SecurityUser loginUser, MultipartFile file, String fjfz, String hj, String groupId, String artifactId, String version);
}
