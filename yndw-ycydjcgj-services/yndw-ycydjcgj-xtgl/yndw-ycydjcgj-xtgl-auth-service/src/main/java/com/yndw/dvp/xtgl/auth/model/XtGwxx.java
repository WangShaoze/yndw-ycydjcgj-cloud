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
 * 岗位
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_gwxx")
public class XtGwxx extends SuperEntity<XtGwxx> {

    private static final long serialVersionUID = 971052909799212035L;
    @ApiModelProperty(value = "组织信息")
    private String zzbh;

    @ApiModelProperty(value = "岗位名称")
    @NotNull(message = "岗位名称不能为空")
    @Size(max = 200, message = "岗位名称最大长度不能超过200")
    private String gwmc;

    @ApiModelProperty(value = "岗位描述")
    @Size(max = 500, message = "岗位描述最大长度不能超过500")
    private String gwms;

    @ApiModelProperty(value = "岗位用途代码")
    @Size(max = 8, message = "岗位用途代码最大长度不能超过8")
    private String gwytdm;

    @ApiModelProperty(value = "岗位状态代码")
    @Size(max = 8, message = "岗位状态代码最大长度不能超过8")
    private String gwztdm;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
}

