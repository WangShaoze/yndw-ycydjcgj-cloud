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
public class XtZzxxCountDto implements Serializable {
    private static final long serialVersionUID = -948266348115868727L;
    private int totalCount;
    private int upCount;
    private int downCount;
}
