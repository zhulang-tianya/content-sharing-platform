package com.content.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_role_permission")
public class RolePermission implements Serializable {

    private Long id;
    
    private Long roleId;
    
    private Long permissionId;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private static final long serialVersionUID = 1L;
}
