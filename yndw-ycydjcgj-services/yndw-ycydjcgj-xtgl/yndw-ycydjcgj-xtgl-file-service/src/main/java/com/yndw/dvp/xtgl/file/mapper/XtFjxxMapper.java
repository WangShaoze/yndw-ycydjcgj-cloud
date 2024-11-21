package com.yndw.dvp.xtgl.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.file.model.XtFjxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Mapper
public interface XtFjxxMapper extends SuperMapper<XtFjxx> {
    List<XtFjxx> queryByFjfz(String bh);
}
