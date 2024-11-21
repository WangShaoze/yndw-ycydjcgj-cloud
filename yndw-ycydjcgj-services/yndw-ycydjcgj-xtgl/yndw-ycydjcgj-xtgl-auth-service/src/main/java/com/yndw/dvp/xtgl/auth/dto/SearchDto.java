package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SearchDto implements Serializable {
    private static final long serialVersionUID = 4210934512308284898L;
    private  String bh;
    @ApiModelProperty(value = "名称")
    private String mc;
    @ApiModelProperty(value = "描述")
    private String ms;
    @ApiModelProperty(value = "厂家或所属应用")
    private String cj;
    @ApiModelProperty(value = "图标")
    private String tb;
    @ApiModelProperty(value = "创建时间")
    private String cjsj;
}
