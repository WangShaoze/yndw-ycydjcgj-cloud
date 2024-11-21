package com.yndw.dvp.xtgl.dm.factory;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.dm.dto.XtDmflDto;
import com.yndw.dvp.xtgl.dm.model.XtDmfl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create By Carlos
 * 2020/7/9
 */
public class XtDmflFactory {
    public static XtDmflDto build(XtDmfl entity) {
        XtDmflDto dto = new XtDmflDto();
        BeanUtil.copyProperties(entity, dto);
        dto.setZtdmStr(getZtdmMsg(dto.getZtdm()));
        return dto;
    }

    public static List<XtDmflDto> build(List<XtDmfl> entityList) {
        List<XtDmflDto> dtolist = new ArrayList<>();
        for (XtDmfl entity : entityList) {
            dtolist.add(build(entity));
        }
        return dtolist;
    }

    public static Page<XtDmflDto> build(Page<XtDmfl> entityPage) {
        Page<XtDmflDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }

    private static String getZtdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }
}
