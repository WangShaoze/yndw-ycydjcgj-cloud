package com.yndw.dvp.xtgl.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.model.XtGgtzxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGgtzxxQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By Liangkh
 * 2021/4/23
 */

@Mapper
public interface XtGgtzxxMapper extends SuperMapper<XtGgtzxx> {
    int updateAllCurrentUser(@Param("bh") String bh);

    int insertRelation(@Param("ggbh") String ggbh, @Param("ids") List<String> ids);

    Page<XtGgtzxx> queryPage(Page<XtGgtzxx> page, @Param("startData") String startData, @Param("endData") String endData, @Param("queryParam") XtGgtzxxQueryParam queryParam, @Param("bh") String bh);

    int read(@Param("ggbh") String ggbh, @Param("id") String id);

}
