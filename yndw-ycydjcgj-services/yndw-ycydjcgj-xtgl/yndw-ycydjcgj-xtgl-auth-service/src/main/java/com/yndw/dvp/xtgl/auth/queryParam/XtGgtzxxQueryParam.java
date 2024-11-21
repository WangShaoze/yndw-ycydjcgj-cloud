package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class XtGgtzxxQueryParam extends SuperQueryParam {

    private static final long serialVersionUID = 3892366853556359609L;
    @ApiModelProperty(name = "ggbt", value = "公告标题")
    private String ggbt;

    @ApiModelProperty(name = "czsj", value = "操作时间")
    private String czsj;

    @ApiModelProperty(name = "jjdj", value = "公告等级")
    private String jjdj;

    @ApiModelProperty(name = "ggzt", value = "已读未读状态")
    private String ggzt;

    @ApiModelProperty(name = "searchword", value = "过滤字段")
    private String searchword;
}
