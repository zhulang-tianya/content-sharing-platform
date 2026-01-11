package com.content.framework.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Permission implements Serializable {

    private Long id;
    
    private String name;
    
    private String code;
    
    private String path;
    
    private String method;
    
    private String description;
    
    private Long parentId;
    
    private Integer sort;
    
    private Integer type;
    
    private String icon;
    
    private String component;
    
    private String redirect;
    
    private Boolean hidden;
    
    private Boolean alwaysShow;
    
    private String permission;
    
    private String role;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private String createBy;
    
    private String updateBy;
    
    private static final long serialVersionUID = 1L;
}
