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
public class XtDmflmxQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = 5749723678038199268L;
    @ApiModelProperty(name = "dmflbm", value = "代码分类编码")
    private String dmflbm;
    @ApiModelProperty(name = "dmmc", value = "代码名称")
    private String dmmc;
    @ApiModelProperty(name = "sjbh", value = "上级编号")
    private String sjbh;
}
