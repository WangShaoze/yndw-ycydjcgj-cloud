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
public class XtGnxxCountDto implements Serializable {
    private static final long serialVersionUID = -6671550817746051628L;
    private int totalCount;
    private int upCount;
    private int downCount;
}
