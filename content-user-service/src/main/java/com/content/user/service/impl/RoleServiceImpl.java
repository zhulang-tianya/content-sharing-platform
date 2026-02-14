package com.content.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.exception.BusinessException;
import com.content.common.utils.StringUtils;
import com.content.framework.security.entity.Role;
import com.content.user.entity.Permission;
import com.content.user.mapper.PermissionMapper;
import com.content.user.mapper.RoleMapper;
import com.content.user.query.RoleQuery;
import com.content.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role getByCode(String code) {
        return roleMapper.selectByCode(code);
    }

    @Override
    public Page<Role> pageRoles(RoleQuery query) {
        Page<Role> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getName()), 
                Role::getName, query.getName())
            .like(StringUtils.isNotBlank(query.getCode()), 
                Role::getCode, query.getCode())
            .eq(query.getStatus() != null, Role::getStatus, query.getStatus())
            .eq(query.getTenantId() != null, Role::getTenantId, query.getTenantId())
            .eq(Role::getDeleted, 0)
            .orderByAsc(Role::getSort);
        return roleMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Role> listAllEnabled() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus, 1)
            .eq(Role::getDeleted, 0)
            .orderByAsc(Role::getSort);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public List<Role> listByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Role role) {
        if (checkCodeExists(role.getCode(), role.getTenantId())) {
            throw new BusinessException("角色编码已存在");
        }
        role.setDeleted(0);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.insert(role) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Role role) {
        Role existing = roleMapper.selectById(role.getId());
        if (existing == null) {
            throw new BusinessException("角色不存在");
        }
        if (!existing.getCode().equals(role.getCode()) && 
            checkCodeExists(role.getCode(), role.getTenantId())) {
            throw new BusinessException("角色编码已存在");
        }
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        role.setDeleted(1);
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        role.setStatus(status);
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        
        jdbcTemplate.update("DELETE FROM sys_role_permission WHERE role_id = ?", roleId);
        
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<Object[]> batchArgs = new ArrayList<>();
            for (Long permissionId : permissionIds) {
                batchArgs.add(new Object[]{roleId, permissionId});
            }
            jdbcTemplate.batchUpdate(
                "INSERT INTO sys_role_permission (role_id, permission_id) VALUES (?, ?)",
                batchArgs
            );
        }
        
        log.info("分配角色权限成功，roleId={}，permissionIds={}", roleId, permissionIds);
        return true;
    }

    @Override
    public List<Long> getPermissionIds(Long roleId) {
        List<Permission> permissions = permissionMapper.selectPermissionsByRoleId(roleId);
        return permissions.stream()
            .map(Permission::getId)
            .toList();
    }

    @Override
    public boolean checkCodeExists(String code, Long tenantId) {
        return roleMapper.checkCodeExists(code, tenantId) > 0;
    }
}
