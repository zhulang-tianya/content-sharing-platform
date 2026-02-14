package com.content.user.service.impl;

import com.content.common.exception.BusinessException;
import com.content.entity.Menu;
import com.content.user.mapper.MenuMapper;
import com.content.user.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<Menu> listByUserId(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<Menu> listAll() {
        return menuMapper.selectAllMenus();
    }

    @Override
    public List<Menu> treeMenus() {
        List<Menu> allMenus = listAll();
        return buildMenuTree(allMenus, 0L);
    }

    @Override
    public List<Menu> getRouters(Long userId) {
        List<Menu> menus = userId == 1L ? listAll() : listByUserId(userId);
        return buildMenuTree(menus.stream()
            .filter(m -> m.getType() != 3)
            .toList(), 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Menu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        return menuMapper.insert(menu) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Menu menu) {
        Menu existing = menuMapper.selectById(menu.getId());
        if (existing == null) {
            throw new BusinessException("菜单不存在");
        }
        menu.setUpdateTime(LocalDateTime.now());
        return menuMapper.updateById(menu) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }
        return menuMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }
        menu.setStatus(status);
        menu.setUpdateTime(LocalDateTime.now());
        return menuMapper.updateById(menu) > 0;
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return menuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRoleMenus(Long roleId, List<Long> menuIds) {
        jdbcTemplate.update("DELETE FROM sys_role_menu WHERE role_id = ?", roleId);
        
        if (menuIds != null && !menuIds.isEmpty()) {
            List<Object[]> batchArgs = new ArrayList<>();
            for (Long menuId : menuIds) {
                batchArgs.add(new Object[]{roleId, menuId});
            }
            jdbcTemplate.batchUpdate(
                "INSERT INTO sys_role_menu (role_id, menu_id) VALUES (?, ?)",
                batchArgs
            );
        }
        
        log.info("分配角色菜单成功，roleId={}，menuIds={}", roleId, menuIds);
        return true;
    }

    /**
     * 构建菜单树
     */
    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> result = new ArrayList<>();
        Map<Long, List<Menu>> groupMap = menus.stream()
            .collect(Collectors.groupingBy(Menu::getParentId));
        
        List<Menu> children = groupMap.get(parentId);
        if (children == null) {
            return result;
        }
        
        for (Menu menu : children) {
            menu.setChildren(buildMenuTree(menus, menu.getId()));
            result.add(menu);
        }
        return result;
    }
}
