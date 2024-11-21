package com.yndw.dvp.common.log.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 审计日志
 *
 * 
 * @date 2020/2/3
 * <p>
 * 
 * 
 */
@Setter
@Getter
public class Audit {
    /**
     * 操作时间
     */
    private LocalDateTime timestamp;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 应用id
     */
    private String clientId;
    /**
     * 操作信息
     */
    private String operation;
    /**
     * 方法入参
     * */
    private String inParam;
    /**
     * 请求方IP
     * */
    private String requestIp;
    /**
     * 服务端IP
     * */
    private String serverIp;
    /**
     * 执行结果（0：失败 1：成功）
     * */
    private String methodResult;
}
