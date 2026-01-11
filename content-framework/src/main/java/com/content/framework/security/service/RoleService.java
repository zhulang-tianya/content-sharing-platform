package com.content.framework.security.service;

import com.content.common.result.Result;
import com.content.framework.security.entity.Role;
import com.content.framework.security.entity.UserRole;

import java.util.List;

public interface RoleService {

    Result<List<Role>> list();

    Result<Role> getById(Long id);

    Result<List<com.content.framework.security.entity.Permission>> getRolePermissions(Long roleId);

    Result<Void> assignPermissions(Long roleId, List<Long> permissionIds);

    Result<List<Role>> getUserRoles(Long userId);

    Result<Void> assignRoles(Long userId, List<Long> roleIds);

    Result<Void> create(Role role);

    Result<Void> update(Role role);

    Result<Void> delete(Long id);

    Result<Void> deleteBatch(List<Long> ids);

    Result<Void> enable(Long id);

    Result<Void> disable(Long id);
}
