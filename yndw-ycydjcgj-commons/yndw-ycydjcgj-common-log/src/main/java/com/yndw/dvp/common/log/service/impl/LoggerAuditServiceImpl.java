package com.yndw.dvp.common.log.service.impl;

import com.yndw.dvp.common.log.model.Audit;
import com.yndw.dvp.common.log.service.IAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.time.format.DateTimeFormatter;

/**
 * 审计日志实现类-打印日志
 *
 * 
 * @date 2020/2/3
 * <p>
 * 
 * 
 */
@Slf4j
@ConditionalOnProperty(name = "dvp.audit-log.log-type", havingValue = "logger", matchIfMissing = true)
public class LoggerAuditServiceImpl implements IAuditService {
    private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}|{}";

    /**
     * 格式为：{时间}|{应用名}|{类名}|{方法名}|{用户id}|{用户名}|{租户id}|{操作信息}
     * 例子：2020-02-04 09:13:34.650|user-center|com.yndw.dvp.user.controller.***Controller|saveOrUpdate|1|admin|webApp|新增用户:admin
     */
    @Override
    public void save(Audit audit) {
        log.debug(MSG_PATTERN
                , audit.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                , audit.getApplicationName(), audit.getClassName(), audit.getMethodName()
                , audit.getUserId(), audit.getUserName(), audit.getClientId()
                , audit.getOperation(), audit.getInParam(), audit.getServerIp(), audit.getRequestIp(), audit.getMethodResult());
    }
}
