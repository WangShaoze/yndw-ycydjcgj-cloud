package com.yndw.dvp.common.core.constant;

import lombok.Getter;

@Getter
public enum ZtdmConstant {

    NORMAL("1", "正常"),
    STOP("2", "停用"),
    LOCK("3", "锁定");

    private String value;
    private String label;

    private ZtdmConstant(String value, String label){
        this.value = value;
        this.label = label;
    }

}
