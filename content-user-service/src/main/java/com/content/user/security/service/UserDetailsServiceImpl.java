package com.content.user.security.service;

import com.content.common.exception.BusinessException;
import com.content.framework.security.LoginUser;
import com.content.entity.Role;
import com.content.entity.Permission;
import com.content.entity.User;
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

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现
 */
@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("加载用户详情: {}", username);
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            log.warn("登录用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        log.info("用户信息: id={}, username={}, password={}, status={}, deleted={}", 
                user.getId(), user.getUsername(), user.getPassword().substring(0, 20) + "...", 
                user.getStatus(), user.getDeleted());

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

        log.info("用户详情加载完成: {}, authorities.size={}", username, loginUser.getAuthorities().size());
        return loginUser;
    }

    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword()); // 直接设置数据库中的BCrypt密码
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
            try {
                List<Permission> allPermissions = permissionMapper.selectAllEnabled();
                Set<String> permissionCodes = allPermissions != null ?
                        allPermissions.stream().map(Permission::getCode).collect(Collectors.toSet()) :
                        new HashSet<>();
                loginUser.setPermissions(permissionCodes);
                loginUser.setRoleCodes(Set.of("ADMIN", "SUPER_ADMIN"));
                loginUser.setAuthorities(buildAuthorities(Set.of("ADMIN", "SUPER_ADMIN"), permissionCodes));
            } catch (Exception e) {
                log.error("超级管理员加载权限失败: {}", e.getMessage());
                // 降级处理，确保登录成功
                loginUser.setPermissions(new HashSet<>());
                loginUser.setRoleCodes(Set.of("ADMIN"));
                loginUser.setAuthorities(buildAuthorities(Set.of("ADMIN"), new HashSet<>()));
            }
            return;
        }

        try {
            List<Role> roles = roleMapper.selectRolesByUserId(userId);
            Set<Role> roleSet = roles != null ? new HashSet<>(roles) : new HashSet<>();
            Set<String> roleCodes = roles != null ?
                    roles.stream().map(Role::getCode).collect(Collectors.toSet()) :
                    new HashSet<>();

            loginUser.setRoles(roleSet);
            loginUser.setRoleCodes(roleCodes);

            List<Permission> permissions = permissionMapper.selectPermissionsByUserId(userId);
            Set<String> permissionCodes = permissions != null ?
                    permissions.stream().map(Permission::getCode).collect(Collectors.toSet()) :
                    new HashSet<>();

            loginUser.setPermissions(permissionCodes);
            loginUser.setAuthorities(buildAuthorities(roleCodes, permissionCodes));

            log.debug("用户 {} 加载角色: {}, 权限: {}", 
                loginUser.getUsername(), roleCodes, permissionCodes.size());
        } catch (Exception e) {
            log.error("加载用户角色和权限失败: {}", e.getMessage());
            // 降级处理，确保登录成功
            loginUser.setRoles(new HashSet<>());
            loginUser.setRoleCodes(new HashSet<>());
            loginUser.setPermissions(new HashSet<>());
            loginUser.setAuthorities(new HashSet<>());
        }
    }

    private Set<GrantedAuthority> buildAuthorities(Set<String> roleCodes, Set<String> permissions) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        if (roleCodes != null) {
            for (String roleCode : roleCodes) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
            }
        }
        
        if (permissions != null) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        
        return authorities;
    }
}
