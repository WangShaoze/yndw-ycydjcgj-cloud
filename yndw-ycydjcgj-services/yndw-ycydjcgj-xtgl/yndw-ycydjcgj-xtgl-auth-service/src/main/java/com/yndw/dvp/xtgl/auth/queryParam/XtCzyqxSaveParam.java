package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Getter
@Setter
@ApiModel
public class XtCzyqxSaveParam{
    private static final long serialVersionUID = 1416962056918687084L;
    @ApiModelProperty(name = "czybh", value = "操作员编号")
    private String czybh;

    @ApiModelProperty(name = "gnxxList", value = "功能列表")
    private List<YyUITreeDto> gnxxList;
}

