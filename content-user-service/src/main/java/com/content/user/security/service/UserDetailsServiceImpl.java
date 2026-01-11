package com.content.user.security.service;

import com.content.common.exception.BusinessException;
import com.content.framework.security.LoginUser;
import com.content.user.entity.User;
import com.content.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("用户已禁用");
        }

        // 获取用户权限
        Set<String> permissions = new HashSet<>();
        // 这里可以根据用户角色查询对应的权限
        // 暂时添加默认权限
        if (user.getType() == 1) {
            permissions.add("admin");
        }
        permissions.add("user:view");

        // 构建权限 authorities
        Set<GrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        // 构建登录用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setUserid(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setPermissions(permissions);
        loginUser.setAuthorities(authorities);

        return loginUser;
    }
}
