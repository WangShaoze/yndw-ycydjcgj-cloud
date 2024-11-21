package com.yndw.dvp.xtgl.dm.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Getter
@Setter
@ApiModel
public class XtDmflQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = 5749723678038199268L;
    @ApiModelProperty(name = "dmflmc", value = "代码分类名称")
    private String dmflmc;
}
