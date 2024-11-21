package com.yndw.dvp.xtgl.auth.feign.fallback;

import com.yndw.dvp.xtgl.auth.feign.IMallYyxxService;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MallYyxxServiceFallbackFactory implements FallbackFactory<IMallYyxxService> {
    @Override
    public IMallYyxxService create(Throwable cause) {
        return new IMallYyxxService(){
            @Override
            public List<XtYyxx> findListByYhbh(String yhbh) {
                return new ArrayList<>();
            }

            @Override
            public List<XtYyxx> findList() {
                return new ArrayList<>();
            }
        };
    }
}
