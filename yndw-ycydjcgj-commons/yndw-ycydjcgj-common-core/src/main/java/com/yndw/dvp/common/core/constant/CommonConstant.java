package com.yndw.dvp.common.core.constant;

/**
 * 全局公共常量
 *
 *
 * @date 2018/10/29
 */
public interface CommonConstant {
    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * The access token issued by the authorization server. This value is REQUIRED.
     */
    String ACCESS_TOKEN = "access_token";

    String BEARER_TYPE = "Bearer";

    /**
     * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "ycydjcgjgly";

    String ADMIN_GW_BH = "2222222";

    String DEF_USER_PASSWORD = "@Passw0rd";

    String LOCK_KEY_PREFIX = "LOCK_KEY:";

    /**
     * 租户id参数
     */
    String TENANT_ID_PARAM = "tenantId";

    /**
     * 负载均衡策略-版本号 信息头
     */
    String Z_L_T_VERSION = "z-l-t-version";

    /**
     * 注册中心元数据 版本号
     */
    String METADATA_VERSION = "version";

    String TREE_TOP_PID = "0";

    String TREE_TOP_PIDS = ",0,";
    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";
    String TIME_ZONE_GMT8 = "GMT+8";

    String UPLOAD_URL = "/yndw/xtgl/file/yndw-ycydjcgj-xtgl-file-service/V1/fjxx/upload";
}
