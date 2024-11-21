package com.yndw.dvp.xtgl.file.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Create By Carlos
 * 2020/7/6
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dvp.file-system")
@RefreshScope
public class FileProperties {
    /**
     * 默认的文件桶名称
     */
    private String defaultBucket;

    /**
     * 密钥的key
     */
    private String accessKey;

    /**
     * 密钥的钥匙
     */
    private String secretKey;

    /**
     * 文件服务器的url（全路径，例如：http://127.0.0.1:9000）
     */
    private String serverEndpoint;

    /**
     * 文件服务器的外网url（全路径，例如：http://172.29.3.29:9000）
     */
    private String externalServerEndpoint;

    /**
     * 文件过期时间，默认：秒
     */
    private Integer expireSeconds;
}
