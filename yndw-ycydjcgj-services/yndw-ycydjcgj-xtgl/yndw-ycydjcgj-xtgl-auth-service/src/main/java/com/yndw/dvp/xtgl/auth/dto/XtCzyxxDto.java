package com.yndw.dvp.xtgl.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/6/15
 */
@Getter
@Setter
public class XtCzyxxDto implements Serializable {
    private static final long serialVersionUID = 8981681064474565995L;
    @ApiModelProperty(value = "操作员编号")
    private String bh;
    @ApiModelProperty(value = "组织编号")
    private String zzbh;
    private String zzmc;
    @ApiModelProperty(value = "岗位编号")
    private String gwbh;
    private String gwmc;
    @ApiModelProperty(value = "登录账号")
    private String dlzh;
    @ApiModelProperty(value = "操作员工号")
    private String czygh;
    @ApiModelProperty(value = "操作员名称")
    private String czymc;
    @ApiModelProperty(value = "办公电话号码")
    private String bgdhhm;
    @ApiModelProperty(value = "手机号码")
    private String sjhm;
    @ApiModelProperty(value = "性别代码")
    private String xbdm;
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    @ApiModelProperty(value = "职称")
    private String zc;
    @ApiModelProperty(value = "职务")
    private String zw;
    @ApiModelProperty(value = "单位")
    private String dw;
    @ApiModelProperty(value = "登录密码已错误次数")
    private Integer dlmmycwcs;
    @ApiModelProperty(value = "登录密码最大错误次数")
    private Integer dlmmzdcwcs;
    @ApiModelProperty(value = "违章扣分")
    private Integer wzkf;
    @ApiModelProperty(value = "操作员状态代码正常、停用、锁定")
    private String czyztdm;
    private String czyztdmStr;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
    @ApiModelProperty(value = "4A邮箱")
    private String email;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
