package com.yndw.dvp.xtgl.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.SearchDto;
import com.yndw.dvp.xtgl.auth.dto.XtGnxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtGnxxDto;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtGnxxMapper extends SuperMapper<XtGnxx> {
    int updateZtdm(XtGnxx entity);

    int updateSjbhs(XtGnxx entity);

    int deleteCzyqxs(@Param(value = "gnbh") String gnbh);

    int deleteJsgngxs(@Param(value = "gnbh") String gnbh);

    XtGnxxCountDto gnxxCount();

   List<XtGnxx>  sqgnxxByYybh(String yybh, String czybh);

    List<XtGnxx> wsqgnxxByYybh(String yybh,String czybh);
	// 搜索功能
    Page<SearchDto> searchGn(Page<SearchDto> page,@Param("wordKey") String wordKey);

    Page<XtGnxx> myGnxx(Page<XtGnxxDto> page, @Param("czybh") String czybh);
}
