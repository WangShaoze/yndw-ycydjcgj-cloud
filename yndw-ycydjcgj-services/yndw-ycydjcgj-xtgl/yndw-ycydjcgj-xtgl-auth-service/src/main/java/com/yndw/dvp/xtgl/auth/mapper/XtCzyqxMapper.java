package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.CzyqxDto;
import com.yndw.dvp.xtgl.auth.dto.GnTopDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyqx;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtCzyqxMapper extends SuperMapper<XtCzyqx> {

    List<CzyqxDto> getTopYy6(@Param(value = "czybh") String czybh);

    List<String> getGnbhsByGwbh(@Param(value = "gwbh") String gwbh);

    List<String> getGnbhsByCzybh(@Param(value = "czybh") String czybh);

    List<String> getYybhsByCzybh(@Param(value = "czybh") String czybh);
    //     String getCzybh1(@Param(value = "jsbh")  String jsbh);
    List<String> getCzybh(@Param(value = "jsbh")  String jsbh);

    List<String> getGnbh(@Param(value = "gnmc")  String gnmc);

    List<String> getJsbh(@Param(value = "gwbh")  String gwbh);

    int deleteCzyGnxx(@Param("czybh") String czybh,@Param("type") String type);

    int deleteCzyqxByJsbhs(@Param("jsbhList") List<String> jsbhList);

    int deleteCzyqxGnxx(@Param("jsbh") String jsbh, @Param("gnbh") String gnbh);

    @Insert("insert into xt_czyqx(jsbh, gnbh,czybh) values(#{jsbh}, #{gnbh},#{czybh})")
    int saveCzyqxGnxx(@Param("jsbh") String jsbh, @Param("gnbh") String gnbh, @Param("czybh") String czybh);

    @Insert("insert into xt_czyqx(czybh, jsbh, gnbh, yybh) values(#{czybh}, '0', #{gnbh}, #{yybh})")
    int saveCzyGnxx(@Param("czybh") String czybh, @Param("gnbh") String gnbh, @Param("yybh") String yybh);

    int saveWhenSaveCzy(@Param("jsbh") String jsbh, @Param("czybh") String czybh, @Param("gnbh") String gnbh);

    List<GnTopDto> gnTopten();

    int gnadd(@Param("czybh") String czybh,@Param("jsbh") String jsbh, @Param("gnbh") String gnbh);

    List<XtGnxx> findGnxxByCzybh(@Param("czybh") String czybh, @Param("gnlxdm") String gnlxdm);

    List<XtCzyqx> descYylb(String czybh);

    int saveCzyqx(XtCzyqx param);
}
