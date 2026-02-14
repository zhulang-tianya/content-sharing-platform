package com.content.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.user.entity.Permission;
import com.content.user.query.PermissionQuery;

import java.util.List;

/**
 * 权限服务接口
 */
public interface PermissionService {

    /**
     * 根据ID查询权限
     */
    Permission getById(Long id);

    /**
     * 根据编码查询权限
     */
    Permission getByCode(String code);

    /**
     * 分页查询权限列表
     */
    Page<Permission> pagePermissions(PermissionQuery query);

    /**
     * 查询所有启用的权限
     */
    List<Permission> listAllEnabled();

    /**
     * 根据用户ID查询权限列表
     */
    List<Permission> listByUserId(Long userId);

    /**
     * 根据角色ID查询权限列表
     */
    List<Permission> listByRoleId(Long roleId);

    /**
     * 查询权限树
     */
    List<Permission> treePermissions();

    /**
     * 新增权限
     */
    boolean save(Permission permission);

    /**
     * 更新权限
     */
    boolean update(Permission permission);

    /**
     * 删除权限
     */
    boolean delete(Long id);

    /**
     * 批量删除权限
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * 修改权限状态
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 检查权限编码是否存在
     */
    boolean checkCodeExists(String code, Long tenantId);
}
