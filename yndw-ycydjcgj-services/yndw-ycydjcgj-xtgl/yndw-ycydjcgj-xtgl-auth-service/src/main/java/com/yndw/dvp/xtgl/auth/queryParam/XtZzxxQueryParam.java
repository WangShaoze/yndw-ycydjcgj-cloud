package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Getter
@Setter
@ApiModel
public class XtZzxxQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = 1358263097570639067L;
    @ApiModelProperty(name = "sjbh", value = "上级组织编号")
    private String sjbh;
}

