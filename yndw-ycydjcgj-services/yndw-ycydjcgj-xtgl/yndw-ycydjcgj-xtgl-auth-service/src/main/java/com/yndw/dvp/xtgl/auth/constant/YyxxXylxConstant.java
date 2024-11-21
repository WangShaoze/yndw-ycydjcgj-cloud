package com.yndw.dvp.xtgl.auth.constant;

import lombok.Getter;

@Getter
public enum YyxxXylxConstant {

    HTTP("1", "HTTP"),
    HTTPS("2", "HTTPS");

    private String value;
    private String label;

    private YyxxXylxConstant(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
