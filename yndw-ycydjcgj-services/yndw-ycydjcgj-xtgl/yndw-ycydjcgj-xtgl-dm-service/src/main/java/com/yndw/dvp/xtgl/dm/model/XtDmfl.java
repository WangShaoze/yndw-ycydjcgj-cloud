package com.yndw.dvp.xtgl.dm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
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
@TableName("xt_dmfl")
public class XtDmfl extends SuperEntity<XtDmfl> {
    private static final long serialVersionUID = 3536856523231038140L;
    @ApiModelProperty(value = "代码类别")
    @Size(max = 8, message = "代码类别最大长度不能超过8")
    private String dmlb;
    @ApiModelProperty(value = "代码分类编码")
    @NotNull(message = "代码分类编码不能为空")
    @Size(max = 32, message = "代码分类编码最大长度不能超过32")
    private String dmflbm;
    @ApiModelProperty(value = "代码分类名称")
    @NotNull(message = "代码分类名称不能为空")
    @Size(max = 64, message = "代码分类编码最大长度不能超过64")
    private String dmflmc;
    @ApiModelProperty(value = "状态代码（正常、停用等）")
    @NotNull(message = "状态代码不能为空")
    private String ztdm;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;

}
