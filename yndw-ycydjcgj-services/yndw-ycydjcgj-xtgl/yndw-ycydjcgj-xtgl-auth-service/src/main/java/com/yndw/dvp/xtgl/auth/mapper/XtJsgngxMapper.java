package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtJsgngx;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Create By Carlos
 * 2020/6/13
 */
@Mapper
public interface XtJsgngxMapper extends SuperMapper<XtJsgngx> {
    @Insert("insert into xt_jsgngx(jsbh, gnbh) values(#{jsbh}, #{gnbh})")
    int saveJsxxGnxx(@Param("jsbh") String jsbh, @Param("gnbh") String gnbh);

    int deleteJsxxGnxx(@Param("jsbh") String jsbh, @Param("gnbh") String gnbh);

    List<String> findGnbhsByJsbh(@Param("jsbh") String jsbh);

    List<XtJsgngx> getJsgngxByJsbh(@Param("jsbhList") List<String> jsbhList);

    List<XtGnxx> findGnxxByJsxxs(@Param("jsbhs") Set<String> jsbhs, @Param("czybh") String czybh, @Param("gnlxdm") String gnlxdm);

    List<XtGnxx> findOwnGnxx(@Param("jsbhs") Set<String> jsbhs, @Param("czybh") String czybh, @Param("gnlxdm") String gnlxdm);

    List<XtGnxx> findAllMenu(@Param("gnlxdm") String gnlxdm);

    List<String> yyxxBygnbh(@Param("gnbh") String gnbh);

    List<XtGnxx> getGnxxByJsbh(@Param("jsbh") String jsbh);
}
