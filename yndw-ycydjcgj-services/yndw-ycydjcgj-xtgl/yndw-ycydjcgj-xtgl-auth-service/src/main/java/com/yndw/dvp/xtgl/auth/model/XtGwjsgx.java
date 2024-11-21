package com.yndw.dvp.xtgl.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * 岗位角色关系
 * Create By Carlos
 * 2020/6/13
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("xt_gwjsgx")
public class XtGwjsgx extends Model<XtGwjsgx> {
    private static final long serialVersionUID = -245114349568398557L;
    private String gwbh;
    private String jsbh;
}
