package com.yndw.dvp;

import com.yndw.dvp.common.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.tsf.annotation.EnableTsf;

/**
 * 
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableRedisHttpSession
@SpringBootApplication
@EnableTsf
@EnableTransactionManagement
@EnableFeignInterceptor
@ServletComponentScan(value = "com.yndw.dvp.common.core.filter")
public class OAuthAppRun {
    public static void main(String[] args) {
        SpringApplication.run(OAuthAppRun.class, args);
    }
}
