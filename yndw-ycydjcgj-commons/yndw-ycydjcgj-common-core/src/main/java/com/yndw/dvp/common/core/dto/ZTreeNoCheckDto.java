package com.yndw.dvp.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create By Carlos
 * 2020/7/1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZTreeNoCheckDto {
    @ApiModelProperty(value = "编号")
    private String bh;

    @ApiModelProperty(value = "上级编号")
    private String sjbh;

    @ApiModelProperty(value = "名称")
    private String mc;

    private boolean checked = false;

    private boolean open = true;

    private boolean nocheck = false;
}
