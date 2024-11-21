package com.yndw.dvp.common.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * 
 */
@Setter
@Getter
public class SuperEntity<T extends Model<?>> extends Model<T> {
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "编号", hidden = true)
   // @NotNull(message = "主键不能为空")
    private String bh;

    @ApiModelProperty(value = "创建人编号", hidden = true)
    @TableField(value = "cjrbh")
    private String cjrbh;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date cjsj;

    @ApiModelProperty(value = "操作人编号", hidden = true)
    @TableField(value = "czrbh")
    private String czrbh;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "操作时间", hidden = true)
    private Date czsj;

    @Override
    protected Serializable pkVal() {
        return this.bh;
    }

}
