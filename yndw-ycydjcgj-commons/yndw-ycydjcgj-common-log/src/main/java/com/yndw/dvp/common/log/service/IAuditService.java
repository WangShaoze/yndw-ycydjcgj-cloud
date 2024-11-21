package com.yndw.dvp.common.log.service;

import com.yndw.dvp.common.log.model.Audit;

/**
 * 审计日志接口
 *
 * 
 * @date 2020/2/3
 * <p>
 * 
 * 
 */
public interface IAuditService {
    void save(Audit audit);
}
