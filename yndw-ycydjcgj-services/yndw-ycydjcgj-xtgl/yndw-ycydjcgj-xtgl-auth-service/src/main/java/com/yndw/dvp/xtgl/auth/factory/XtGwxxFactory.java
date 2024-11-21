package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.constant.ZzxxDwbzConstant;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxDto;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtGwxxFactory {
    public static XtGwxxDto build(XtGwxx entity) {
        XtGwxxDto dto = new XtGwxxDto();
        BeanUtil.copyProperties(entity, dto);

        dto.setGwztdmStr(getGwztdmMsg(dto.getGwztdm()));
        return dto;
    }

    public static List<XtGwxxDto> build(List<XtGwxx> entityList) {
        List<XtGwxxDto> dtoList = new ArrayList<>();
        for (XtGwxx entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    public static Page<XtGwxxDto> build(Page<XtGwxx> entityPage) {
        Page<XtGwxxDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }


    private static String getGwztdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }
}
