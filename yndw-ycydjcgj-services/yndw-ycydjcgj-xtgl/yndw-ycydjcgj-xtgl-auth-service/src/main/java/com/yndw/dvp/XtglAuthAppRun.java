package com.yndw.dvp;

import com.yndw.dvp.common.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.tsf.annotation.EnableTsf;

/**
 * Create By Carlos
 * 2020/6/12
 */
//测试git测试
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsf
@EnableTransactionManagement
@EnableFeignInterceptor
@ServletComponentScan(value = "com.yndw.dvp.common.core.filter")
public class XtglAuthAppRun {
    public static void main(String[] args) {
        SpringApplication.run(XtglAuthAppRun.class, args);
    }
}

