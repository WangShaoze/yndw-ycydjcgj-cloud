package com.yndw.dvp.xtgl.dm.factory;

import cn.hutool.core.bean.BeanUtil;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.dm.dto.XtDmflmxDto;
import com.yndw.dvp.xtgl.dm.model.XtDmflmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create By Carlos
 * 2020/7/9
 */
public class XtDmflmxFactory {
    public static XtDmflmxDto build(XtDmflmx entity) {
        XtDmflmxDto dto = new XtDmflmxDto();
        BeanUtil.copyProperties(entity, dto);
        dto.setZtdmStr(getZtdmMsg(dto.getZtdm()));
        return dto;
    }

    public static List<XtDmflmxDto> build(List<XtDmflmx> entityList) {
        List<XtDmflmxDto> dtolist = new ArrayList<>();
        for (XtDmflmx entity : entityList) {
            dtolist.add(build(entity));
        }
        return dtolist;
    }


    private static String getZtdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }
}
