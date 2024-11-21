package com.yndw.dvp.oauth.queryParam;

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
public class OAuthLoginLogQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = -1372109983938679106L;
    @ApiModelProperty(name = "searchword", value = "过滤字段")
    private String searchword;
    @ApiModelProperty(name = "dlsj", value = "登录时间")
    private String dlsj;
}
