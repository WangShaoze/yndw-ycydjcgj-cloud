package com.yndw.dvp.xtgl.file.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_fjxx")
public class XtFjxx extends SuperEntity<XtFjxx> {
    private static final long serialVersionUID = -5196695029396926967L;
    @ApiModelProperty(value = "附件名称")
    private String fjmc;
    @ApiModelProperty(value = "附件路径")
    private String fjlj;
    @ApiModelProperty(value = "附件大小")
    private long fjdx;
    @ApiModelProperty(value = "附件存储路径")
    private String fjcclj;
    @ApiModelProperty(value = "附件后缀")
    private String fjhz;
    @ApiModelProperty(value = "附件分组")
    private String fjfz;

}
