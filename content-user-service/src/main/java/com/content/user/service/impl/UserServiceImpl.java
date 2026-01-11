package com.content.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.user.entity.User;
import com.content.user.mapper.UserMapper;
import com.content.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * 负责用户相关的业务逻辑实现
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getByUsername(String username) {
        log.debug("根据用户名获取用户信息: {}", username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = baseMapper.selectOne(queryWrapper);
        log.debug("根据用户名获取用户信息结果: {}", user);
        return user;
    }

    /**
     * 根据邮箱获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    @Override
    public User getByEmail(String email) {
        log.debug("根据邮箱获取用户信息: {}", email);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = baseMapper.selectOne(queryWrapper);
        log.debug("根据邮箱获取用户信息结果: {}", user);
        return user;
    }

    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    @Override
    public User getByPhone(String phone) {
        log.debug("根据手机号获取用户信息: {}", phone);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = baseMapper.selectOne(queryWrapper);
        log.debug("根据手机号获取用户信息结果: {}", user);
        return user;
    }
}