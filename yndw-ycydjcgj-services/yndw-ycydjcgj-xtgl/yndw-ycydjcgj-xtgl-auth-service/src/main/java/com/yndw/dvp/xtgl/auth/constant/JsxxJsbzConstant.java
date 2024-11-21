package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum JsxxJsbzConstant {
    COMPANY("1", "分类"),
    ROLE("2", "角色");

    private String value;
    private String label;

    private JsxxJsbzConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
