package com.content.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    
    /**
     * 根据权限ID查询权限详情
     * 
     * @param permissionId 权限ID
     * @return 权限详情
     */
    Permission selectPermissionById(@Param("permissionId") Long permissionId);
    
    /**
     * 根据权限编码查询权限
     * 
     * @param code 权限编码
     * @return 权限详情
     */
    Permission selectPermissionByCode(@Param("code") String code);
    
    /**
     * 根据角色ID查询权限列表
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据用户ID查询权限列表
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUserId(@Param("userId") Long userId);
}
