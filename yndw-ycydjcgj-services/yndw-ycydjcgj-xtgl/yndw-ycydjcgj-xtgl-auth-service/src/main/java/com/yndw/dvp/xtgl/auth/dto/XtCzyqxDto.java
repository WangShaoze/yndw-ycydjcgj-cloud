package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Data
@ApiModel
public class XtCzyqxDto implements Serializable {
    private static final long serialVersionUID = 1579581110822247862L;
    private String czybh;
    private String jsbh;
    private String gnbh;
    private int usecount;//使用数
}
