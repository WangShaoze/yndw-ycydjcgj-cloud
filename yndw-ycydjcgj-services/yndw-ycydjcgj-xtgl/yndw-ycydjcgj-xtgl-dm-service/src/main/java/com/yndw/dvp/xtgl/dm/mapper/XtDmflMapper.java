package com.yndw.dvp.xtgl.dm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Mapper
public interface XtDmflMapper extends BaseMapper<XtDmfl> {
    int updateZtdm(XtDmfl entity);
}
