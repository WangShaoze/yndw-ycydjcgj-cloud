package com.yndw.dvp.xtgl.auth.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yndw.dvp.common.core.constant.CommonConstant;
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
public class XtCzyxxExcelDto implements Serializable {

    private static final long serialVersionUID = 4545203398364320294L;
    private String zzbh;
    @Excel(name = "组织名称", height = 20, width = 30, isImportField = "true_st")
    private String zzmc;
    private String gwbh;
    @Excel(name = "岗位名称", height = 20, width = 30, isImportField = "true_st")
    private String gwmc;
    @Excel(name = "登录账号", height = 20, width = 30, isImportField = "true_st")
    private String dlzh;
    @Excel(name = "工号", height = 20, width = 30, isImportField = "true_st")
    private String czygh;
    @Excel(name = "姓名", height = 20, width = 30, isImportField = "true_st")
    private String czymc;
    private String bgdhhm;
    private String sjhm;
    @Excel(name = "性别", replace = {"男_1", "女_2"}, isImportField = "true_st")
    private String xbdm;
    private Date csrq;
    private String zc;
    private String zw;
    private String dw;
    private Integer dlmmycwcs;
    private Integer dlmmzdcwcs;
    private Integer wzkf;
    private String czyztdm;
    private String czyztdmStr;
    private Date ztbgsj;
    private String email;
    private String avatar;
    @Excel(name = "创建时间", format = CommonConstant.DATETIME_FORMAT, isImportField = "true_st", width = 20)
    private Date cjsj;
}
