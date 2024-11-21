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
public class XtGwxxCountDto implements Serializable {
    private static final long serialVersionUID = 8089679825241227835L;
    private int totalCount;
    private int upCount;
    private int downCount;
}
