package com.yndw.dvp.xtgl.auth.queryParam;

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
public class XtGnxxQueryParam extends PageQueryParam {
    private static final long serialVersionUID = 5450062828823054203L;
    @ApiModelProperty(name = "sjbh", value = "上级功能编号")
    private String sjbh;

    @ApiModelProperty(name = "ssyybh", value = "所属应用编号")
    private String ssyybh;

    public XtGnxxQueryParam(String sjbh, String ssyybh) {
        this.sjbh = sjbh;
        this.ssyybh = ssyybh;
    }

    public XtGnxxQueryParam() {
    }
}

