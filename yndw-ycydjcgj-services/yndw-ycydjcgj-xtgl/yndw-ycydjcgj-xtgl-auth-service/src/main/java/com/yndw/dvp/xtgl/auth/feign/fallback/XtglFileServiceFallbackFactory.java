package com.yndw.dvp.xtgl.auth.feign.fallback;

import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.xtgl.auth.feign.XtglFileService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class XtglFileServiceFallbackFactory implements FallbackFactory<XtglFileService> {
    @Override
    public XtglFileService create(Throwable throwable) {
        return new XtglFileService() {
            @Override
            public Map<String,Object> upload(SecurityUser loginUser, MultipartFile file, String fjfz) {
                log.error("调用findMenuBysjbh异常：{}", loginUser, file, fjfz, throwable);
                return new HashMap<>();
            }
        };
    }
}
