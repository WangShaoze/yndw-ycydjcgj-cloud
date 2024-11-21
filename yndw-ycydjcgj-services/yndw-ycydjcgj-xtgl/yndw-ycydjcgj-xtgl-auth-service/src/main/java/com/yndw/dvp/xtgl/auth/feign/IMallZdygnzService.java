package com.yndw.dvp.xtgl.auth.feign;

import com.yndw.dvp.xtgl.auth.dto.XtZdygnzDto;
import com.yndw.dvp.xtgl.auth.feign.fallback.MallYyxxServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "yndw-ycydjcgj-mall-components", fallbackFactory = MallYyxxServiceFallbackFactory.class, decode404 = true)
public interface IMallZdygnzService {

    /**
     * 获取用户自定义功能组信息
     * @return
     */
    @GetMapping(value ="/yndw/mall/components/yndw-ycydjcgj-xtgl-auth-service/V1/zdygnz")
    List<XtZdygnzDto> getZdyGnzByYhbh(String yhbh);
}
