package com.yndw.dvp.xtgl.auth.queryParam;

import com.yndw.dvp.common.core.model.SuperQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/6/12
 *
 * @author tuobang
 */
@Getter
@Setter
@ApiModel
public class XtJsxxQueryParam extends SuperQueryParam {

    private static final long serialVersionUID = 8898006457777648838L;
    @ApiModelProperty(name = "sjbh", value = "上级角色编号")
    private String sjbh;
    @ApiModelProperty(name = "ssyybh", value = "所属应用编号")
    private String ssyybh;

    public XtJsxxQueryParam(String sjbh, String ssyybh) {
        this.sjbh = sjbh;
        this.ssyybh = ssyybh;
    }

    public XtJsxxQueryParam() {
    }
}

