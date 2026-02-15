package com.content.framework.security.service;

import com.content.common.exception.AuthException;
import com.content.common.exception.ParamException;
import com.content.common.result.Result;
import com.content.framework.security.LoginUser;
import com.content.framework.security.SecurityUtils;
import com.content.framework.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Result<Map<String, Object>> login(String username, String password) {
        if (SecurityUtils.isEmpty(username) || SecurityUtils.isEmpty(password)) {
            throw new ParamException("用户名或密码不能为空");
        }

        try {
            log.info("用户登录: {}", username);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            log.info("认证成功: {}", username);
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            log.info("获取LoginUser: {}, userId: {}", loginUser.getUsername(), loginUser.getUserId());
            String token = jwtUtils.generateToken(loginUser.getUsername(), loginUser.getUserId());
            log.info("生成token: {}", token.substring(0, 20) + "...");
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", loginUser);
            log.info("登录成功: {}", username);
            return Result.success(result);
        } catch (AuthenticationException e) {
            log.error("认证失败: {}, 原因: {}", username, e.getMessage());
            throw new AuthException("用户名或密码错误");
        } catch (Exception e) {
            log.error("登录失败: {}, 原因: {}", username, e.getMessage(), e);
            throw new AuthException("登录失败: " + e.getMessage());
        }
    }

    public Result<Void> logout() {
        // 在实际应用中，这里可以将token加入黑名单
        return Result.success();
    }

    public Result<LoginUser> getProfile() {
        String username = SecurityUtils.getUsername();
        if (SecurityUtils.isEmpty(username)) {
            throw new AuthException("Unauthorized");
        }
        // 这里可以从数据库加载用户的详细信息
        LoginUser loginUser = new LoginUser(
                SecurityUtils.getUserId(),
                username,
                null,
                new ArrayList<>()
        );
        return Result.success(loginUser);
    }

    public Result<String> refreshToken(String refreshToken) {
        if (SecurityUtils.isEmpty(refreshToken)) {
            throw new ParamException("刷新令牌不能为空");
        }
        // 在实际应用中，这里需要验证refreshToken的有效性
        String username = jwtUtils.getUsernameFromToken(refreshToken);
        if (SecurityUtils.isEmpty(username)) {
            throw new AuthException("刷新令牌无效");
        }
        String newToken = jwtUtils.generateToken(username, jwtUtils.getUserIdFromToken(refreshToken));
        return Result.success(newToken);
    }
}
