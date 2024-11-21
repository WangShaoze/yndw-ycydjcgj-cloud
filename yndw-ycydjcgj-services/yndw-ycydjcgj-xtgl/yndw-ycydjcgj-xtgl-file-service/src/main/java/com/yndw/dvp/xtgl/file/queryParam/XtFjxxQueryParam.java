package com.yndw.dvp.xtgl.file.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/7/7
 */
@Getter
@Setter
@ApiModel
public class XtFjxxQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = -6012082295347567610L;
    @ApiModelProperty(name="fjmc",value = "附件名称")
    private String fjmc;

    @ApiModelProperty(name="fjfz",value = "附件分组")
    private String fjfz;
}
