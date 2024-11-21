package com.yndw.dvp.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: dvp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public static <T> CommonResult<T> succeed(String msg) {
        return build(null, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> CommonResult<T> succeed(T model, String msg) {
        return build(model, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> CommonResult<T> succeed(T model) {
        return build(model, ResultCode.SUCCESS.getCode(), "");
    }

    public static <T> CommonResult<T> build(T datas, Integer code, String msg) {
        return new CommonResult<>(datas, code, msg);
    }

    public static <T> CommonResult<T> failed(String msg) {
        return build(null, ResultCode.FAILED.getCode(), msg);
    }

    public static <T> CommonResult<T> failed(T model, String msg) {
        return build(model, ResultCode.FAILED.getCode(), msg);
    }

    public static <T> CommonResult<T> unauthorized(String msg) {
        return build(null, ResultCode.UNAUTHORIZED.getCode(), msg);
    }
}
