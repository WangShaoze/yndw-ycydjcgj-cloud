package com.yndw.dvp.xtgl.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtCzyxxQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtCzyxxMapper extends SuperMapper<XtCzyxx> {
    int updateDlmm(XtCzyxx entity);

    int deleteCzyqxs(@Param(value = "czybh") String czybh);

    int updateZtdm(XtCzyxx entity);

    int updateAvatar(XtCzyxx entity);

    SecurityUser getByBh(@Param(value = "bh") String bh);

    SecurityUser getByDlzh(@Param(value = "dlzh") String dlzh);

    List<XtCzyxx> queryList4Xm(@Param(value = "bhArry") String[] bhArry);

    @Update("update xt_czyxx set dlmm = #{ddmm}")
    int initPassword(String ddmm);

    Page<XtCzyxxDto> queryPage(Page<XtCzyxxDto> page, @Param("queryParam") XtCzyxxQueryParam queryParam);

    XtCzyxxDto getMoreById(@Param(value = "bh") String bh);

    @Update("update xt_czyxx set dlmmycwcs = (dlmmycwcs+1) where bh = #{bh}")
    int updateDlmmycwcs(@Param(value = "bh") String bh);

    @Update("update xt_czyxx set dlmmycwcs = (dlmmycwcs+1),dlmmdyccwsj=now() where bh = #{bh}")
    int updateDlmmycwcsAndSj(@Param(value = "bh") String bh);

    @Update("update xt_czyxx set dlmmycwcs = 0,dlmmdyccwsj = NULL where bh = #{bh}")
    int updateDlmmycwcsToZero(@Param(value = "bh") String bh);

    XtCzyxxCountDto czyxxCount();

    List<XtGnxx> findGnxxByCzyxxs(@Param("czybhs") Set<String> czybhs, @Param("gnlxdm") String gnlxdm);

    int deleteCzyqxsByZzbh(@Param(value = "zzbh") String zzbh);

    int deleteByZzbh(@Param(value = "zzbh") String zzbh);

    List<XtCzyxx> getCzyxxByGwbh(@Param(value = "gwbh") String gwbh);
}
