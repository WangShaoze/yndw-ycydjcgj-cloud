package com.yndw.dvp.xtgl.auth.dto;

import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class XtSqGnxx implements Serializable {
    @ApiModelProperty(value = "已授权功能信息")
    private List<XtGnxx> ysqGnxx;
    @ApiModelProperty(value = "未授权功能信息")
    private List<XtGnxx> wsqGnxx;
}
