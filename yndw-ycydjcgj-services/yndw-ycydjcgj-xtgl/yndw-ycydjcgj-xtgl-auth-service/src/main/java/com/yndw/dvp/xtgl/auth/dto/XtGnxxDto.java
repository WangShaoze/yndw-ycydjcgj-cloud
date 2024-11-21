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
public class XtGnxxDto implements Serializable {
    private static final long serialVersionUID = 4210934512308284898L;
    private String bh;
    @ApiModelProperty(value = "上级功能编号")
    private String sjbh;
    @ApiModelProperty(value = "上级功能编号")
    private String sjbhs;
    @ApiModelProperty(value = "功能名称")
    private String gnmc;
    @ApiModelProperty(value = "功能描述")
    private String gnms;

    @ApiModelProperty(value = "所属应用编号")
    private String ssyybh;

    @ApiModelProperty(value = "功能链接地址")
    private String gnljdz;

    @ApiModelProperty(value = "功能请求方法")
    private String gnqqff;

    @ApiModelProperty(value = "功能权限标识")
    private String gnqxbz;


    @ApiModelProperty(value = "功能类型代码(1_菜单,2_权限)")
    private String gnlxdm;
    private String gnlxdmStr;

    @ApiModelProperty(value = "顺序号")
    private Integer djmksxh;

    @ApiModelProperty(value = "隐藏标志（是、否）")
    private String ycbz;
    private String ycbzStr;

    @ApiModelProperty(value = "功能状态代码（正常、停用等）")
    private String gnztdm;
    private String gnztdmStr;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

    @ApiModelProperty(value = "功能图标")
    private String gntb;

    private Boolean open = true;

    @ApiModelProperty(value = "应用名称")
    private String yymc;

    @ApiModelProperty(value = "图标编码")
    private String tbbh;
}
