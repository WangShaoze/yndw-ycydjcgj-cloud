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
public class XtJsxxCountDto implements Serializable {
    private static final long serialVersionUID = 1448943445674113194L;
    private int totalCount;
    private int disableCount;
    private int downCount;
}
