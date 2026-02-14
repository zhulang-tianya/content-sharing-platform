package com.content.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.framework.security.entity.Role;
import com.content.user.query.RoleQuery;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

    Role getById(Long id);

    Role getByCode(String code);

    Page<Role> pageRoles(RoleQuery query);

    List<Role> listAllEnabled();

    List<Role> listByUserId(Long userId);

    boolean save(Role role);

    boolean update(Role role);

    boolean delete(Long id);

    boolean deleteBatch(List<Long> ids);

    boolean updateStatus(Long id, Integer status);

    boolean assignPermissions(Long roleId, List<Long> permissionIds);

    List<Long> getPermissionIds(Long roleId);

    boolean checkCodeExists(String code, Long tenantId);
}
