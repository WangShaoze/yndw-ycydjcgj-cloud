package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum YyztdmConstant {

    START("1", "启用"),
    STOP("2", "停用");

    private String value;
    private String label;

    private YyztdmConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
