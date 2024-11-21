package com.yndw.dvp.xtgl.auth.feign.fallback;

import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.xtgl.auth.feign.ToolJenkinsService;
import com.yndw.dvp.xtgl.auth.feign.XtglFileService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ToolJenkinsServiceFallbackFactory implements FallbackFactory<ToolJenkinsService> {
    @Override
    public ToolJenkinsService create(Throwable throwable) {
        return new ToolJenkinsService() {
            @Override
            public Map<String,Object> uploadComponent(String jobName, String fileName, String fileType, String fileBh, String groupId, String artifactId, String version) {
                log.error("调用findMenuBysjbh异常：{}", jobName, fileName, fileType, fileBh, groupId , artifactId , version, throwable);
                return new HashMap<>();
            }

            @Override
            public Map<String,Object> doJob(String jobName) {
                log.error("构建镜像异常：任务名称：{}",jobName,throwable);
                return new HashMap<>();
            }
        };
    }
}
