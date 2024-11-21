package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YyUITreeDto {
    @ApiModelProperty(value = "节点唯一索引")
    private String id;

    @ApiModelProperty(value = "父节点ID")
    private String sjid;

    @ApiModelProperty(value = "所属应用ID")
    private String ssyyid;

    @ApiModelProperty(value = "节点标题")
    private String title;

    @ApiModelProperty(value = "节点字段名")
    private String field;

    @ApiModelProperty(value = "节点对应url")
    private String herf = "";

    @ApiModelProperty(value = "子节点")
    private List<YyUITreeDto> children;

    private boolean checked = false;

    private boolean spread = true;

    private boolean disabled = false;
}
