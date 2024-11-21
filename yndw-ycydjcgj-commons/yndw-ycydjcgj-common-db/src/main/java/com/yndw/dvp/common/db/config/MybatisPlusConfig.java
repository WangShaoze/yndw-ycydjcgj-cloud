package com.yndw.dvp.common.db.config;

import com.yndw.dvp.common.db.interceptor.ParameterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author gan
 * @Date 2024/5/10 9:41
 * @PackageName:com.yndw.dvp.common.db.config
 * @ClassName: MybatisPlusConfig 防SQL注入 % _ \
 * @Description: TODO
 * @Version 1.0
 */

@Configuration
public class MybatisPlusConfig {

    @Bean
    public ParameterInterceptor mybatisPlusInterceptor() {
        ParameterInterceptor interceptor = new ParameterInterceptor();
        return interceptor;
    }
}
