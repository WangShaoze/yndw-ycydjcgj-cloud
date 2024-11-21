package com.yndw.dvp.xtgl.dm.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Getter
@Setter
@ApiModel
public class XtDmflmxDto implements Serializable {
    private static final long serialVersionUID = -7426289755677145293L;
    private String bh;
    private String sjbh;
    private String dmflbm;//代码分类编号
    private String dmbm;
    private String dmmc;
    private Integer xssxh;
    private String ztdm;
    private String ztdmStr;
}
