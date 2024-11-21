package com.yndw.dvp.xtgl.auth.feign;

import com.yndw.dvp.xtgl.auth.feign.fallback.ToolJenkinsServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "yndw-ycydjcgj-tool-jenkins-service", fallbackFactory = ToolJenkinsServiceFallbackFactory.class, decode404 = true)
public interface ToolJenkinsService {

    @PostMapping("/yndw/tool/yndw-ycydjcgj-tool-jenkins-service/V1/job/uploadComponent")
    Map<String,Object> uploadComponent(@RequestParam("jobName") String jobName, @RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType, @RequestParam("fileBh") String fileBh, @RequestParam("groupId") String groupId, @RequestParam("artifactId") String artifactId, @RequestParam("version") String version);

    @PostMapping("/yndw/tool/yndw-ycydjcgj-tool-jenkins-service/V1/log/doJob")
    Map<String,Object> doJob(@RequestParam("jobName") String jobName);
}
