package com.content.user.security.service;

import com.content.common.exception.BusinessException;
import com.content.framework.security.LoginUser;
import com.content.framework.security.entity.Role;
import com.content.user.entity.Permission;
import com.content.user.entity.User;
import com.content.user.mapper.PermissionMapper;
import com.content.user.mapper.RoleMapper;
import com.content.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            log.warn("登录用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            log.warn("登录用户已被禁用: {}", username);
            throw new BusinessException("用户已被禁用");
        }

        if (user.getDeleted() != null && user.getDeleted() == 1) {
            log.warn("登录用户已被删除: {}", username);
            throw new BusinessException("用户已被删除");
        }

        LoginUser loginUser = buildLoginUser(user);
        loadUserRolesAndPermissions(loginUser);

        return loginUser;
    }

    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setNickname(user.getNickname());
        loginUser.setEmail(user.getEmail());
        loginUser.setPhone(user.getPhone());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setGender(user.getGender());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserType(user.getType());
        return loginUser;
    }

    private void loadUserRolesAndPermissions(LoginUser loginUser) {
        Long userId = loginUser.getUserId();

        if (loginUser.isAdmin()) {
            Set<String> allPermissions = permissionMapper.selectAllEnabled()
                .stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
            loginUser.setPermissions(allPermissions);
            loginUser.setRoleCodes(Set.of("ADMIN", "SUPER_ADMIN"));
            loginUser.setAuthorities(buildAuthorities(Set.of("ADMIN", "SUPER_ADMIN"), allPermissions));
            return;
        }

        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        Set<Role> roleSet = new HashSet<>(roles);
        Set<String> roleCodes = roles.stream()
            .map(Role::getCode)
            .collect(Collectors.toSet());

        loginUser.setRoles(roleSet);
        loginUser.setRoleCodes(roleCodes);

        List<Permission> permissions = permissionMapper.selectPermissionsByUserId(userId);
        Set<String> permissionCodes = permissions.stream()
            .map(Permission::getCode)
            .collect(Collectors.toSet());

        loginUser.setPermissions(permissionCodes);
        loginUser.setAuthorities(buildAuthorities(roleCodes, permissionCodes));

        log.debug("用户 {} 加载角色: {}, 权限: {}", 
            loginUser.getUsername(), roleCodes, permissionCodes.size());
    }

    private Set<GrantedAuthority> buildAuthorities(Set<String> roleCodes, Set<String> permissions) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        for (String roleCode : roleCodes) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
        }
        
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        
        return authorities;
    }
}
