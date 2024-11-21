package com.yndw.dvp.common.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 */
@Setter
@Getter
public class SuperTreeEntity<T extends SuperEntity<?>> extends SuperEntity<T> {
    @ApiModelProperty(value = "上级编号", hidden = true)
    private String sjbh;
}
