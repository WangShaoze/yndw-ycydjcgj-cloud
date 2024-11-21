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
public class XtYyxxCountDto implements Serializable {
    private static final long serialVersionUID = -5900272647079818309L;
    private int totalCount;
    private int upCount;
    private int downCount;
}
