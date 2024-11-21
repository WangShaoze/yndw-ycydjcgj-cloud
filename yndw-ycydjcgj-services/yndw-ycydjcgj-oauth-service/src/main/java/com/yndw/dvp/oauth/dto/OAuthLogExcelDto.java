package com.yndw.dvp.oauth.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class OAuthLogExcelDto implements Serializable {
    private static final long serialVersionUID = 5216979582564367403L;

    private String czybh;
    @Excel(name = "操作员账号", height = 20, width = 30, isImportField = "true_st")
    private String czydlzh;
    //@Excel(name = "操作员名称", height = 20, width = 30, isImportField = "true_st")
    private String czymc;
    @Excel(name = "登录类型", height = 20, width = 30, isImportField = "true_st")
    private String dllx;
    @Excel(name = "ip", height = 20, width = 30, isImportField = "true_st")
    private String ipdz;
    @Excel(name = "操作时间", height = 20, width = 30, isImportField = "true_st")
    private LocalDateTime dlsj;
}
