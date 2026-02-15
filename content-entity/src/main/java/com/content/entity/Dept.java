package com.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门实体类
 */
@Data
@TableName("sys_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private String ancestors;

    private String leader;

    private String phone;

    private String email;

    private Integer sort;

    private Integer status;

    private Integer deleted;

    private Long tenantId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Dept> children;
}
