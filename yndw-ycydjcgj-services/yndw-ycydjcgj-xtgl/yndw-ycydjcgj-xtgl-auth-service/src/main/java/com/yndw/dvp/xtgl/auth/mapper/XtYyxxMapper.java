package com.yndw.dvp.xtgl.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@Mapper
public interface XtYyxxMapper extends SuperMapper<XtYyxx> {
    int updateYyztdm(XtYyxx entity);

    int updateAvatar(XtYyxx entity);

    Page<XtYyxx> queryPage(Page<XtYyxx> page);

    Page<XtYyxx> WsqQueryPage(Page<XtYyxx> page);

    XtYyxxDto getMoreById(@Param(value = "bh") String bh);

    List<XtYyxx> getYyxxNum(@Param("xtDm") List<String> xtDm);

    XtYyxxCountDto yyxxCount();

    List<XtYyFlCountDto> yyFlCount(@Param(value = "yyztdm") String  yyztdm);

    List<JsfYyxxCountDto> yyJsfyyCount();

    List<CjfYyxxCountDto> yyCjfyyCount();

    List<XtYyxx> findAuthYyxxList(@Param("czybh") String czybh);
}
