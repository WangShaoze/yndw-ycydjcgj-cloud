package com.yndw.dvp.xtgl.auth.dto;

import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtZdygnz;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class XtGnzxxDto extends XtZdygnz implements Serializable {

       @ApiModelProperty(value = "功能信息children")
       private List<XtGnxx> children;
}
