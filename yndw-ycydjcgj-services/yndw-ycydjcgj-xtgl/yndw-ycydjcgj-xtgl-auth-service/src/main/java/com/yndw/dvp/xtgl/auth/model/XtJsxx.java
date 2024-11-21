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
 * 角色
 * Create By Carlos
 * 2020/6/12
 *
 * @author tuobang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_jsxx")
public class XtJsxx extends SuperEntity<XtJsxx> {

    private static final long serialVersionUID = -1680920960985487638L;
    @ApiModelProperty(value = "角色标志（是，否）")
    @NotNull(message = "角色标志不能为空")
    @Size(max = 8, message = "角色标志最大长度不能超过8")
    private String jsbz;

    @ApiModelProperty(value = "上级角色编号")
    @Size(max = 64, message = "上级角色编号最大长度不能超过64")
    private String sjbh;

    @ApiModelProperty(value = "上级角色编号集")
    @Size(max = 1024, message = "上级角色编号最大长度不能超过1024")
    private String sjbhs;

    @ApiModelProperty(value = "所属应用编号")
    @Size(max = 64, message = "所属应用编号最大长度不能超过64")
    private String ssyybh;

    @ApiModelProperty(value = "角色名称")
    @NotNull(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称最大长度不能超过100")
    private String jsmc;

    @ApiModelProperty(value = "角色描述")
    @Size(max = 200, message = "角色描述最大长度不能超过200")
    private String jsms;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

    @ApiModelProperty(value = "角色状态代码（正常、停用等）")
    @NotNull(message = "角色状态代码不能为空")
    @Size(max = 8, message = "角色状态代码最大长度不能超过8")
    private String jsztdm;

}
