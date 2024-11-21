package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/6/15
 */
@Getter
@Setter
public class XtGwxxDto implements Serializable {
    private static final long serialVersionUID = 7021958844843594733L;
    private String bh;
    @ApiModelProperty(value = "组织信息")
    private String zzbh;

    @ApiModelProperty(value = "岗位名称")
    private String gwmc;

    @ApiModelProperty(value = "岗位描述")
    private String gwms;

    @ApiModelProperty(value = "岗位用途代码")
    private String gwytdm;

    @ApiModelProperty(value = "岗位状态代码")
    private String gwztdm;
    private String gwztdmStr;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
}
