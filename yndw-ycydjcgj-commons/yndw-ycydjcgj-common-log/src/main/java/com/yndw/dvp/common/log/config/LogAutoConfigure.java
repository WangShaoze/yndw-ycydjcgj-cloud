package com.yndw.dvp.common.log.config;

import com.yndw.dvp.common.log.properties.AuditLogProperties;
import com.yndw.dvp.common.log.properties.LogDbProperties;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动配置
 *
 * 
 * @date 2019/8/13
 */
@EnableConfigurationProperties({AuditLogProperties.class})
public class LogAutoConfigure {
    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}
}
