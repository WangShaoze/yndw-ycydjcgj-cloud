package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 应用信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xt_yyxx")
public class XtYyxx extends SuperEntity<XtYyxx> {
    private static final long serialVersionUID = -2561096658304534257L;
    @ApiModelProperty(value = "应用编号")
    @TableId(type = IdType.ASSIGN_UUID)
    @Size(max = 64, message = "应用编号最大长度不能超过64")
    private String bh;
    @ApiModelProperty(value = "应用编码")
    @Size(max = 64, message = "应用编码最大长度不能超过64")
    private String yybm;
    @ApiModelProperty(value = "应用名称")
    @Size(max = 256, message = "应用名称最大长度不能超过256")
    private String yymc;
    @ApiModelProperty(value = "应用简称")
    @Size(max = 256, message = "应用简称最大长度不能超过256")
    private String yyjc;
    @ApiModelProperty(value = "英文简称")
    @Size(max = 256, message = "英文简称最大长度不能超过256")
    private String ywjc;
    @ApiModelProperty(value = "应用描述")
    @Size(max = 1024, message = "应用描述最大长度不能超过1024")
    private String yyms;
    @ApiModelProperty(value = "应用建设方")
    @Size(max = 256, message = "应用建设方最大长度不能超过256")
    private String yyjsf;
    @ApiModelProperty(value = "建设方负责人")
    @Size(max = 64, message = "建设方负责人最大长度不能超过64")
    private String jsffzr;
    @ApiModelProperty(value = "应用承建方")
    @Size(max = 256, message = "应用承建方最大长度不能超过256")
    private String yycjf;
    @ApiModelProperty(value = "承建方负责人")
    @Size(max = 64, message = "承建方负责人最大长度不能超过64")
    private String cjffzr;
    @ApiModelProperty(value = "服务器IP")
    @Size(max = 64, message = "服务器IP最大长度不能超过64")
    private String fwqip;
    @ApiModelProperty(value = "应用端口")
    @Size(max = 8, message = "应用端口最大长度不能超过8")
    private String yydk;
    @ApiModelProperty(value = "应用状态（1：启用；2：停用；）")
    @Size(max = 8, message = "应用状态最大长度不能超过8")
    private String yyztdm;
    @ApiModelProperty(value = "协议类型（1：http；2：https）")
    @Size(max = 8, message = "协议类型最大长度不能超过8")
    private String xylx;
    @ApiModelProperty(value = "应用类别")
    @Size(max = 64, message = "应用类别最大长度不能超过64")
    private String yylb;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;
    @ApiModelProperty(value = "应用图标")
    @Size(max = 1024, message = "应用图标最大长度不能超过1024")
    private String yytb;
    @ApiModelProperty(value = "状态变更时间")
    private Date ztbgsj;
//    @ApiModelProperty(value = "应用首页")
//    private String yysy;

}
