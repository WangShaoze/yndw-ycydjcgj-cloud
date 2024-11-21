package com.yndw.dvp.common.core.constant;

import lombok.Getter;

/**
 * Create By Carlos
 * 2020/11/20
 */

@Getter
public enum XmEvnConstant {

    TEST("test", "测试环境"),
    PROD("prod", "正式环境");

    private String value;
    private String label;

    private XmEvnConstant(String value, String label){
        this.value = value;
        this.label = label;
    }
}