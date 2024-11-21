package com.yndw.dvp.gateway.feign.fallback;

import cn.hutool.core.collection.CollectionUtil;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.gateway.feign.SecurityMenuService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * menuService降级工场
 *
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
            public List<SecurityMenu> findMenuByJsbhs(String czybhs) {
                log.error("调用findMenuByJsbhs异常：{}", czybhs, throwable);
                return CollectionUtil.newArrayList();
            }

            @Override
            public List<SecurityMenu> findGnxxByCzybh(String czybh) {

                log.error("调用findGnxxByCzybh异常：{}", czybh, throwable);
                return CollectionUtil.newArrayList();
            }

            @Override
            public List<SecurityMenu> authGnxxByCzy(String czybh) {
                log.error("调用authGnxxByCzy异常：{}", czybh, throwable);
                return CollectionUtil.newArrayList();
            }
        };
    }
}
