package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class GnTopDto extends XtGnxxDto implements Serializable{
    private static final long serialVersionUID = 4786297060543805688L;
    private String gnmc;
    private int usecount;
    private String yymc;
    private String tbbh;
    private String tbbm;
}
