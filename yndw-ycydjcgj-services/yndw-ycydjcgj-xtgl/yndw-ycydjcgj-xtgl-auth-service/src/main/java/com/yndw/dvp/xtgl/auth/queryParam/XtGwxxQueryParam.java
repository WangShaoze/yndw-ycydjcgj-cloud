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
public class XtGwxxQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = 6272277681631306207L;
    @ApiModelProperty(name = "zzbh", value = "组织编号")
    private String zzbh;

    @ApiModelProperty(name = "gwmc", value = "岗位名称")
    private String gwmc;

    /**
     * type = 1 ,查询本级组织下岗位
     * type = 2 ,查询本级组织及本级组织下所有组织下岗位
     */
    @ApiModelProperty(name = "type", value = "查询类型")
    private String type = "1";
}
