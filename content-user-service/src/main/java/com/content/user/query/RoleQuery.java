package com.content.user.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色查询条件
 */
@Data
public class RoleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long pageNum = 1L;

    /**
     * 每页大小
     */
    private Long pageSize = 10L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;
}
