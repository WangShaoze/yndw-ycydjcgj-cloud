package com.yndw.dvp.common.core.utils;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.dto.ChildTreeDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  
 *  * @projectName vcsrm-parent
 *  * @title     TreeUtils   
 *  * @package    com.clfps.utils  
 *  * @description    树形结构utils
 *  * @author IT_CREAT     
 *  * @date  2019 2019/7/1 15:39  
 *  * @version V1.0.0 
 *  
 */
public class TreeUtil {

    /**
     * 使用递归方法建树
     * @param treeBeans 按规则转换的树形实体集合列表，没有层次结构
     * @param topId 指定顶级节点id，必须指定
     * @param topChildTreeDto 指定顶级节点构造的树形实体，可以传入空值，但是传入空值会将顶级节点的数据去除，返回指定顶级节点的子节点树形结构集合
     * @return 树形结构实体集合
     */
    public static List<ChildTreeDto> buildByRecursive(List<ChildTreeDto> treeBeans, String topId , ChildTreeDto topChildTreeDto) {
        List<ChildTreeDto> trees = new ArrayList<>();
        ChildTreeDto treeBeanTop = null;
        for (ChildTreeDto treeBean : treeBeans) {
            if(StrUtil.equals(treeBean.getSjbh(),topId)){
                Long level = 1L;
                if(!ObjectUtils.isEmpty(topChildTreeDto) && StrUtil.equals(topChildTreeDto.getBh(), topId)){
                    level = 2L;
                }
                ChildTreeDto treeBean2 = findChildren(treeBean,treeBeans,level);
                if(CollectionUtils.isEmpty(treeBean2.getChildren())){
                    treeBean2.setHasChildren(false);
                    treeBean2.setLevel(level);
                }else {
                    treeBean2.setHasChildren(true);
                    treeBean2.setLevel(level);
                    treeBean2.setChildrenSize((long) treeBean2.getChildren().size());
                }
                trees.add(treeBean2);
            }else if(!ObjectUtils.isEmpty(topChildTreeDto) && StrUtil.equals(treeBean.getBh(), topId)){
                treeBeanTop = treeBean;
            }
        }
        if(!ObjectUtils.isEmpty(treeBeanTop)){
            treeBeanTop.setChildren(trees)
                    .setChildrenSize((long) trees.size())
                    .setHasChildren(true)
                    .setLevel(1L);
            trees = new ArrayList<>();
            trees.add(treeBeanTop);
        }
        return trees;
    }


    /**
     * 递归查找子节点
     * @param
     * @return
     */
    private static ChildTreeDto findChildren(ChildTreeDto treeBean, List<ChildTreeDto> treeBeans, Long level) {
        for (ChildTreeDto it : treeBeans) {
            if(StrUtil.equals(treeBean.getBh(), it.getSjbh())) {
                if (treeBean.getChildren() == null) {
                    treeBean.setChildren(new ArrayList<>());
                }
                ChildTreeDto treeBean2 = findChildren(it,treeBeans,level+1);
                if(!CollectionUtils.isEmpty(treeBean2.getChildren())){
                    treeBean2.setHasChildren(true);
                    treeBean2.setChildrenSize((long) treeBean.getChildren().size());
                    treeBean2.setLevel(level+1);
                }else if(treeBean2.getChildren() == null || treeBean2.getChildren().size() == 0){
                    treeBean2.setHasChildren(false);
                    treeBean2.setLevel(level+1);
                }
                treeBean.getChildren().add(treeBean2);
            }
        }
        return treeBean;
    }
}
