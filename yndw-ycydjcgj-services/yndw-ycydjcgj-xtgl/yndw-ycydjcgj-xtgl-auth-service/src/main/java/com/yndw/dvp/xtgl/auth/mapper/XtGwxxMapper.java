package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtGwxxMapper extends SuperMapper<XtGwxx> {
    int updateZtdm(XtGwxx entity);

    XtGwxxCountDto gwxxCount();

    int deleteGwjsgxs(@Param(value = "gwbh") String gwbh);

    int deleteGwjsgxsByZzbh(@Param(value = "zzbh") String zzbh);

    int deleteByZzbh(@Param(value = "zzbh") String zzbh);
}
