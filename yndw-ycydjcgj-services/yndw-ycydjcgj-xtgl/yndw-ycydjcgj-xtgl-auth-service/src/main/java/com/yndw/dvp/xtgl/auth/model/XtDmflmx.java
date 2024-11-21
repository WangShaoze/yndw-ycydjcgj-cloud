package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperTreeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_dmflmx")
public class XtDmflmx extends SuperTreeEntity<XtDmflmx> {
    private static final long serialVersionUID = 3536856523231038140L;
    @ApiModelProperty(value = "代码分类编码")
    private String dmflbm;
    @ApiModelProperty(value = "代码编码")
    @NotNull(message = "代码编码不能为空")
    @Size(max = 32, message = "代码编码最大长度不能超过32")
    private String dmbm;
    @ApiModelProperty(value = "代码名称")
    @NotNull(message = "代码名称不能为空")
    @Size(max = 64, message = "代码编码最大长度不能超过64")
    private String dmmc;
    @ApiModelProperty(value = "显示顺序号")
    private Integer xssxh;
    @ApiModelProperty(value = "状态代码（正常、停用等）")
    private String ztdm;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
}
