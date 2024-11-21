package com.yndw.dvp.common.core.utils;

/**
 * Create By Carlos
 * 2020/9/28
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {
    public static Integer[] getPage(Integer totalCount, Integer limit){
        int totalPages = cn.hutool.core.util.PageUtil.totalPage(totalCount,limit);
        Integer[] pages = new Integer[totalPages];
        for(int i = 0; i < totalPages;i++){
            pages[i] = (i+1);
        }
        return pages;
    }

}
