package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxExcelDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtCzyxxFactory {
    public static XtCzyxxDto build(XtCzyxx entity) {
        XtCzyxxDto dto = new XtCzyxxDto();
        BeanUtil.copyProperties(entity, dto);
        dto.setCzyztdmStr(getCzyztdmMsg(dto.getCzyztdm()));
        return dto;
    }

    public static XtCzyxxExcelDto buildExcelDto(XtCzyxx entity) {
        XtCzyxxExcelDto dto = new XtCzyxxExcelDto();
        BeanUtil.copyProperties(entity, dto);
        dto.setCzyztdmStr(getCzyztdmMsg(dto.getCzyztdm()));
        return dto;
    }

    public static List<XtCzyxxDto> build(List<XtCzyxx> entityList) {
        List<XtCzyxxDto> dtolist = new ArrayList<>();
        for (XtCzyxx entity : entityList) {
            dtolist.add(build(entity));
        }
        return dtolist;
    }

    public static List<XtCzyxxExcelDto> buildExcelDto(List<XtCzyxx> entityList) {
        List<XtCzyxxExcelDto> dtolist = new ArrayList<>();
        for (XtCzyxx entity : entityList) {
            dtolist.add(buildExcelDto(entity));
        }
        return dtolist;
    }

    public static Page<XtCzyxxDto> build(Page<XtCzyxx> entityPage) {
        Page<XtCzyxxDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }

    public static Page<XtCzyxxDto> buildDto(Page<XtCzyxxDto> dtoPage) {
        for (XtCzyxxDto dto : dtoPage.getRecords()) {
            dto.setCzyztdmStr(getCzyztdmMsg(dto.getCzyztdm()));
        }
        return dtoPage;
    }

    private static String getCzyztdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

}
