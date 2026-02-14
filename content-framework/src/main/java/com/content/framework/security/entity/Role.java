package com.content.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色实体类
 */
@Data
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String name;
    
    private String code;
    
    private String description;
    
    private Long parentId;
    
    private Integer sort;
    
    private Integer status;
    
    private Integer deleted;

    private String dataScope;
    
    private Long tenantId;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private String createBy;
    
    private String updateBy;
}
