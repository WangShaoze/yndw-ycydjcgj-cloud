package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserQueryLikeParam extends SuperQueryParam {
    private static final long serialVersionUID = 33282389222306207L;

    @ApiModelProperty(name = "user_code_part", value = "用户编号")
    private String user_code_part;

    @ApiModelProperty(name = "user_name_part", value = "用户名称")
    private String user_name_part;
}
