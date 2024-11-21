package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.dto.ZTreeDto;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.xtgl.auth.constant.JsxxJsbzConstant;
import com.yndw.dvp.xtgl.auth.dto.XtJsxxDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.model.XtJsxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtJsxxFactory {
    public static XtJsxxDto build(XtJsxx entity) {
        XtJsxxDto dto = new XtJsxxDto();
        BeanUtil.copyProperties(entity, dto);

        dto.setJsztdmStr(getZzztdmMsg(dto.getJsztdm()));
        dto.setJsbzStr(getJsbzMSsg(dto.getJsbz()));
        return dto;
    }

    public static List<XtJsxxDto> build(List<XtJsxx> entityList) {
        List<XtJsxxDto> dtoList = new ArrayList<>();
        for (XtJsxx entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    public static List<ZTreeDto> buildZTree(String gwjss, List<XtJsxx> entityList) {
        List<ZTreeDto> dtoList = new ArrayList<>();
        for (XtJsxx entity : entityList) {
            ZTreeDto dto = new ZTreeDto(entity.getBh(), entity.getSjbh(), entity.getJsmc(), false, true);
            if (StrUtil.indexOf(gwjss, "," + entity.getBh() + ",", 0, true) >= 0) {
                dto.setChecked(true);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    private static String getJsbzMSsg(String val) {
        Optional<JsxxJsbzConstant> map = EnumUtil.getEnumObject(JsxxJsbzConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getZzztdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }


    public static List<YyUITreeDto> buildJsxxTree(String gwyys, String gwjss, List<XtJsxx> jsxxList, List<XtYyxx> yyxxList) {

        List<YyUITreeDto> yyNodeList = new ArrayList<>();
        List<YyUITreeDto> jsxxNodeList = new ArrayList<>();
        List<List<YyUITreeDto>> tree = new ArrayList<>();
        //构建第一级节点(应用)
        for (XtYyxx entity : yyxxList) {
            YyUITreeDto dto = new YyUITreeDto(entity.getBh(), "", "", entity.getYymc(), "yyxx", "", new ArrayList<>(), false, true, true);
            yyNodeList.add(dto);
        }

        jsxxNodeList = convertToTreeNode(gwjss, jsxxList, yyNodeList);
        //加载应用节点下的角色节点
        for (int i = 0; i < yyNodeList.size(); i++) {
            yyNodeList.get(i).setChildren(buildTree(yyNodeList.get(i).getId(),jsxxNodeList));
        }
        return yyNodeList;
    }

    /**
     * 将角色列表转化为树形结构列表
     *
     * @param gwjss
     * @param jsxxList
     * @param yyNodeList
     * @return
     */
    public static List<YyUITreeDto> convertToTreeNode(String gwjss, List<XtJsxx> jsxxList, List<YyUITreeDto> yyNodeList) {

        List<YyUITreeDto> loopTreeList = new ArrayList<>();
        XtJsxx loopJsxx = new XtJsxx();
        //先将功能列表转化为树形结构列表
        String [] gwjsArr = gwjss.split(",");
        for (int i = 0; i < jsxxList.size(); i++) {
            loopJsxx = jsxxList.get(i);
            YyUITreeDto dto = new YyUITreeDto(loopJsxx.getBh(), loopJsxx.getSjbh(), loopJsxx.getSsyybh(), loopJsxx.getJsmc(), "jsxx", "", new ArrayList<>(), false, true, false);
//            if (gwjss.indexOf(loopJsxx.getBh()) > 0) {
            if (Arrays.asList(gwjsArr).contains(loopJsxx.getBh())) {
                dto.setChecked(true);
            }
            loopTreeList.add(dto);
        }
        //处理各个应用下的第一级功能节点
        String yybh = "";
        for (int i = 0; i < yyNodeList.size(); i++) {
            yybh = yyNodeList.get(i).getId();
            for (int j = 0; j < loopTreeList.size(); j++) {
                if (StrUtil.equals(yybh, loopTreeList.get(j).getSsyyid()) && StrUtil.equals(loopTreeList.get(j).getSjid(),"0")) {
                    loopTreeList.get(j).setSjid(yybh);
                }
            }
        }
        return loopTreeList;
    }

    /**
     * 递归构建节点的子节点
     * @param sjid
     * @param gnxxNodeList
     * @return
     */
    public static List<YyUITreeDto> buildTree(String sjid, List<YyUITreeDto> gnxxNodeList) {
        List<YyUITreeDto> treeList = new ArrayList<>();
        for (YyUITreeDto yyUITreeDto : gnxxNodeList) {
            if (StrUtil.equals(yyUITreeDto.getSjid(), sjid)) {
                yyUITreeDto.setChildren(buildTree(yyUITreeDto.getId(), gnxxNodeList));
                treeList.add(yyUITreeDto);
            }
        }
        return treeList;
    }


}
