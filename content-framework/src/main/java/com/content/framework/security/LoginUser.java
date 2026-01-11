package com.content.framework.security;

import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class LoginUser implements org.springframework.security.core.userdetails.UserDetails {

    private Long userid;

    private String username;

    private String password;

    private Set<String> permissions;

    private Collection<org.springframework.security.core.GrantedAuthority> authorities;

    public LoginUser() {
    }

    public LoginUser(Long userid, String username, String password, Collection<org.springframework.security.core.GrantedAuthority> authorities) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 获取用户ID
     */
    public Long getUserId() {
        return userid;
    }
}
