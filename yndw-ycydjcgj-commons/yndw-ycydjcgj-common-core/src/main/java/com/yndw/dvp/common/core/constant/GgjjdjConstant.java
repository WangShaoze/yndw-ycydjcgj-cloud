package com.yndw.dvp.common.core.constant;

import lombok.Getter;

@Getter
public enum GgjjdjConstant {

    URGENT("0","紧急"),
    IMPORTANT("1","重要"),
    ORDINARY("2", "正常");

    private String value;
    private String label;

    private GgjjdjConstant(String value, String label){
        this.value = value;
        this.label = label;
    }

}
