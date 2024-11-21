package com.yndw.dvp.xtgl.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Create By Carlos
 * 2020/7/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_logger")
public class XtLog extends Model<XtLog> {
    private static final long serialVersionUID = -5308961849681925651L;
    private String id;
    private String applicationName;
    private String className;
    private String methodName;
    private String userId;
    private String userName;
    private String clientId;
    private String operation;
    private String timestamp;
}
