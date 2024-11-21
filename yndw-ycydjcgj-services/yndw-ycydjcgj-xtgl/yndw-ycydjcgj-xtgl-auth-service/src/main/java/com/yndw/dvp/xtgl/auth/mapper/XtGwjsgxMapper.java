package com.yndw.dvp.xtgl.auth.mapper;


import java.util.List;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.model.XtGwjsgx;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Create By Carlos
 * 2020/6/13
 */
@Mapper
public interface XtGwjsgxMapper extends SuperMapper<XtGwjsgx> {
    int deleteGwxxJsxx(@Param("gwbh") String gwbh, @Param("jsbh") String jsbh);

    @Insert("insert into xt_gwjsgx(gwbh, jsbh) values(#{gwbh}, #{jsbh})")
    int saveGwxxJsxx(@Param("gwbh") String gwbh, @Param("jsbh") String jsbh);

    List<String> findJsbhsByGwbh(@Param("gwbh") String gwbh);

    List<String> findYybhsByGwbh(@Param("gwbh") String gwbh);

    @Select("<script>select j.*,gj.gwbh from xt_gwjsgx gj inner join xt_jsxx j on j.jsbh = gj.jsbh where gj.gwbh IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<XtJsxx> findJsxxsByGwbhs(List<String> gwbhs);
}