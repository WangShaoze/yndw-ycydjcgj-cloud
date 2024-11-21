package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.*;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtGgtzxxDto;
import com.yndw.dvp.xtgl.auth.model.XtGgtzxx;

import java.util.*;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtGgtzxxFactory {
    public static XtGgtzxxDto build(XtGgtzxx entity) {
        XtGgtzxxDto dto = new XtGgtzxxDto();
        BeanUtil.copyProperties(entity, dto);

        dto.setJjdjStr(getJjdjzMsg(dto.getJjdj()));
        dto.setGgztStr(getGgztMsg(dto.getGgzt()));
        dto.setFbztStr(getFbztMsg(dto.getFbzt()));
        return dto;
    }

    public static List<XtGgtzxxDto> build(List<XtGgtzxx> entityList) {
        List<XtGgtzxxDto> dtoList = new ArrayList<>();
        for (XtGgtzxx entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    public static Page<XtGgtzxxDto> buildDto(Page<XtGgtzxx> entityPage) {
        Page<XtGgtzxxDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }

    private static String getJjdjzMsg(String val) {
        Optional<GgjjdjConstant> map = EnumUtil.getEnumObject(GgjjdjConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getGgztMsg(String val) {
        Optional<GgztConstant> map = EnumUtil.getEnumObject(GgztConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getFbztMsg(String val) {
        Optional<GgfbztConstant> map = EnumUtil.getEnumObject(GgfbztConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

}
