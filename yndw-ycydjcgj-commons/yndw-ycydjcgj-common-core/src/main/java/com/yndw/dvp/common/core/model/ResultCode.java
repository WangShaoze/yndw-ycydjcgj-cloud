package com.yndw.dvp.common.core.model;

/**
 * Create By Carlos
 * 2020/6/12
 */
public enum ResultCode {
    SUCCESS(0), //成功
    FAILED(1),//失败
    UNAUTHORIZED(401);//未认证或过期
    private Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}