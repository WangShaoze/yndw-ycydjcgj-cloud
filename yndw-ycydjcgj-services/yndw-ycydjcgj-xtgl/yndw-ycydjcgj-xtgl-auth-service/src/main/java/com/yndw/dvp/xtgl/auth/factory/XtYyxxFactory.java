package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.xtgl.auth.constant.YyxxXylxConstant;
import com.yndw.dvp.xtgl.auth.constant.YyztdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtYyxxDto;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;

import java.util.*;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtYyxxFactory {
    public static XtYyxxDto build(XtYyxx entity) {
        XtYyxxDto dto = new XtYyxxDto();
        BeanUtil.copyProperties(entity, dto);
        dto.setYyztdmStr(getYyztdmMsg(dto.getYyztdm()));
        dto.setXylxStr(getXylxMsg(dto.getXylx()));
        return dto;
    }

    public static List<XtYyxxDto> build(List<XtYyxx> entityList) {
        List<XtYyxxDto> dtoList = new ArrayList<>();
        for (XtYyxx entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    public static Page<XtYyxxDto> buildDto(Page<XtYyxx> entityPage) {
        Page<XtYyxxDto> dtoPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        dtoPage.setRecords(build(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }

    private static String getYyztdmMsg(String val) {
        Optional<YyztdmConstant> map = EnumUtil.getEnumObject(YyztdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getXylxMsg(String val) {
        Optional<YyxxXylxConstant> map = EnumUtil.getEnumObject(YyxxXylxConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }


//    public static List<ZTreeDto> buildZTree(String jsgns, List<XtDmflmx> dmflmxList, List<XtYyxx> entityList) {
//        List<ZTreeDto> dtoList = new ArrayList<>();
//        for (XtYyxx entity : entityList) {
//            ZTreeDto dto = new ZTreeDto(entity.getBh(),entity.getSjbh(),entity.getGnmc(),false,true);
//            if(StrUtil.indexOf(jsgns,","+entity.getBh()+",",0,true) >= 0){
//                dto.setChecked(true);
//            }
//            dtoList.add(dto);
//        }
//        return dtoList;
//    }
//
//    public static List<ChildTreeDto> buildChildTree(List<SecurityMenu> menuList) {
//        ChildTreeDto treeDto = null;
//        Map<String,Object> otherParameter = null;
//        List<ChildTreeDto> treeDtoList = new ArrayList<>();
//
//        for (SecurityMenu securityMenu : menuList) {
//            treeDto = new ChildTreeDto();
//            treeDto.setBh(securityMenu.getBh());
//            treeDto.setSjbh(securityMenu.getSjbh());
//            otherParameter = new HashMap<>();
//            otherParameter.put("gnmc",securityMenu.getGnmc());
//            otherParameter.put("gnljdz",securityMenu.getGnljdz());
//            treeDto.setOtherParameter(otherParameter);
//            treeDtoList.add(treeDto);
//        }
//        return TreeUtil.buildByRecursive(treeDtoList,CommonConstant.TREE_TOP_PID,null);
//    }
}
