package com.content.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限实体类
 */
@Data
@TableName("sys_permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限类型：2-按钮，3-接口
     */
    private Integer type;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
}