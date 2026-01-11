package com.content.framework.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserRole implements Serializable {

    private Long id;
    
    private Long userId;
    
    private Long roleId;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private static final long serialVersionUID = 1L;
}
