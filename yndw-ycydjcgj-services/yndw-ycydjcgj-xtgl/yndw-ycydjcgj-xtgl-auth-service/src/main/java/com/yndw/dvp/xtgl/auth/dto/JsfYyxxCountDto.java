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
public class JsfYyxxCountDto implements Serializable {
    private static final long serialVersionUID = 4786297060543805688L;
    private String zzmc;
    private int start;
    private int stop;
    private int yyCount;
}
