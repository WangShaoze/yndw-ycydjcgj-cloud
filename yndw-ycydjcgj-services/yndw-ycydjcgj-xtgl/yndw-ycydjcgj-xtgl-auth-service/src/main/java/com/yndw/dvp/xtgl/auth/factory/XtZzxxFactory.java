package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.constant.ZzxxDwbzConstant;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtZzxxTreeDto;
import com.yndw.dvp.xtgl.auth.model.XtZzxx;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtZzxxFactory {

    public static List<XtZzxxTreeDto> build(List<XtZzxx> entityList) {
        List<XtZzxxTreeDto> retDtoList = new ArrayList<>();
        for (XtZzxx entity : entityList) {
            retDtoList.add(build(entity));
        }
        //构建树
        List<XtZzxxTreeDto> temp = new ArrayList<>();
        for (XtZzxxTreeDto menu : retDtoList) {
            //子节点数据
            List<XtZzxxTreeDto> childList = new ArrayList<>();
            for (XtZzxxTreeDto childMenu : retDtoList) {
                if (Objects.equals(menu.getBh(), childMenu.getSjbh())) {
                    childList.add(childMenu);
                    temp.add(childMenu);
                }
            }
            menu.setChildren(childList);
        }
        //原数据集合移除子节点
        if (temp.size() > 0) {
            retDtoList.removeAll(temp);
        }
        return retDtoList;
    }

    public static XtZzxxTreeDto build(XtZzxx entity) {
        XtZzxxTreeDto dto = new XtZzxxTreeDto();
        BeanUtil.copyProperties(entity, dto);

        dto.setZzztdmStr(getZzztdmMsg(dto.getZzztdm()));
        dto.setDwbzStr(getDwbzMsg(dto.getDwbz()));
        return dto;
    }

    private static String getZzztdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getDwbzMsg(String val) {
        Optional<ZzxxDwbzConstant> map = EnumUtil.getEnumObject(ZzxxDwbzConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static List<XtZzxxTreeDto> buildTree(List<XtZzxxTreeDto> nodes) {
        Map<String, List<XtZzxxTreeDto>> children = nodes.stream().filter(node -> !node.getSjbh().equals("0"))
                .collect(Collectors.groupingBy(node -> node.getSjbh()));
        nodes.forEach(node -> node.setChildren(children.get(node.getBh())));
        return nodes.stream().filter(node -> node.getSjbh().equals("0")).collect(Collectors.toList());
    }

//    public static List<XtZzxxTreeDto> recover(List<XtZzxxDto> dtoList) {
//        List<XtZzxxTreeDto> retDtoList = new ArrayList<>();
//        List<XtZzxxDto> tmpList = dtoList;
//        XtZzxxDto entity = new XtZzxxDto();
//        for (int i = 0; i < tmpList.size(); i++) {
//            entity = dtoList.get(i);
//            for (int j = i+1; j < tmpList.size(); j++) {
//                if (entity.getBh().equals(tmpList.get(j).getSjbh())){
//
//                }
//            }
//        }
//        return retDtoList;
//    }
}
