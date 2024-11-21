package com.yndw.dvp.common.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityMenu implements Serializable {
    private static final long serialVersionUID = -3239216472966652490L;
    private String bh;
    private String sjbh;  //上级功能编号
    private String gnmc;
    private String gnljdz;  //上级功能编号
    private String gnqqff;  // gnqqff
    private String gnqxbz;  // 功能类型代码
}
