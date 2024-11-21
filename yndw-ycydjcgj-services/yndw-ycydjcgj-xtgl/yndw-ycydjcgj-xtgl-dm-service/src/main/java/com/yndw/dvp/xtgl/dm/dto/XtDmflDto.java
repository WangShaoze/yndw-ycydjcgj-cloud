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
public class XtDmflDto implements Serializable {
    private static final long serialVersionUID = 714155282069790706L;

    private String bh;
    private String dmflbm;
    private String dmflmc;
    private String ztdm;
    private String ztdmStr;
}
