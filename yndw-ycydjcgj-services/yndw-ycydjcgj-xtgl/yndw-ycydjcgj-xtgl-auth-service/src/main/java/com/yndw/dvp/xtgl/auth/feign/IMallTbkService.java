package com.yndw.dvp.xtgl.auth.feign;

import com.yndw.dvp.xtgl.auth.feign.fallback.MallYyxxServiceFallbackFactory;
import com.yndw.dvp.xtgl.auth.model.Xttbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "yndw-ycydjcgj-mall-components", fallbackFactory = MallYyxxServiceFallbackFactory.class, decode404 = true)
public interface IMallTbkService {

    /**
     * 获取应用列表
     * @return
     */
    @GetMapping(value ="/yndw/mall/components/yndw-ycydjcgj-mall-components/V1/tbk/getTbByYhbh")
    Xttbk getTbByYhbh(String bh);
}
