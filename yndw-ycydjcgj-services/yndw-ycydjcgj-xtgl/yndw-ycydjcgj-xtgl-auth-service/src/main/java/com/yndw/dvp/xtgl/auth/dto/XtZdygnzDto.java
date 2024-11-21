package com.yndw.dvp.xtgl.auth.dto;

import com.yndw.dvp.xtgl.auth.model.XtZdygnz;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class XtZdygnzDto extends XtZdygnz implements Serializable {
    private String gnbh;
    private String tbbm;
    private String gnmc;
    private String tbbh;
}
