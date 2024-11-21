package com.yndw.dvp.xtgl.log.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class XtLogQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = -2477737842744679640L;
    @ApiModelProperty(name = "keyword", value = "过滤字段")
    private String keyword;
    @ApiModelProperty(name = "timestamp", value = "业务日志时间戳")
    private String timestamp;
}