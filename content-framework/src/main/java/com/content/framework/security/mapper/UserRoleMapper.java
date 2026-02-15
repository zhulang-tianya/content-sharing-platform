package com.content.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联Mapper
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    
    /**
     * 根据用户ID查询角色ID列表
     * 
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID查询用户ID列表
     * 
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> selectUserIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 批量删除用户角色关联
     * 
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 删除数量
     */
    int deleteBatchByUserIdAndRoleIds(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
