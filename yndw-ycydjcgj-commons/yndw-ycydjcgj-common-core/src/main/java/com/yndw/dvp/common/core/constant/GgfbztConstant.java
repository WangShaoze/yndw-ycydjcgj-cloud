package com.yndw.dvp.common.core.constant;

        import lombok.Getter;

@Getter
public enum GgfbztConstant{

    PUBLISHED("1","已发布"),
    UNPUBLISHED("0","未发布");

    private String value;
    private String label;

    private GgfbztConstant(String value, String label){
        this.value = value;
        this.label = label;
    }

}

