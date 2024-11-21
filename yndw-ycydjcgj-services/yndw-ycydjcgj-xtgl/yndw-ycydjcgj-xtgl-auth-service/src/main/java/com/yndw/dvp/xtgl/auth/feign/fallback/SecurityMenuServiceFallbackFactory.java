package com.yndw.dvp.xtgl.auth.feign.fallback;

import cn.hutool.core.collection.CollectionUtil;
import com.yndw.dvp.xtgl.auth.feign.SecurityMenuService;
import com.yndw.dvp.xtgl.auth.model.XtDmflmx;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * menuService降级工场
 *
 * @date 2019/1/18
 */
@Slf4j
@Component
public class SecurityMenuServiceFallbackFactory implements FallbackFactory<SecurityMenuService> {
    @Override
    public SecurityMenuService create(Throwable throwable) {
        return new SecurityMenuService() {
            @Override
            public List<XtDmflmx> findMenuBySjbh(String sjbh) {
                log.error("调用findMenuBysjbh异常：{}", sjbh, throwable);
                return CollectionUtil.newArrayList();
            }
        };
    }
}
