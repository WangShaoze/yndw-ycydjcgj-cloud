package com.yndw.dvp.xtgl.auth.feign.fallback;

import com.yndw.dvp.xtgl.auth.dto.XtZdygnzDto;
import com.yndw.dvp.xtgl.auth.feign.IMallZdygnzService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MallZdygnzServiceFallbackFactory implements FallbackFactory<IMallZdygnzService> {
    @Override
    public IMallZdygnzService create(Throwable cause) {
        return new IMallZdygnzService() {
            @Override
            public List<XtZdygnzDto> getZdyGnzByYhbh(String yhbh) {
                return new ArrayList<>();
            }
        };
    }
}
