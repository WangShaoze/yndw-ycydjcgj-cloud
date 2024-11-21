package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum GnxxYcbzConstant {
    SHOW("1", "显示"),
    HIDE("2", "隐藏");

    private String value;
    private String label;

    private GnxxYcbzConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
