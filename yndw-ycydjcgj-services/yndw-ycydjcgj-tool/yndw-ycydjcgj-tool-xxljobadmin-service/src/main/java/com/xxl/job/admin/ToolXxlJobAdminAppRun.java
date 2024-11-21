package com.xxl.job.admin;

import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.tsf.annotation.EnableTsf;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@EnableTsf
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsfAuth //开启服务鉴权注解
@EnableTsfRoute//开启服务路由注解
@EnableTsfRateLimit//开启服务限流注解
@EnableTsfSleuth//开启调用链注解
@EnableTsfMonitor//开启监控注解
@ServletComponentScan(value = "com.yndw.dvp.common.core.filter")
public class ToolXxlJobAdminAppRun {
	public static void main(String[] args) {
        SpringApplication.run(ToolXxlJobAdminAppRun.class, args);
	}
}