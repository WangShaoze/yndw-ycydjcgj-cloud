package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 用户表
 * 2020/11/3
 *
 * @author wangshoaze
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User  extends Model<User> {
    private static final long serialVersionUID = 14321432144321L;

    @ApiModelProperty(value = "用户表唯一标识")
//    @NotNull(message = "用户表唯一标识为空")
    @Size(max = 16, message = "用户表唯一标识最大长度不能超过11")
    private String id;

    @ApiModelProperty(value = "用户编号")
    @NotNull(message = "用户编号不能为空")
    @Size(max = 16, message = "用户编号最大长度不能超过16")
    private String userCode;

    @ApiModelProperty(value = "用户名称")
    @NotNull(message = "用户名称不能为空")
    @Size(max = 255, message = "用户名称最大长度不能超过255")
    private String userName;

    @ApiModelProperty(value = "所属供电所ID")
    @NotNull(message = "所属供电所ID不能为空")
    @Size(max = 64, message = "所属供电所ID最大长度不能超过64")
    private String suoId;

    @ApiModelProperty(value = "所属供电所")
    @NotNull(message = "所属供电所不能为空")
    @Size(max = 255, message = "所属供电所最大长度不能超过255")
    private String suo;

    @ApiModelProperty(value = "所属供电局ID")
//    @NotNull(message = "所属供电局ID不能为空")
    @Size(max = 64, message = "所属供电局ID最大长度不能超过64")
    private String juId;

    @ApiModelProperty(value = "所属供电局")
//    @NotNull(message = "所属供电局不能为空")
    @Size(max = 255, message = "所属供电局最大长度不能超过255")
    private String ju;

    @ApiModelProperty(value = "创建人ID")
    @Size(max = 64, message = "创建人ID最大长度不能超过64")
    private String createId;

    @ApiModelProperty(value = "创建人的账号")
    @Size(max = 16, message = "角色标志最大长度不能超过16")
    private String createName;

    @ApiModelProperty(value = "更新人ID")
    @Size(max = 64, message = "更新人ID最大长度不能超过64")
    private String updateId;

    @ApiModelProperty(value = "更新人的账号")
    @Size(max = 16, message = "更新人的账号最大长度不能超过16")
    private String updateName;

    @ApiModelProperty(value = "上级编号路径（一遍填写供电所到总局的路径）")
    @NotNull(message = "上级编号路径不能为空")
    @Size(max = 1024, message = "上级编号路径最大长度不能超过1024")
    private String sjbhs;
}

