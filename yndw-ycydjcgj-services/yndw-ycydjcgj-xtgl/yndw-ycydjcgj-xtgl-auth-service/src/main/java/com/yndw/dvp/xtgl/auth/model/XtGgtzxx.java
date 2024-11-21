package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 公告通知信息
 * Create By Liangkh
 * 2021/4/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_ggtzxx")
public class XtGgtzxx extends SuperEntity<XtGgtzxx> {

    private static final long serialVersionUID = 1611895861712985627L;
    @ApiModelProperty(value = "公告标题")
    @NotNull(message = "公告标题不能为空")
    @Size(max = 256, message = "公告标题最大长度不能超过256")
    private String ggbt;

    @ApiModelProperty(value = "公告内容")
    @Size(max = 2048, message = "公告内容最大长度不能超过2048")
    private String ggnr;

    @ApiModelProperty(value = "紧急等级")
    @NotNull(message = "紧急等级不能为空")
    @Size(max = 8, message = "紧急等级最大长度不能超过8")
    private String jjdj;

    @ApiModelProperty(value = "公告状态")
    @Size(max = 32, message = "公告状态最大长度不能超过32")
    private String ggzt;

    @ApiModelProperty(value = "发布状态")
    @Size(max = 32, message = "公告状态最大长度不能超过32")
    private String fbzt;

    @ApiModelProperty(value = "当前用户编号")
    @Size(max = 256, message = "当前用户编号最大长度不能超过256")
    private String dqczybh;

    @ApiModelProperty(value = "创建人名称")
    @Size(max = 64, message = "创建人名称最大长度不能超过32")
    private String cjrmc;
}
