package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/6/15
 */
@Getter
@Setter
public class XtYyxxDto implements Serializable {

    private static final long serialVersionUID = 6666966568543368649L;
    @ApiModelProperty(value = "应用编号")
    private String bh;
    @ApiModelProperty(value = "应用编码")
    private String yybm;
    @ApiModelProperty(value = "应用名称")
    private String yymc;
    @ApiModelProperty(value = "应用简称")
    private String yyjc;
    @ApiModelProperty(value = "英文简称")
    private String ywjc;
    @ApiModelProperty(value = "应用描述")
    private String yyms;
    @ApiModelProperty(value = "应用建设方")
    private String yyjsf;
    @ApiModelProperty(value = "建设方负责人")
    private String jsffzr;
    @ApiModelProperty(value = "应用承建方")
    private String yycjf;
    @ApiModelProperty(value = "承建方负责人")
    private String cjffzr;
    @ApiModelProperty(value = "服务器IP")
    private String fwqip;
    @ApiModelProperty(value = "应用端口")
    private String yydk;
    @ApiModelProperty(value = "应用状态（1：启用；2：停用；）")
    private String yyztdm;
    private String yyztdmStr;
    @ApiModelProperty(value = "协议类型（1：http；2：https）")
    private String xylx;
    private String xylxStr;
    @ApiModelProperty(value = "应用类别")
    private String yylb;
    @ApiModelProperty(value = "顺序号")
    private int sxh;
    @ApiModelProperty(value = "应用图标")
    private String yytb;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
//    @ApiModelProperty(value = "应用首页")
//    private String yysy;

}
