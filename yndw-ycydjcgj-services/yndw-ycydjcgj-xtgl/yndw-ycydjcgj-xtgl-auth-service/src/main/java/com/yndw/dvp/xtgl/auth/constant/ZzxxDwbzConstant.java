package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum ZzxxDwbzConstant {
    COMPANY("1", "单位"),
    DEPT("2", "部门");

    private String value;
    private String label;

    private ZzxxDwbzConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
