package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuLeaf extends MenuMeta {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "")
    private boolean hidden = false;

    @ApiModelProperty(value = "组件路径")
    private String component;
}
