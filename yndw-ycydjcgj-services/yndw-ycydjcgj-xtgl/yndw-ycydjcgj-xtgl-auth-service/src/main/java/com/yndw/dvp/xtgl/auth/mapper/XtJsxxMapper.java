package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxCountDto;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtJsxxMapper extends SuperMapper<XtJsxx> {
    int updateZtdm(XtJsxx entity);

    int updateSjbhs(XtJsxx entity);

    XtJsxxCountDto jsxxCount();

    int deleteCzyqxs(@Param(value = "jsbh") String jsbh);

    int deleteJsgngxs(@Param(value = "jsbh") String jsbh);

    int deleteGwjsgxs(@Param(value = "jsbh") String jsbh);

}
