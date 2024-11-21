package com.yndw.dvp.xtgl.auth.feign;


import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.xtgl.auth.feign.fallback.XtglFileServiceFallbackFactory;
import com.yndw.dvp.xtgl.auth.model.XtDmflmx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author carlos
 */
@FeignClient(name = "yndw-ycydjcgj-xtgl-file-service", fallbackFactory = XtglFileServiceFallbackFactory.class, decode404 = true)
public interface XtglFileService {

    @PostMapping(value = "/yndw/xtgl/file/yndw-ycydjcgj-xtgl-file-service/V1/fjxx/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String,Object> upload(@RequestParam("loginUser") SecurityUser loginUser, @RequestPart("file") MultipartFile file, @RequestParam(value = "fjfz") String fjfz);
}
