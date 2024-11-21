package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("xt_zdygnz")
public class XtZdygnz extends SuperEntity<XtZdygnz> {
    private static final long serialVersionUID = 5314887776384515711L;
    private String gnzmc;
    private String czybh;
    private String gnzms;
    private String tbbh;
}


