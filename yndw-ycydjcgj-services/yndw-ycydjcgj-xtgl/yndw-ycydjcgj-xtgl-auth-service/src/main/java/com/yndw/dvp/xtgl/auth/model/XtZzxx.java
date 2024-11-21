package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 组织机构
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_zzxx")
public class XtZzxx extends SuperEntity<XtZzxx> {

    private static final long serialVersionUID = 7550719656414140963L;
    @ApiModelProperty(value = "组织名称")
    @NotNull(message = "组织名称不能为空")
    @Size(max = 200, message = "最大长度不能超过200")
    private String zzmc;

    @ApiModelProperty(value = "组织简称")
    @NotNull(message = "组织简称不能为空")
    @Size(max = 200, message = "最大长度不能超过200")
    private String zzjc;

    @ApiModelProperty(value = "组织地址")
    @NotNull(message = "组织地址不能为空")
    @Size(max = 200, message = "最大长度不能超过200")
    private String zzdz;

    @ApiModelProperty(value = "上级组织编号（直接上级）")
    @Size(max = 64, message = "最大长度不能超过64")
    private String sjbh;

    @ApiModelProperty(value = "上级组织编号路径")
    @Size(max = 1024, message = "最大长度不能超过1024")
    private String sjbhs;

    @ApiModelProperty(value = "所属单位编号（单位标志为是的组织编号）")
    private String ssdwbh;

    @ApiModelProperty(value = "单位标志（0、单位 1、部门）")
    @Size(max = 60, message = "最大长度不能超过60")
    private String dwbz;

    @ApiModelProperty(value = "办公电话号码")
    @Size(max = 20, message = "最大长度不能超过20")
    private String bgdhhm;

    @ApiModelProperty(value = "传真号码")
    @Size(max = 20, message = "最大长度不能超过20")
    private String czhm;

    @ApiModelProperty(value = "显示顺序号")
    private Integer xssxh;

    @ApiModelProperty(value = "组织状态代码（正常、停用、锁定等）")
    private String zzztdm;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
}
