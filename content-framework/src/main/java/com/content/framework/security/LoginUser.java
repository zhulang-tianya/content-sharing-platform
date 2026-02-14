package com.content.framework.security;

import com.content.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 */
@Data
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户类型（0普通用户 1管理员）
     */
    private Integer userType;

    /**
     * 角色列表
     */
    private Set<Role> roles;

    /**
     * 角色编码列表
     */
    private Set<String> roleCodes;

    /**
     * 权限编码列表
     */
    private Set<String> permissions;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * Token
     */
    private String token;

    /**
     * 权限列表
     */
    private Collection<GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    /**
     * 判断是否为管理员
     */
    @JsonIgnore
    public boolean isAdmin() {
        return userId != null && userId == 1L;
    }

    /**
     * 判断是否拥有某个角色
     */
    @JsonIgnore
    public boolean hasRole(String roleCode) {
        if (isAdmin()) {
            return true;
        }
        return roleCodes != null && roleCodes.contains(roleCode);
    }

    /**
     * 判断是否拥有某个权限
     */
    @JsonIgnore
    public boolean hasPermission(String permission) {
        if (isAdmin()) {
            return true;
        }
        return permissions != null && permissions.contains(permission);
    }

    /**
     * 构造函数
     */
    public LoginUser() {
    }

    /**
     * 简化构造函数
     */
    public LoginUser(Long userId, String username, String password, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
