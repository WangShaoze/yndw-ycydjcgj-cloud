package com.yndw.dvp.xtgl.auth.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 公告通知信息
 * <p>
 * 2021/4/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_ggtzxx")
public class XtGgtzxxDto extends SuperEntity<XtGgtzxxDto> {

    private static final long serialVersionUID = -5618961641176224060L;
    @ApiModelProperty(value = "公告标题")
    @NotNull(message = "公告标题不能为空")
    private String ggbt;

    @ApiModelProperty(value = "公告内容")
    private String ggnr;

    @ApiModelProperty(value = "紧急等级(0：紧急；1：重要；2：普通)")
    private String jjdj;
    private String jjdjStr;

    @ApiModelProperty(value = "公告状态(0：未读；1已读)")
    private String ggzt;
    private String ggztStr;

    @ApiModelProperty(value = "发布状态(0:未发布；1已发布)")
    private String fbzt;
    private String fbztStr;

    @ApiModelProperty(value = "当前用户编号")
    private String dqczybh;

    @Size(max = 64, message = "创建人名称最大长度不能超过32")
    private String cjrmc;
}
