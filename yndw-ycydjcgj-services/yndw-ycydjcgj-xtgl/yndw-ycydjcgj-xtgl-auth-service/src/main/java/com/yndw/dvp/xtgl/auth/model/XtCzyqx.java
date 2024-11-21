package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("xt_czyqx")
public class XtCzyqx extends Model<XtCzyqx> {
    private static final long serialVersionUID = 5314887776384515711L;
    private String czybh;
    private String jsbh;
    private String gnbh;
    private int usecount;//使用数
    private String yybh;
}


