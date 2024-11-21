package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
    private static final long serialVersionUID = 4432143143214321L;

    @ApiModelProperty(value = "用户表唯一标识")
    private String id;

    @ApiModelProperty(value = "用户编号")
    private String userCode;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "所属供电所")
    private String suo;

    @ApiModelProperty(value = "所属供电所")
    private String suoId;

    @ApiModelProperty(value = "所属供电局")
    private String ju;

    @ApiModelProperty(value = "上级编号路径")
    private String sjbhs;


}
