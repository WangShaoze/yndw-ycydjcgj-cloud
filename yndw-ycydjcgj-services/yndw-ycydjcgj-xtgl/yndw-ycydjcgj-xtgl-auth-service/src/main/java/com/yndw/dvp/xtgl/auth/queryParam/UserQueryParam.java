package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserQueryParam extends SuperQueryParam {
    private static final long serialVersionUID = 33282389222306207L;

    @ApiModelProperty(name = "zzbh", value = "组织编号")
    private String zzbh;

    @ApiModelProperty(name = "zzmc", value = "组织名称")
    private String zzmc;

    @ApiModelProperty(name = "suo_id", value = "所属供电所ID")
    private String suo_id;

    @ApiModelProperty(name = "suo", value = "所属供电所")
    private String suo;

    @ApiModelProperty(name = "ju_id", value = "所属供电局ID")
    private String ju_id;

    @ApiModelProperty(name = "ju", value = "所属供电局")
    private String ju;

    @ApiModelProperty(name = "sjbhs", value = "上级角色编号")
    private String sjbhs;
}
