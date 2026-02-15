package com.content.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色列表
     */
    @Select("SELECT r.* FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.status = 1 AND r.deleted = 0")
    List<Role> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据角色编码查询角色
     */
    @Select("SELECT * FROM sys_role WHERE code = #{code} AND deleted = 0")
    Role selectByCode(@Param("code") String code);

    /**
     * 检查角色编码是否存在
     */
    @Select("SELECT COUNT(1) FROM sys_role WHERE code = #{code} AND tenant_id = #{tenantId} AND deleted = 0")
    int checkCodeExists(@Param("code") String code, @Param("tenantId") Long tenantId);
}
