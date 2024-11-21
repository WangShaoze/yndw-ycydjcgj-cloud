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
public class XtCzyxxCountDto implements Serializable {
    private static final long serialVersionUID = 8212964528353779258L;
    private int totalCount;
    private int disableCount;
    private int downCount;
}
