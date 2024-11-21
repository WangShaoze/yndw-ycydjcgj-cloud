package com.yndw.dvp.common.core.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页实体类
 *
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private Integer code;
    private long page;
    private long limit;
    private Long count;
    private List<T> data;

    public static <T> PageResult<T> succeed(Page<T> page) {
        return build(ResultCode.SUCCESS.getCode(), page.getCurrent(),page.getSize(),page.getTotal(),page.getRecords());
    }

    public static <T> PageResult<T> failed() {
        return build(ResultCode.SUCCESS.getCode(), 0,0,0,null);
    }

    public static <T> PageResult<T> build(Integer code,long pageNum, long pageSize,long pageTotal,List<T> data) {
        return new PageResult<T>(code,pageNum,pageSize,pageTotal,data);
    }
}
