package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 预警表
 * 2020/11/3
 * @author wangshoaze
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("yun_jin")
public class YuJin extends SuperEntity<YuJin> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预警信息表的唯一标识")
    @NotNull(message = "预警信息表的唯一标识不能为空")
    @Size(max = 11, message = "预警信息表的唯一标识最大长度不能超过11")
    private int id;

    @ApiModelProperty(value = "一级预警值")
    @NotNull(message = "一级预警值不能为空")
    @Size(max = 10, message = "角色标志最大长度不能超过10")
    private String yjyjz;

    @ApiModelProperty(value = "二级预警值")
    @NotNull(message = "二级预警值不能为空")
    @Size(max = 10, message = "角色标志最大长度不能超过10")
    private String erjyjz;

    @ApiModelProperty(value = "创建人ID")
    @Size(max = 64, message = "创建人ID最大长度不能超过11")
    private String createId;

    @ApiModelProperty(value = "创建人的账号")
    @Size(max = 16, message = "角色标志最大长度不能超过16")
    private String createName;

    @ApiModelProperty(value = "更新人ID")
    @Size(max = 64, message = "更新人ID最大长度不能超过11")
    private String updateId;

    @ApiModelProperty(value = "更新人的账号")
    @Size(max = 16, message = "更新人的账号最大长度不能超过16")
    private String updateName;

}
