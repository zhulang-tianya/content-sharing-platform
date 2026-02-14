package com.content.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.framework.security.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    /**
     * 根据角色ID查询角色详情
     * 
     * @param roleId 角色ID
     * @return 角色详情
     */
    Role selectRoleById(@Param("roleId") Long roleId);
    
    /**
     * 根据角色编码查询角色
     * 
     * @param code 角色编码
     * @return 角色详情
     */
    Role selectRoleByCode(@Param("code") String code);
    
    /**
     * 根据用户ID查询角色列表
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolesByUserId(@Param("userId") Long userId);
}
