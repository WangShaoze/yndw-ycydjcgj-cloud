package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_tbk")
public class Xttbk extends SuperEntity<Xttbk> {
    private static final long serialVersionUID = -1680920960985487638L;
    @ApiModelProperty(value = "图标编码")
    @Size(max = 1024, message = "应用图标最大长度不能超过1024")
    private String tbbm;

    @ApiModelProperty(value = "图标描述")
    private String tbms;
}
