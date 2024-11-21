package com.yndw.dvp.common.core.constant;

/**
 * Create By Carlos
 * 2020/12/18
 */
public  enum GrantType {

    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE("authorization_code"),

    /**
     * 隐式模式
     */
    IMPLICIT("implicit"),

    /**
     * 密码模式
     */
    PASSWORD("password"),

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials"),

    /**
     * 刷新令牌
     */
    REFRESH_TOKEN("refresh_token"),

    /**
     * 密码+验证码模式
     */
    PASSWORD_CODE("password_code");

    private final String code;

    GrantType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
