package com.yndw.dvp.xtgl.auth.feign;


import com.yndw.dvp.xtgl.auth.feign.fallback.SecurityMenuServiceFallbackFactory;
import com.yndw.dvp.xtgl.auth.model.XtDmflmx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author carlos
 */
@FeignClient(name = "yndw-ycydjcgj-xtgl-dm-service", fallbackFactory = SecurityMenuServiceFallbackFactory.class, decode404 = true)
public interface SecurityMenuService {
    /**
     * 代码菜单列表
     *
     * @param sjbh
     */
    @GetMapping(value = "/yndw/xtgl/dm/yndw-ycydjcgj-xtgl-dm-service/V1/dmflmx/findMenuBySjbh")
    List<XtDmflmx> findMenuBySjbh(@RequestParam("sjbh") String sjbh);
}
