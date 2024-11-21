package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtZzxxMapper extends SuperMapper<XtZzxx> {
    int updateZtdm(XtZzxx entity);

    int updateSjbhs(XtZzxx entity);

    XtZzxxCountDto zzxxCount();
}
