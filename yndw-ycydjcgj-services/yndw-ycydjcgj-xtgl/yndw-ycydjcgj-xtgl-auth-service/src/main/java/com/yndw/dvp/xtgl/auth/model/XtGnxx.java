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
 * 功能
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_gnxx")
public class XtGnxx extends SuperEntity<XtGnxx> {

    private static final long serialVersionUID = 3085212797517399621L;

    @ApiModelProperty(value = "上级功能编号(操作的上级是功能,功能的上级是模块,模块的上级也可以是模块)")
    @Size(max = 32, message = "上级功能编号最大长度不能超过32")
    private String sjbh;
    @ApiModelProperty(value = "上级功能编号集")
    @Size(max = 1024, message = "上级功能编号集最大长度不能超过1024")
    private String sjbhs;

    @ApiModelProperty(value = "功能名称")
    @NotNull(message = "功能名称不能为空")
    @Size(max = 60, message = "功能名称最大长度不能超过60")
    private String gnmc;

    @ApiModelProperty(value = "功能描述")
    @Size(max = 256, message = "功能描述最大长度不能超过256")
    private String gnms;

    @ApiModelProperty(value = "所属应用编号")
    @Size(max = 64, message = "所属应用编号最大长度不能超过64")
    private String ssyybh;

    @ApiModelProperty(value = "功能链接地址")
    @NotNull(message = "功能链接地址不能为空")
    @Size(max = 128, message = "功能链接地址最大长度不能超过128")
    private String gnljdz;

    @ApiModelProperty(value = "功能请求方法")
    @Size(max = 128, message = "功能请求方法最大长度不能超过128")
    private String gnqqff;

    @ApiModelProperty(value = "功能权限标识")
    @Size(max = 128, message = "功能权限标识最大长度不能超过128")
    private String gnqxbz;


    @ApiModelProperty(value = "功能类型代码(1_菜单,2_权限)")
    @NotNull(message = "功能类型代码不能为空")
    @Size(max = 8, message = "功能类型代码最大长度不能超过8")
    private String gnlxdm;

    @ApiModelProperty(value = "顺序号")
    @NotNull(message = "顺序号不能为空")
    private Integer djmksxh;

    @ApiModelProperty(value = "隐藏标志（是、否）")
    @NotNull(message = "隐藏标志不能为空")
    @Size(max = 8, message = "隐藏标志最大长度不能超过8")
    private String ycbz;

    @ApiModelProperty(value = "功能状态代码（正常、停用等）")
    @Size(max = 8, message = "功能状态代码最大长度不能超过8")
    private String gnztdm;

    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

    @ApiModelProperty(value = "图标编码")
    private String tbbh;
}
