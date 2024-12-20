package com.yndw.dvp.common.log.service.impl;

import com.yndw.dvp.common.log.model.Audit;
import com.yndw.dvp.common.log.properties.AuditLogProperties;
import com.yndw.dvp.common.log.properties.LogDbProperties;
import com.yndw.dvp.common.log.service.IAuditService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * 审计日志实现类-数据库
 *
 * 
 * @date 2020/2/8
 * <p>
 * 
 * 
 */
@Slf4j
@ConditionalOnProperty(name = "dvp.audit-log.log-type", havingValue = "db")
@ConditionalOnClass(JdbcTemplate.class)
public class DbAuditServiceImpl implements IAuditService {
    private static final String INSERT_SQL = " insert into sys_logger " +
            " (application_name, class_name, method_name, user_id, user_name, client_id, operation, timestamp, in_param," +
            " request_ip, server_ip, method_result) " +
            " values (?,?,?,?,?,?,?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public DbAuditServiceImpl(@Autowired(required = false) LogDbProperties logDbProperties, DataSource dataSource) {
        //优先使用配置的日志数据源，否则使用默认的数据源
        if (logDbProperties != null && StringUtils.isNotEmpty(logDbProperties.getJdbcUrl())) {
            dataSource = new HikariDataSource(logDbProperties);
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS `sys_logger`  (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `application_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '应用名',\n" +
                "  `class_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类名',\n" +
                "  `method_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '方法名',\n" +
                "  `user_id` int(11) NULL COMMENT '用户id',\n" +
                "  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户名',\n" +
                "  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '租户id',\n" +
                "  `operation` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作信息',\n" +
                "  `timestamp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',\n" +
                "  `in_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '方法入参',\n "+
                "  `request_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方IP',\n" +
                "  `server_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务端IP',\n" +
                "  `method_result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行结果（0：失败 1：成功',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;";
        this.jdbcTemplate.execute(sql);
    }

    @Async
    @Override
    public void save(Audit audit) {
        this.jdbcTemplate.update(INSERT_SQL
                , audit.getApplicationName(), audit.getClassName(), audit.getMethodName()
                , audit.getUserId(), audit.getUserName(), audit.getClientId()
                , audit.getOperation(), audit.getTimestamp(), audit.getInParam()
                , audit.getServerIp(), audit.getRequestIp(), audit.getMethodResult());
    }
}
