package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Data
@ApiModel
public class XtYyFlCountDto implements Serializable {
    private static final long serialVersionUID = 4786297060543805688L;
    private String yylb;
    private int yyCount;
}
