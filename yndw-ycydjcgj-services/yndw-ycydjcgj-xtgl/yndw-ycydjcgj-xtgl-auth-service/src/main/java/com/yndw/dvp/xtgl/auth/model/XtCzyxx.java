package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 操作员
 * Create By Carlos
 * 2020/6/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_czyxx")
public class XtCzyxx extends SuperEntity<XtCzyxx> {

    private static final long serialVersionUID = -4695294321494197545L;
    @ApiModelProperty(value = "组织编号")
    @Size(max = 64, message = "组织编号最大长度不能超过64")
    private String zzbh;
    @ApiModelProperty(value = "组织名称")
    @TableField(exist = false)
    private String zzmc;
    @ApiModelProperty(value = "岗位编号")
    @NotEmpty(message = "岗位不能为空")
    @Size(max = 64, message = "岗位编号最大长度不能超过64")
    private String gwbh;
    @ApiModelProperty(value = "岗位名称")
    @TableField(exist = false)
    private String gwmc;
    @ApiModelProperty(value = "登录账号")
    @NotEmpty(message = "登录账号不能为空")
    @Size(min = 6, max = 15, message = "登录账号必须是6-15个字符")
    private String dlzh;
    @ApiModelProperty(value = "登录密码")
    private String dlmm;
    @TableField(exist = false)
    @ApiModelProperty(value = "旧登录密码")
    private String oldDlmm;
    @TableField(exist = false)
    @ApiModelProperty(value = "新登录密码")
    private String newDlmm;
    @ApiModelProperty(value = "操作员工号")
    @Size(max = 64, message = "操作员工号最大长度不能超过64")
    private String czygh;
    @ApiModelProperty(value = "操作员名称")
    @Size(max = 20, message = "操作员名称最大长度不能超过20")
    private String czymc;
    @ApiModelProperty(value = "办公电话号码")
    @Size(max = 20, message = "办公电话号码最大长度不能超过20")
    private String bgdhhm;
    @ApiModelProperty(value = "手机号码")
    @Size(max = 20, message = "手机号码最大长度不能超过20")
    private String sjhm;
    @ApiModelProperty(value = "性别代码")
    @Size(max = 8, message = "性别代码最大长度不能超过8")
    private String xbdm;
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    @ApiModelProperty(value = "职称")
    @Size(max = 50, message = "职称最大长度不能超过50")
    private String zc;
    @ApiModelProperty(value = "职务")
    @Size(max = 50, message = "职务最大长度不能超过50")
    private String zw;
    @ApiModelProperty(value = "单位")
    @Size(max = 50, message = "单位最大长度不能超过50")
    private String dw;
    @ApiModelProperty(value = "登录密码已错误次数")
    private Integer dlmmycwcs;
    @ApiModelProperty(value = "登录密码最大错误次数")
    private Integer dlmmzdcwcs;
    @ApiModelProperty(value = "违章扣分")
    private Integer wzkf;
    @ApiModelProperty(value = "操作员状态代码正常、停用、锁定")
    private String czyztdm;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
    @ApiModelProperty(value = "4A邮箱")
    @Size(max = 50, message = "4A邮箱最大长度不能超过50")
    private String email;
    @ApiModelProperty(value = "用户头像")
    private String avatar;


}
