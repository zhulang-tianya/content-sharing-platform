package com.content.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.content.user.entity.Role;
import com.content.user.query.RoleQuery;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色
     */
    Role getById(Long id);

    /**
     * 根据编码查询角色
     *
     * @param code 角色编码
     * @return 角色
     */
    Role getByCode(String code);

    /**
     * 分页查询角色列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<Role> pageRoles(RoleQuery query);

    /**
     * 查询所有启用的角色
     *
     * @return 角色列表
     */
    List<Role> listAllEnabled();

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> listByUserId(Long userId);

    /**
     * 新增角色
     *
     * @param role 角色信息
     * @return 是否成功
     */
    boolean save(Role role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return 是否成功
     */
    boolean update(Role role);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID列表
     * @return 是否成功
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * 修改角色状态
     *
     * @param id     角色ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 分配角色权限
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return 是否成功
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 获取角色权限ID列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getPermissionIds(Long roleId);

    /**
     * 检查角色编码是否存在
     *
     * @param code     角色编码
     * @param tenantId 租户ID
     * @return 是否存在
     */
    boolean checkCodeExists(String code, Long tenantId);
}
