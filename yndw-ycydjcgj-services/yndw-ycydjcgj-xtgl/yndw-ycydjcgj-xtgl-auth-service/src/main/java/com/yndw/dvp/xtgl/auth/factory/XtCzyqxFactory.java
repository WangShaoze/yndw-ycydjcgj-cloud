package com.yndw.dvp.xtgl.auth.factory;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.dto.ZTreeNoCheckDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;

import java.util.*;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class XtCzyqxFactory {
    //zTree
    public static List<ZTreeNoCheckDto> buildZTree(String czyJsGns, String czygns, String czyyys, List<XtGnxx> gnxxList, List<XtYyxx> yyxxList) {
        List<ZTreeNoCheckDto> dtoList = new ArrayList<>();
        List<ZTreeNoCheckDto> levelOneList = new ArrayList<>();
        List<ZTreeNoCheckDto> levelTwoList = new ArrayList<>();
        List<ZTreeNoCheckDto> levelThreeList = new ArrayList<>();
        List<ZTreeNoCheckDto> levelFourList = new ArrayList<>();
        for (XtYyxx entity : yyxxList) {
            ZTreeNoCheckDto dto = new ZTreeNoCheckDto(entity.getBh(), "-1", entity.getYymc(), false, true, false);
            if (StrUtil.indexOf(czyyys, "," + entity.getBh() + ",", 0, true) >= 0) {
                dto.setChecked(true);
            }
            dto.setNocheck(true);
            levelOneList.add(dto);
        }
        for (int i = 0; i < levelOneList.size(); i++) {
            ZTreeNoCheckDto entity = levelOneList.get(i);
            for (int k = 0; k < gnxxList.size(); k++) {
                XtGnxx entityGnxx = gnxxList.get(k);
                if (entity.getBh().equals(entityGnxx.getSsyybh()) && entityGnxx.getSjbh().equals("0")) {
                    ZTreeNoCheckDto dto = new ZTreeNoCheckDto(entityGnxx.getBh(), entityGnxx.getSsyybh(), entityGnxx.getGnmc(), false, true, false);
                    if (StrUtil.indexOf(czygns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setChecked(true);
                    }
                    if (StrUtil.indexOf(czyJsGns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setNocheck(true);
                    }
                    levelTwoList.add(dto);
                    gnxxList.remove(entityGnxx);
                    k--;
                }
            }
        }
        for (int i = 0; i < levelTwoList.size(); i++) {
            ZTreeNoCheckDto entity = levelTwoList.get(i);
            for (int k = 0; k < gnxxList.size(); k++) {
                XtGnxx entityGnxx = gnxxList.get(k);
                if (entity.getBh().equals(entityGnxx.getSjbh())) {
                    ZTreeNoCheckDto dto = new ZTreeNoCheckDto(entityGnxx.getBh(), entityGnxx.getSjbh(), entityGnxx.getGnmc(), false, true, false);
                    if (StrUtil.indexOf(czygns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setChecked(true);
                    }
                    if (StrUtil.indexOf(czyJsGns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setNocheck(true);
                    }
                    levelThreeList.add(dto);
                    gnxxList.remove(entityGnxx);
                    k--;
                }
            }
        }
        for (int i = 0; i < levelThreeList.size(); i++) {
            ZTreeNoCheckDto entity = levelThreeList.get(i);
            for (int k = 0; k < gnxxList.size(); k++) {
                XtGnxx entityGnxx = gnxxList.get(k);
                if (entity.getBh().equals(entityGnxx.getSjbh())) {

                    ZTreeNoCheckDto dto = new ZTreeNoCheckDto(entityGnxx.getBh(), entityGnxx.getSjbh(), entityGnxx.getGnmc(), false, true, false);
                    if (StrUtil.indexOf(czygns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setChecked(true);
                    }
                    if (StrUtil.indexOf(czyJsGns, "," + entityGnxx.getBh() + ",", 0, true) >= 0) {
                        dto.setNocheck(true);
                    }
                    levelFourList.add(dto);
                }
            }
        }
        levelThreeList.addAll(levelFourList);
        levelTwoList.addAll(levelThreeList);
        levelOneList.addAll(levelTwoList);
        dtoList = levelOneList;
        return dtoList;
    }

    //layui自带的tree
    public static List<YyUITreeDto> buildGnxxTree(String czyJsGns, String czygns, String czyyys, List<XtGnxx> gnxxList, List<XtYyxx> yyxxList) {

        List<YyUITreeDto> yyNodeList = new ArrayList<>();
        List<YyUITreeDto> gnxxNodeList = new ArrayList<>();
        List<List<YyUITreeDto>> tree = new ArrayList<>();
        //构建第一级节点(应用)
        for (XtYyxx entity : yyxxList) {
            YyUITreeDto dto = new YyUITreeDto(entity.getBh(), "", "", entity.getYymc(), "yyxx", "", new ArrayList<>(), false, true, false);
            if (StrUtil.indexOf(czyyys, "," + entity.getBh() + ",", 0, true) >= 0) {
//                dto.setChecked(true);
                dto.setChecked(false);
            }
            dto.setDisabled(true);
            yyNodeList.add(dto);
        }

        gnxxNodeList = convertToTreeNode(czyJsGns, czygns, gnxxList, yyNodeList);
        //加载应用节点下的功能节点
        for (int i = 0; i < yyNodeList.size(); i++) {
            yyNodeList.get(i).setChildren(buildTree(yyNodeList.get(i).getId(),gnxxNodeList));
        }

        //数据回显
        yyNodeList = recover(czygns, yyNodeList);
        return yyNodeList;
    }

    /**
     * 递归设置数据回显
     * @param czygns
     * @param gnxxNodeList
     * @return
     */
    public static List<YyUITreeDto> recover(String czygns, List<YyUITreeDto> gnxxNodeList) {
        List<YyUITreeDto> treeList = gnxxNodeList;
        for (int i = 0; i < treeList.size(); i++) {
            YyUITreeDto yyUITreeDto = treeList.get(i);
            if (yyUITreeDto.getChildren().size() > 0){
                yyUITreeDto.setChildren(recover(czygns, yyUITreeDto.getChildren()));
            }else {
                String [] czygnArr = czygns.split(",");
                if (Arrays.asList(czygnArr).contains(yyUITreeDto.getId())) {
                    yyUITreeDto.setChecked(true);
                }
            }
        }
        return treeList;
    }

    /**
     * 将功能列表转化为树形结构列表
     *
     * @param czyJsGns
     * @param czygns
     * @param gnxxList
     * @param yyNodeList
     * @return
     */
    public static List<YyUITreeDto> convertToTreeNode(String czyJsGns, String czygns, List<XtGnxx> gnxxList, List<YyUITreeDto> yyNodeList) {

        List<YyUITreeDto> loopTreeList = new ArrayList<>();
        XtGnxx loopGnxx = new XtGnxx();

        String [] czygnArr = czygns.split(",");
        String [] czyJsGnArr = czyJsGns.split(",");
        //先将功能列表转化为树形结构列表
        for (int i = 0; i < gnxxList.size(); i++) {
            loopGnxx = gnxxList.get(i);
            YyUITreeDto dto = new YyUITreeDto(loopGnxx.getBh(), loopGnxx.getSjbh(), loopGnxx.getSsyybh(), loopGnxx.getGnmc(), "gnxx", "", new ArrayList<>(), false, true, false);
//            if (Arrays.asList(czygnArr).contains(loopGnxx.getBh())) {
//                dto.setChecked(true);
//            }
            if (Arrays.asList(czyJsGnArr).contains(loopGnxx.getBh())) {
                dto.setDisabled(true);
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

    /**
     * 以下将移植到zdygnz相关类下
     */
    public static List<YyUITreeDto> buildGnzGnxxTree(String czygns, String czyyys, List<XtGnxx> gnxxList, List<XtYyxx> yyxxList) {

        List<YyUITreeDto> yyNodeList = new ArrayList<>();
        List<YyUITreeDto> gnxxNodeList = new ArrayList<>();
        List<List<YyUITreeDto>> tree = new ArrayList<>();
        //构建第一级节点(应用)
        for (XtYyxx entity : yyxxList) {
            YyUITreeDto dto = new YyUITreeDto(entity.getBh(), "", "", entity.getYymc(), "yyxx", "", new ArrayList<>(), false, true, false);
            if (StrUtil.indexOf(czyyys, "," + entity.getBh() + ",", 0, true) >= 0) {
                dto.setChecked(true);
            }
            dto.setDisabled(true);
            yyNodeList.add(dto);
        }

        gnxxNodeList = convertToGnzTreeNode( czygns, gnxxList, yyNodeList);
        //把权限功能从czygns中删除,保存到menuGnbhList中
        List<String> gnbhList = StrUtil.split(czygns, ',');
        List<String> menuGnbhList = new ArrayList<>();
        List<XtGnxx> authGnxxList = new ArrayList<>();
        for (int i = 0; i < gnbhList.size(); i++) {
            for (int j = 0; j < gnxxList.size(); j++) {
                if (StrUtil.equals(gnbhList.get(i),gnxxList.get(j).getBh())){
                    menuGnbhList.add(gnbhList.get(i));
                    break;
                }
            }
        }
        String authGnxx = String.join(",",menuGnbhList);

        //加载应用节点下的功能节点
        for (int i = 0; i < yyNodeList.size(); i++) {
            yyNodeList.get(i).setChildren(buildTree(yyNodeList.get(i).getId(),gnxxNodeList,authGnxx));
        }
        return yyNodeList;
    }
    /**
     * 将功能列表转化为树形结构列表
     *
     * @param czygns
     * @param gnxxList
     * @param yyNodeList
     * @return
     */
    public static List<YyUITreeDto> convertToGnzTreeNode(String czygns, List<XtGnxx> gnxxList, List<YyUITreeDto> yyNodeList) {

        List<YyUITreeDto> loopTreeList = new ArrayList<>();
        XtGnxx loopGnxx = new XtGnxx();

        List<String> gnbhList = StrUtil.split(czygns, ',');
        List<XtGnxx> authGnxxList = new ArrayList<>();
        //先将功能列表转化为树形结构列表
        for (int i = 0; i < gnbhList.size(); i++) {
            for (int j = 0; j < gnxxList.size(); j++) {
                if (StrUtil.equals(gnxxList.get(j).getBh(),gnbhList.get(i))){
                    authGnxxList.add(gnxxList.get(j));
                    break;
                }
            }
        }
        for (int i = 0; i < authGnxxList.size(); i++) {
            loopGnxx = authGnxxList.get(i);
            YyUITreeDto dto = new YyUITreeDto(loopGnxx.getBh(), loopGnxx.getSjbh(), loopGnxx.getSsyybh(), loopGnxx.getGnmc(), "gnxx", "", new ArrayList<>(), false, true, false);
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
    public static List<YyUITreeDto> buildTree(String sjid, List<YyUITreeDto> gnxxNodeList, String authGnxx) {
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
