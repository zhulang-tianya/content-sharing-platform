package com.content.framework.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Role implements Serializable {

    private Long id;
    
    private String name;
    
    private String code;
    
    private String description;
    
    private Integer sort;
    
    private Boolean status;
    
    private Boolean deleted;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private String createBy;
    
    private String updateBy;
    
    private static final long serialVersionUID = 1L;
}
