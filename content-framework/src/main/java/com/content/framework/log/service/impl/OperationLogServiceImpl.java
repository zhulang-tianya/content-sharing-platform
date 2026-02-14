package com.content.framework.log.service.impl;

import com.content.framework.log.entity.OperationLog;
import com.content.framework.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Async
    public void save(OperationLog operationLog) {
        try {
            String sql = "INSERT INTO sys_operation_log (title, business_type, operator_type, method, " +
                    "request_method, operator, oper_ip, oper_uri, oper_param, json_result, status, " +
                    "error_msg, cost_time, oper_time, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    operationLog.getTitle(),
                    operationLog.getBusinessType(),
                    operationLog.getOperatorType(),
                    operationLog.getMethod(),
                    operationLog.getRequestMethod(),
                    operationLog.getOperator(),
                    operationLog.getOperIp(),
                    operationLog.getOperUri(),
                    operationLog.getOperParam(),
                    operationLog.getJsonResult(),
                    operationLog.getStatus(),
                    operationLog.getErrorMsg(),
                    operationLog.getCostTime(),
                    operationLog.getOperTime(),
                    operationLog.getTenantId()
            );
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }

    @Override
    public OperationLog getById(Long id) {
        String sql = "SELECT * FROM sys_operation_log WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            OperationLog log = new OperationLog();
            log.setId(rs.getLong("id"));
            log.setTitle(rs.getString("title"));
            log.setBusinessType(rs.getString("business_type"));
            log.setOperatorType(rs.getString("operator_type"));
            log.setMethod(rs.getString("method"));
            log.setRequestMethod(rs.getString("request_method"));
            log.setOperator(rs.getString("operator"));
            log.setOperIp(rs.getString("oper_ip"));
            log.setOperUri(rs.getString("oper_uri"));
            log.setOperParam(rs.getString("oper_param"));
            log.setJsonResult(rs.getString("json_result"));
            log.setStatus(rs.getInt("status"));
            log.setErrorMsg(rs.getString("error_msg"));
            log.setCostTime(rs.getLong("cost_time"));
            log.setOperTime(rs.getTimestamp("oper_time").toLocalDateTime());
            log.setTenantId(rs.getLong("tenant_id"));
            return log;
        }, id);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_operation_log WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
