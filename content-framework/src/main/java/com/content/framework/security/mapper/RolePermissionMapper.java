package com.content.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联Mapper
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    
    /**
     * 根据角色ID查询权限ID列表
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据权限ID查询角色ID列表
     * 
     * @param permissionId 权限ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByPermissionId(@Param("permissionId") Long permissionId);
    
    /**
     * 批量删除角色权限关联
     * 
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 删除数量
     */
    int deleteBatchByRoleIdAndPermissionIds(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
