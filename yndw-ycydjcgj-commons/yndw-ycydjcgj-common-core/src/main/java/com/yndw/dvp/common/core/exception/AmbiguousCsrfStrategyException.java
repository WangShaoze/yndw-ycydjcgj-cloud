package com.yndw.dvp.common.core.exception;

/**
 * Description: 对于当前请求, 符合 CSRF 保护机制的策略有 0 个或者 大于1 个时, 抛出该异常
 * Create By Carlos
 * 2020/12/18
 */
public class AmbiguousCsrfStrategyException  extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Ambiguous csrf protecting strategy: %d";

    public AmbiguousCsrfStrategyException(int eligibleStrategyNum) {
        super(String.format(MESSAGE_TEMPLATE, eligibleStrategyNum));
    }
}