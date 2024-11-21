package com.yndw.dvp.common.core.exception;

import com.yndw.dvp.common.core.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * 异常通用处理
 *
 * 
 */
@ResponseBody
@Slf4j
public class DefaultExceptionAdvice {
    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public CommonResult badRequestException(IllegalArgumentException e) {
        return defHandler("参数解析失败", e);
    }

    /**
     * AccessDeniedException异常处理返回json
     * 返回状态码:403
     */
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public CommonResult badMethodExpressException(AccessDeniedException e) {
        return defHandler("没有权限请求当前方法", e);
    }

    /**
     * 返回状态码:405
     */
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return defHandler("不支持当前请求方法", e);
    }

    /**
     * 返回状态码:415
     */
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public CommonResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return defHandler("不支持当前媒体类型", e);
    }

    /**
     * SQLException sql异常处理
     * 返回状态码:500
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class})
    public CommonResult handleSQLException(SQLException e) {
        return defHandler("服务运行SQLException异常", e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:500
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public CommonResult handleException(BusinessException e) {
        return defHandler(e.getMessage(), e);
    }

    /**
     * IdempotencyException 幂等性异常
     * 返回状态码:200
     */
//    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IdempotencyException.class)
    public CommonResult handleException(IdempotencyException e) {
        return CommonResult.failed(e.getMessage());
    }


    /** hibernate validate exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleException(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return defHandler(error.getDefaultMessage(), e);
    }


    /**
     * 缺少crsf token
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoCachedCsrfTokenException.class)
    public CommonResult handleException(NoCachedCsrfTokenException e) {
        return defHandler("缺少跨站TOKEN", e);
    }

    /**
     * 对于当前请求, 符合 CSRF 保护机制的策略有 0 个或者 大于1 个时, 抛出该异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AmbiguousCsrfStrategyException.class)
    public CommonResult handleException(AmbiguousCsrfStrategyException e) {
        return defHandler("CSRF策略异常", e);
    }


    /**
     * 所有异常统一处理
     * 返回状态码:500
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        return defHandler("未知异常", e);
    }

    private CommonResult defHandler(String msg, Exception e) {
        log.error(msg, e);
        return CommonResult.failed(msg);
    }
}
