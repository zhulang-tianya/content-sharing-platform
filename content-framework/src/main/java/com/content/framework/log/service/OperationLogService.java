package com.content.framework.log.service;

import com.content.framework.log.entity.OperationLog;

/**
 * 操作日志服务接口
 */
public interface OperationLogService {

    /**
     * 保存操作日志
     */
    void save(OperationLog operationLog);

    /**
     * 根据ID查询操作日志
     */
    OperationLog getById(Long id);

    /**
     * 删除操作日志
     */
    boolean delete(Long id);
}
