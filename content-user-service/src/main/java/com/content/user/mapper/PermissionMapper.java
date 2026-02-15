package com.content.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限Mapper接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Select("SELECT DISTINCT p.* FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN sys_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND p.status = 1")
    List<Permission> selectPermissionsByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Select("SELECT p.* FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId} AND p.status = 1")
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据权限编码查询权限
     *
     * @param code 权限编码
     * @return 权限
     */
    @Select("SELECT * FROM sys_permission WHERE code = #{code} AND status = 1")
    Permission selectByCode(@Param("code") String code);

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @param tenantId 租户ID
     * @return 是否存在
     */
    @Select("SELECT COUNT(1) FROM sys_permission WHERE code = #{code} AND tenant_id = #{tenantId} AND status = 1")
    int checkCodeExists(@Param("code") String code, @Param("tenantId") Long tenantId);

    /**
     * 查询所有启用的权限列表
     *
     * @return 权限列表
     */
    @Select("SELECT * FROM sys_permission WHERE status = 1 ORDER BY sort")
    List<Permission> selectAllEnabled();
}
