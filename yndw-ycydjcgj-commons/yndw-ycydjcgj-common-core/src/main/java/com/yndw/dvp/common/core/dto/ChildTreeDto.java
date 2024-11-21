package com.yndw.dvp.common.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Create By Carlos
 * 2020/6/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChildTreeDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /*id和pid必须赋值，otherParameter装一些其他参数，比如name，key-value键值对的形式*/
    /*将你从数据库查出来的集合循环，然后对每一行数据进行赋值转换成TreeBean结构，调用TreeUtils工具即可得到树形结构列表*/
    private String bh;//id

    private String sjbh;//pid

    private Map<String, Object> otherParameter;//其他参数，需要展示的参数,比如name之类的


    /*---------------------------------下面这些不需要你赋值-------------------------------------------*/
    private Boolean hasChildren;//是否含有下一级

    private List<ChildTreeDto> children;//包含的子节点

    private Long childrenSize;//子节点的集合大小

    private Long level;//级别
}
