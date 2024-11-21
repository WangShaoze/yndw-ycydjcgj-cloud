package com.yndw.dvp.common.core.constant;

import lombok.Getter;

@Getter
public enum GgztConstant {

    READ("1","已读"),
    UNREAD("0","未读");

    private String value;
    private String label;

    private GgztConstant(String value, String label){
        this.value = value;
        this.label = label;
    }

}
