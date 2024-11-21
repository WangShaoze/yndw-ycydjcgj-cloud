package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.dto.ChildTreeDto;
import com.yndw.dvp.common.core.dto.ZTreeDto;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.utils.EnumUtil;
import com.yndw.dvp.common.core.utils.TreeUtil;
import com.yndw.dvp.xtgl.auth.constant.GnxxLxdmConstant;
import com.yndw.dvp.xtgl.auth.constant.GnxxYcbzConstant;
import com.yndw.dvp.xtgl.auth.dto.MenuLeaf;
import com.yndw.dvp.xtgl.auth.dto.MenuMeta;
import com.yndw.dvp.xtgl.auth.dto.MenuNode;
import com.yndw.dvp.xtgl.auth.dto.XtGnxxDto;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;

import java.util.*;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtGnxxFactory {
    public static XtGnxxDto build(XtGnxx entity) {
        XtGnxxDto dto = new XtGnxxDto();
        BeanUtil.copyProperties(entity, dto);

        dto.setYcbzStr(getYcbzMsg(dto.getYcbz()));
        dto.setGnztdmStr(getGnztdmMsg(dto.getGnztdm()));
        dto.setGnlxdmStr(getGnlxdmMsg(dto.getGnlxdm()));
        return dto;
    }

    public static List<XtGnxxDto> build(List<XtGnxx> entityList) {
        List<XtGnxxDto> dtoList = new ArrayList<>();
        for (XtGnxx entity : entityList) {
            dtoList.add(build(entity));
        }
        return dtoList;
    }

    private static String getGnztdmMsg(String val) {
        Optional<ZtdmConstant> map = EnumUtil.getEnumObject(ZtdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getGnlxdmMsg(String val) {
        Optional<GnxxLxdmConstant> map = EnumUtil.getEnumObject(GnxxLxdmConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    private static String getYcbzMsg(String val) {
        Optional<GnxxYcbzConstant> map = EnumUtil.getEnumObject(GnxxYcbzConstant.class, e -> e.getValue().equals(val));
        return map.isPresent() ? map.get().getLabel() : "未知";
    }

    public static List<ZTreeDto> buildZTree(String jsgns, List<XtGnxx> entityList) {
        List<ZTreeDto> dtoList = new ArrayList<>();
        String [] jsGnArr = jsgns.split(",");
        for (XtGnxx entity : entityList) {
            ZTreeDto dto = new ZTreeDto(entity.getBh(), entity.getSjbh(), entity.getGnmc(), false, true);
//            if (StrUtil.indexOf(jsgns, "," + entity.getBh() + ",", 0, true) >= 0) {
            if (Arrays.asList(jsGnArr).contains(entity.getBh())) {
                dto.setChecked(true);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<ChildTreeDto> buildChildTree(List<SecurityMenu> menuList) {
        ChildTreeDto treeDto = null;
        Map<String, Object> otherParameter = null;
        List<ChildTreeDto> treeDtoList = new ArrayList<>();
        for (SecurityMenu securityMenu : menuList) {
            treeDto = new ChildTreeDto();
            treeDto.setBh(securityMenu.getBh());
            treeDto.setSjbh(securityMenu.getSjbh());
            otherParameter = new HashMap<>();
            otherParameter.put("gnmc", securityMenu.getGnmc());
            otherParameter.put("gnljdz", securityMenu.getGnljdz());
            treeDto.setOtherParameter(otherParameter);
            treeDtoList.add(treeDto);
        }
        return TreeUtil.buildByRecursive(treeDtoList, CommonConstant.TREE_TOP_PID, null);
    }

    public static List<MenuNode> buildGnxxMenuTree(List<SecurityMenu> menuList) {
        List<SecurityMenu> tmpList = menuList;
        List<MenuNode> routers = new ArrayList<MenuNode>();

        MenuNode node =  null;
        SecurityMenu tmpMenu = null;
        MenuMeta meta = null;

        for(int i=0; i < tmpList.size(); i++){
            tmpMenu = tmpList.get(i);
            if(tmpMenu.getSjbh().equals("0")){
                node = new MenuNode();
                meta = new MenuMeta();
                node.setBh(tmpMenu.getBh());
                node.setComponent(tmpMenu.getGnqxbz());
                node.setName(tmpMenu.getGnljdz());
                node.setPath(tmpMenu.getGnljdz());
                meta.setIcon("user");
                meta.setLink("");
                meta.setNoCache(false);
                meta.setTitle(tmpMenu.getGnmc());
                node.setMeta(meta);
                node.setHidden(false);
                node.setRedirect("noRedirect");

                routers.add(node);
                tmpList.remove(i--);
                node = null;
                meta = null;
            }
        }
        routers = recurRouters(routers,menuList);
        return routers;
    }
    public static List<MenuNode> recurRouters(List<MenuNode> nodeList, List<SecurityMenu> menuList) {

        List<MenuNode> tmpNodeList = null;
        MenuNode tempNode =  null;
        SecurityMenu tmpMenu = null;
        MenuNode node =  null;
        MenuMeta meta = null;

        for (int i = 0; i < nodeList.size(); i++) {
            tmpNodeList = new ArrayList<MenuNode>();
            tempNode = nodeList.get(i);
            for (int j = 0; j < menuList.size(); j++) {
                tmpMenu = menuList.get(j);
                if (tmpMenu.getSjbh().equals(tempNode.getBh())){
                    node = new MenuNode();
                    meta = new MenuMeta();
                    node.setComponent(tmpMenu.getGnqxbz());
                    node.setName(tmpMenu.getGnljdz());
                    node.setPath(tmpMenu.getGnljdz());
                    node.setHidden(false);
//                    node.setBh(tmpMenu.getBh());
//                    node.setRedirect("noRedirect");
                    meta.setIcon("user");
                    meta.setLink("");
                    meta.setNoCache(false);
                    meta.setTitle(tmpMenu.getGnmc());
                    node.setMeta(meta);

                    tmpNodeList.add(node);
                    menuList.remove(j--);
                    node =  null;
                    meta = null;
                }
            }
            tempNode.setChildren(recurRouters(tmpNodeList,menuList));
            if (tempNode.getChildren().size() > 0){
                tempNode.setAlwaysShow(true);
            }
        }
        return nodeList;
//        for (int i = 0; i < nodeList.size(); i++) {
//            tmpNodeList = new ArrayList<MenuNode>();
//            for (int j = 0; j < menuList.size(); j++) {
//                tmpMenu = menuList.get(j);
//                if (tmpMenu.getSjbh().equals(nodeList.get(i).getBh())) {
//                    node = new MenuNode();
//                    meta = new MenuMeta();
//                    node.setAlwaysShow(false);
//                    node.setComponent(tmpMenu.getGnqxbz());
//                    node.setName(tmpMenu.getGnljdz());
//                    node.setPath(tmpMenu.getGnljdz());
//                    node.setBh(tmpMenu.getBh());
//                    node.setHidden(false);
//                    node.setRedirect("noRedirect");
//                    meta.setIcon("user");
//                    meta.setLink("");
//                    meta.setNoCache(false);
//                    meta.setTitle(tmpMenu.getGnmc());
//                    node.setMeta(meta);
//
//                    tmpNodeList.add(node);
//                    menuList.remove(j--);
//                }
//            }
//            nodeList.get(i).setChildren(tmpNodeList);
//            tmpNodeList =null;
//            meta = null;
//            node =null;
//        }
//        return recurRouters(tmpNodeList,menuList);
    }

}
