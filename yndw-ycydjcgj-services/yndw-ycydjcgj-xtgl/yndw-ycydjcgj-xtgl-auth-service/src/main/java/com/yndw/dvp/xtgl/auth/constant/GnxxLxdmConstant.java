package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum GnxxLxdmConstant {
    MENU("1", "菜单"),
    PERMISSION("2", "权限");

    private String value;
    private String label;

    private GnxxLxdmConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
