package com.content.user.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限查询条件
 */
@Data
public class PermissionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pageNum = 1L;
    private Long pageSize = 10L;
    private String name;
    private String code;
    private Integer type;
    private Integer status;
    private Long parentId;
    private Long tenantId;
}
