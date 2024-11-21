package com.yndw.dvp.xtgl.auth.feign.fallback;

import com.yndw.dvp.xtgl.auth.feign.IMallTbkService;
import com.yndw.dvp.xtgl.auth.model.Xttbk;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MallTbkServiceFallbackFactory implements FallbackFactory<IMallTbkService> {
    @Override
    public IMallTbkService create(Throwable cause) {
        return new IMallTbkService() {
            @Override
            public Xttbk getTbByYhbh(String bh) {
                return new Xttbk();
            }
        };
    }
}
