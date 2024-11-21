package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Getter
@Setter
@ApiModel
public class XtYyxxQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = -2607239132536859493L;

    private String bh;
    @ApiModelProperty(name = "yymc", value = "应用名称")
    private String yymc;

    @ApiModelProperty(name = "yyjc", value = "应用简称")
    private String yyjc;

    @ApiModelProperty(name = "ywjc", value = "英文简称")
    private String ywjc;

    @ApiModelProperty(name = "yylb", value = "应用类别")
    private String yylb;


}

