package com.yndw.dvp.xtgl.dm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Mapper
public interface XtDmflmxMapper extends BaseMapper<XtDmflmx> {
    int updateZtdm(XtDmflmx entity);

    List<XtDmflmx> findMenuBySjbh(@Param(value = "sjbh") String sjbh);
}
