package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * 角色功能关系
 * Create By Carlos
 * 2020/6/13
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("xt_jsgngx")
public class XtJsgngx extends Model<XtJsgngx> {
    private static final long serialVersionUID = 7146261525813015680L;
    private String jsbh;
    private String gnbh;
}
