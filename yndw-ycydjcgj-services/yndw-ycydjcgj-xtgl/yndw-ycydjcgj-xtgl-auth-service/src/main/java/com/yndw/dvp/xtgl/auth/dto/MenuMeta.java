package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuMeta {

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    private String link;

    @ApiModelProperty(value = "是否缓存")
    private boolean noCache = false;

    @ApiModelProperty(value = "标题")
    private String title;
}
