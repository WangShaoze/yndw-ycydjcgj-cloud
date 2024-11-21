package com.yndw.dvp.xtgl.log.factory;

import cn.hutool.core.bean.BeanUtil;
import com.yndw.dvp.xtgl.log.dto.SysLogExcelDto;
import com.yndw.dvp.xtgl.log.model.XtLog;

import java.util.ArrayList;
import java.util.List;

public class SysLogExcelFactory {

    public static List<SysLogExcelDto> buildExcelDto(List<XtLog> logsList) {
        List<SysLogExcelDto> dtolist = new ArrayList<>();
        for (XtLog entity : logsList) {
            dtolist.add(buildExcelDto(entity));
        }
        return dtolist;
    }

    public static SysLogExcelDto buildExcelDto(XtLog entity) {
        SysLogExcelDto dto = new SysLogExcelDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }
}
