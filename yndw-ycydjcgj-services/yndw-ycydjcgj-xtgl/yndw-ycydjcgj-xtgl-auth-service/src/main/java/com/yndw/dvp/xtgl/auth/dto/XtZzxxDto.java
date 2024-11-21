package com.yndw.dvp.xtgl.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class XtZzxxDto implements Serializable {
    private static final long serialVersionUID = -2025183858528092333L;
    @ApiModelProperty(value = "组织编号")
    private String bh;

    @ApiModelProperty(value = "组织名称")
    private String zzmc;

    @ApiModelProperty(value = "组织简称")
    private String zzjc;

    @ApiModelProperty(value = "组织地址")
    private String zzdz;

    @ApiModelProperty(value = "上级组织编号（直接上级）")
    private String sjbh;
    @ApiModelProperty(value = "上级组织编号路径")
    private String sjbhs;

    @ApiModelProperty(value = "所属单位编号（单位标志为是的组织编号）")
    private String ssdwbh;

    @ApiModelProperty(value = "单位标志（0、单位 1、部门）")
    private String dwbz;
    private String dwbzStr;

    @ApiModelProperty(value = "办公电话号码")
    private String bgdhhm;

    @ApiModelProperty(value = "传真号码")
    private String czhm;

    @ApiModelProperty(value = "显示顺序号")
    private Integer xssxh;

    @ApiModelProperty(value = "组织状态代码（正常、停用、锁定等）")
    private String zzztdm;
    private String zzztdmStr;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

    private Boolean open = true;
}
