package com.yndw.dvp.xtgl.log.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysLogExcelDto implements Serializable {

    private static final long serialVersionUID = 406231998617903364L;
    @Excel(name = "服务名称", height = 20, width = 30, isImportField = "true_st")
    private String applicationName;
    @Excel(name = "类名", height = 20, width = 30, isImportField = "true_st")
    private String className;
    @Excel(name = "方法", height = 20, width = 30, isImportField = "true_st")
    private String methodName;
    private String userId;
    @Excel(name = "操作员", height = 20, width = 30, isImportField = "true_st")
    private String userName;
    private String clientId;
    @Excel(name = "操作说明", height = 20, width = 30, isImportField = "true_st")
    private String operation;
    @Excel(name = "操作时间", height = 20, width = 30, isImportField = "true_st")
    private String timestamp;
}
