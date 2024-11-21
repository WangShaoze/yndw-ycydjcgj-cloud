package com.yndw.dvp.common.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yndw.dvp.common.core.lock.DistributedLock;

/**
 * service接口父类
 *
 * 
 * @date 2019/1/10
 * <p>
 * 
 * 
 */
public interface ISuperService<T> extends IService<T> {
    /**
     * 幂等性新增记录
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper, String msg) throws Exception;

    boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper) throws Exception;
    /**
     * 幂等性修改记录
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean updateIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper, String msg) throws Exception;

    boolean updateIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper) throws Exception;

    /**
     * 幂等性新增或更新记录
     * 例子如下：
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper, String msg) throws Exception;

    boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, QueryWrapper<T> countWrapper) throws Exception;
}
