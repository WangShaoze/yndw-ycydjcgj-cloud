package com.yndw.dvp.common.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Setter
@Getter
public class SuperQueryParam implements Serializable {
    @ApiModelProperty(name="page",value = "分页查询页码")
    private Integer page = 1;
    @ApiModelProperty(name="limit",value = "分页查询数据条数")
    private Integer limit = 10;
    @ApiModelProperty(name="pageSize",value = "默认排序规则")
    private String sort = "cjsj";
}
