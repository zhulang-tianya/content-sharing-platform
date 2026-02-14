package com.content.user.service;

import com.content.user.entity.Menu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {

    /**
     * 根据ID查询菜单
     */
    Menu getById(Long id);

    /**
     * 根据用户ID查询菜单列表
     */
    List<Menu> listByUserId(Long userId);

    /**
     * 查询所有菜单
     */
    List<Menu> listAll();

    /**
     * 查询菜单树
     */
    List<Menu> treeMenus();

    /**
     * 根据用户ID查询路由菜单
     */
    List<Menu> getRouters(Long userId);

    /**
     * 新增菜单
     */
    boolean save(Menu menu);

    /**
     * 更新菜单
     */
    boolean update(Menu menu);

    /**
     * 删除菜单
     */
    boolean delete(Long id);

    /**
     * 修改菜单状态
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 根据角色ID获取菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 分配角色菜单
     */
    boolean assignRoleMenus(Long roleId, List<Long> menuIds);
}
