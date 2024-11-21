package com.yndw.dvp.xtgl.auth.feign;

import com.yndw.dvp.xtgl.auth.feign.fallback.MallYyxxServiceFallbackFactory;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "yndw-ycydjcgj-mall-components", fallbackFactory = MallYyxxServiceFallbackFactory.class, decode404 = true)
public interface IMallYyxxService {

    /**
     * 获取应用列表
     * @return
     */
    @GetMapping(value ="/yndw/mall/components/yndw-ycydjcgj-mall-components/V1/yyxx/findList")
    List<XtYyxx> findList();

    /**
     * 通过用户编号获取应用列表
     * @return
     */
    @GetMapping(value ="/yndw/mall/components/yndw-ycydjcgj-mall-components/V1/yyxx/findListByYhbh")
    List<XtYyxx> findListByYhbh(String yhbh);
}
