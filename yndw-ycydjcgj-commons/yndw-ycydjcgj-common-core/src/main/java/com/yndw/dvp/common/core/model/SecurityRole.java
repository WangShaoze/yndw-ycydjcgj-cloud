package com.yndw.dvp.common.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityRole implements Serializable {
    private static final long serialVersionUID = 201573226498635896L;
    private String bh;
    private String jsmc;
}
