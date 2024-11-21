package com.yndw.dvp.common.core.exception;

/**
 * Create By Carlos
 * 2020/12/18
 */
public class NoCachedCsrfTokenException extends RuntimeException {

    public NoCachedCsrfTokenException(String message) {
        super(message);
    }
}
