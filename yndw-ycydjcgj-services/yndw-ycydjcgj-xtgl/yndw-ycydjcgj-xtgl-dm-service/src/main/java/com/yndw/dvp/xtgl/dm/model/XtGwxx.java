package com.yndw.dvp.xtgl.dm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * xt_gwxx
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_gwxx")
public class XtGwxx implements Serializable {

    private static final long serialVersionUID = 6048452659758748778L;
    /**
     * 组织编码
     */
    @ApiModelProperty(value="组织编码")
    private String zzbh;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    private String gwmc;

    /**
     * 岗位描述
     */
    @ApiModelProperty(value="岗位描述")
    private String gwms;

    /**
     * 岗位用途代码
     */
    @ApiModelProperty(value="岗位用途代码")
    private String gwytdm;

    /**
     * 岗位状态代码
     */
    @ApiModelProperty(value="岗位状态代码")
    private String gwztdm;

    /**
     * 状态变更时间
     */
    @ApiModelProperty(value="状态变更时间")
    private Date ztbgsj;
}