package com.yndw.dvp.common.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityUser implements Serializable {
    private static final long serialVersionUID = -4839503021042964173L;
    private String bh;
    private String zzbh;
    private String gwbh;
    private String dlzh;
    private String dlmm;
    private String czyztdm;
    private Integer dlmmycwcs;
    private Date dlmmdyccwsj;
    private Date ztbgsj;

    private List<SecurityRole> securityRoleList;
}
