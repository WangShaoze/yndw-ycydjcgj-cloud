package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/6/15
 *
 * @author tuobang
 */
@Getter
@Setter
public class XtJsxxDto implements Serializable {

    private static final long serialVersionUID = -7621681354989266684L;
    @ApiModelProperty(value = "编号")
    private String bh;
    @ApiModelProperty(value = "角色标志（是，否）")
    private String jsbz;
    private String jsbzStr;
    @ApiModelProperty(value = "上级角色编号")
    private String sjbh;
    private String sjbhs;

    @ApiModelProperty(value = "所属应用编号")
    private String ssyybh;

    @ApiModelProperty(value = "角色名称")
    private String jsmc;

    @ApiModelProperty(value = "角色描述")
    private String jsms;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

    @ApiModelProperty(value = "角色状态代码（正常、停用等）")
    private String jsztdm;
    private String jsztdmStr;

    private Boolean open = true;
}
