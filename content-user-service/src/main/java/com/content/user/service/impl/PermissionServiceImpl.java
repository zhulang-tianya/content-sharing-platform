package com.content.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.common.exception.BusinessException;
import com.content.common.utils.StringUtils;
import com.content.user.entity.Permission;
import com.content.user.mapper.PermissionMapper;
import com.content.user.query.PermissionQuery;
import com.content.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public Permission getById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public Permission getByCode(String code) {
        return permissionMapper.selectByCode(code);
    }

    @Override
    public Page<Permission> pagePermissions(PermissionQuery query) {
        Page<Permission> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getName()), 
                Permission::getName, query.getName())
            .like(StringUtils.isNotBlank(query.getCode()), 
                Permission::getCode, query.getCode())
            .eq(query.getType() != null, Permission::getType, query.getType())
            .eq(query.getStatus() != null, Permission::getStatus, query.getStatus())
            .eq(query.getParentId() != null, Permission::getParentId, query.getParentId())
            .eq(query.getTenantId() != null, Permission::getTenantId, query.getTenantId())
            .orderByAsc(Permission::getSort);
        return permissionMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Permission> listAllEnabled() {
        return permissionMapper.selectAllEnabled();
    }

    @Override
    public List<Permission> listByUserId(Long userId) {
        return permissionMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public List<Permission> listByRoleId(Long roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permission> treePermissions() {
        List<Permission> allPermissions = listAllEnabled();
        return buildTree(allPermissions, 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Permission permission) {
        if (checkCodeExists(permission.getCode(), permission.getTenantId())) {
            throw new BusinessException("权限编码已存在");
        }
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        return permissionMapper.insert(permission) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Permission permission) {
        Permission existing = permissionMapper.selectById(permission.getId());
        if (existing == null) {
            throw new BusinessException("权限不存在");
        }
        if (!existing.getCode().equals(permission.getCode()) && 
            checkCodeExists(permission.getCode(), permission.getTenantId())) {
            throw new BusinessException("权限编码已存在");
        }
        permission.setUpdateTime(LocalDateTime.now());
        return permissionMapper.updateById(permission) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission == null) {
            throw new BusinessException("权限不存在");
        }
        return permissionMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        return permissionMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        Permission permission = permissionMapper.selectById(id);
        if (permission == null) {
            throw new BusinessException("权限不存在");
        }
        permission.setStatus(status);
        permission.setUpdateTime(LocalDateTime.now());
        return permissionMapper.updateById(permission) > 0;
    }

    @Override
    public boolean checkCodeExists(String code, Long tenantId) {
        return permissionMapper.checkCodeExists(code, tenantId) > 0;
    }

    /**
     * 构建权限树
     */
    private List<Permission> buildTree(List<Permission> permissions, Long parentId) {
        List<Permission> result = new ArrayList<>();
        Map<Long, List<Permission>> groupMap = permissions.stream()
            .collect(Collectors.groupingBy(Permission::getParentId));
        
        List<Permission> children = groupMap.get(parentId);
        if (children == null) {
            return result;
        }
        
        for (Permission permission : children) {
            result.add(permission);
        }
        return result;
    }
}
