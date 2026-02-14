-- RBAC权限管理系统数据库表结构
-- 数据库名称配置（单数据库模式）
-- 所有服务使用同一个数据库，便于维护和管理
CREATE DATABASE IF NOT EXISTS `content_sharing_platform` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `content_sharing_platform`;

-- 表前缀策略：
-- 1. 系统管理相关表使用 sys_ 前缀
-- 2. 用户服务相关表使用 user_ 前缀
-- 3. 内容服务相关表使用 content_ 前缀
-- 4. 其他服务按服务名使用相应前缀

-- 租户表
CREATE TABLE `sys_tenant` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '租户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '租户名称',
  `code` VARCHAR(50) NOT NULL COMMENT '租户编码',
  `description` VARCHAR(255) COMMENT '租户描述',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `expire_time` DATETIME COMMENT '过期时间',
  `creator_id` BIGINT COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_tenant_code` (`code`),
  KEY `idx_sys_tenant_status` (`status`),
  KEY `idx_sys_tenant_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- 角色表
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(255) COMMENT '角色描述',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父角色ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志：1-已删除，0-正常',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_code_tenant` (`code`, `tenant_id`),
  KEY `idx_sys_role_parent_id` (`parent_id`),
  KEY `idx_sys_role_status` (`status`),
  KEY `idx_sys_role_deleted` (`deleted`),
  KEY `idx_sys_role_tenant_id` (`tenant_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_role_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE `sys_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `code` VARCHAR(50) NOT NULL COMMENT '权限编码',
  `path` VARCHAR(255) COMMENT '请求路径',
  `method` VARCHAR(10) COMMENT '请求方法',
  `description` VARCHAR(255) COMMENT '权限描述',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父权限ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `type` TINYINT DEFAULT 3 COMMENT '权限类型：2-按钮，3-接口',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_permission_code_tenant` (`code`, `tenant_id`),
  KEY `idx_sys_permission_parent_id` (`parent_id`),
  KEY `idx_sys_permission_type` (`type`),
  KEY `idx_sys_permission_path_method` (`path`, `method`),
  KEY `idx_sys_permission_status` (`status`),
  KEY `idx_sys_permission_tenant_id` (`tenant_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_permission_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 菜单表
CREATE TABLE `sys_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `code` VARCHAR(50) NOT NULL COMMENT '菜单编码',
  `path` VARCHAR(255) COMMENT '路由路径',
  `component` VARCHAR(255) COMMENT '组件路径',
  `redirect` VARCHAR(255) COMMENT '重定向路径',
  `icon` VARCHAR(50) COMMENT '菜单图标',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `type` TINYINT DEFAULT 1 COMMENT '菜单类型：1-目录，2-菜单，3-按钮',
  `visible` TINYINT DEFAULT 1 COMMENT '是否显示：1-显示，0-隐藏',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_menu_code_tenant` (`code`, `tenant_id`),
  KEY `idx_sys_menu_parent_id` (`parent_id`),
  KEY `idx_sys_menu_type` (`type`),
  KEY `idx_sys_menu_visible` (`visible`),
  KEY `idx_sys_menu_status` (`status`),
  KEY `idx_sys_menu_tenant_id` (`tenant_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_menu_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_user_role` (`user_id`, `role_id`),
  KEY `idx_sys_user_role_user_id` (`user_id`),
  KEY `idx_sys_user_role_role_id` (`role_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `sys_role_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_permission` (`role_id`, `permission_id`),
  KEY `idx_sys_role_permission_role_id` (`role_id`),
  KEY `idx_sys_role_permission_permission_id` (`permission_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 菜单与权限关联表
CREATE TABLE `sys_menu_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_menu_permission` (`menu_id`, `permission_id`),
  KEY `idx_sys_menu_permission_menu_id` (`menu_id`),
  KEY `idx_sys_menu_permission_permission_id` (`permission_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_menu_permission_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_menu_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单与权限关联表';

-- 角色与菜单关联表
CREATE TABLE `sys_role_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_menu` (`role_id`, `menu_id`),
  KEY `idx_sys_role_menu_role_id` (`role_id`),
  KEY `idx_sys_role_menu_menu_id` (`menu_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_role_menu_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_menu_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单关联表';

-- 部门表
CREATE TABLE `sys_dept` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` VARCHAR(50) NOT NULL COMMENT '部门名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父部门ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `leader` VARCHAR(50) COMMENT '部门负责人',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `email` VARCHAR(50) COMMENT '邮箱',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志：1-已删除，0-正常',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_sys_dept_parent_id` (`parent_id`),
  KEY `idx_sys_dept_status` (`status`),
  KEY `idx_sys_dept_deleted` (`deleted`),
  KEY `idx_sys_dept_tenant_id` (`tenant_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_dept_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 用户表
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `email` VARCHAR(50) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `avatar` VARCHAR(255) COMMENT '头像',
  `dept_id` BIGINT COMMENT '部门ID',
  `type` TINYINT DEFAULT 0 COMMENT '用户类型：0-普通用户，1-管理员',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志：1-已删除，0-正常',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_user_username_tenant` (`username`, `tenant_id`),
  KEY `idx_sys_user_dept_id` (`dept_id`),
  KEY `idx_sys_user_type` (`type`),
  KEY `idx_sys_user_status` (`status`),
  KEY `idx_sys_user_deleted` (`deleted`),
  KEY `idx_sys_user_tenant_id` (`tenant_id`),
  -- 外键约束
  CONSTRAINT `fk_sys_user_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 初始化基础数据
-- 0. 初始化默认租户
INSERT INTO `sys_tenant` (`name`, `code`, `description`, `status`) VALUES 
('默认租户', 'DEFAULT', '系统默认租户', 1);

-- 1. 初始化默认部门
INSERT INTO `sys_dept` (`name`, `parent_id`, `sort`, `status`, `tenant_id`) VALUES 
('总公司', 0, 1, 1, 1),
('技术部', 1, 2, 1, 1),
('运营部', 1, 3, 1, 1),
('市场部', 1, 4, 1, 1);

-- 2. 初始化默认角色
INSERT INTO `sys_role` (`name`, `code`, `description`, `status`, `tenant_id`) VALUES 
('超级管理员', 'ADMIN', '系统超级管理员，拥有所有权限', 1, 1),
('普通用户', 'USER', '普通用户，拥有基础权限', 1, 1),
('部门管理员', 'DEPT_ADMIN', '部门管理员，拥有部门内用户管理权限', 1, 1);

-- 3. 初始化菜单数据
INSERT INTO `sys_menu` (`name`, `code`, `path`, `component`, `icon`, `parent_id`, `sort`, `type`, `visible`, `status`, `tenant_id`) VALUES 
-- 系统管理目录
('系统管理', 'system', '/system', NULL, 'setting', 0, 1, 1, 1, 1, 1),
  -- 用户管理菜单
  ('用户管理', 'user', '/system/user', 'system/user/index', 'user', 1, 1, 2, 1, 1, 1),
    -- 用户管理按钮
    ('用户查看', 'user:view', NULL, NULL, NULL, 2, 1, 3, 1, 1, 1),
    ('用户新增', 'user:add', NULL, NULL, NULL, 2, 2, 3, 1, 1, 1),
    ('用户编辑', 'user:edit', NULL, NULL, NULL, 2, 3, 3, 1, 1, 1),
    ('用户删除', 'user:delete', NULL, NULL, NULL, 2, 4, 3, 1, 1, 1),
  -- 角色管理菜单
  ('角色管理', 'role', '/system/role', 'system/role/index', 'team', 1, 2, 2, 1, 1, 1),
    -- 角色管理按钮
    ('角色查看', 'role:view', NULL, NULL, NULL, 7, 1, 3, 1, 1, 1),
    ('角色新增', 'role:add', NULL, NULL, NULL, 7, 2, 3, 1, 1, 1),
    ('角色编辑', 'role:edit', NULL, NULL, NULL, 7, 3, 3, 1, 1, 1),
    ('角色删除', 'role:delete', NULL, NULL, NULL, 7, 4, 3, 1, 1, 1),
  -- 权限管理菜单
  ('权限管理', 'permission', '/system/permission', 'system/permission/index', 'lock', 1, 3, 2, 1, 1, 1),
    -- 权限管理按钮
    ('权限查看', 'permission:view', NULL, NULL, NULL, 12, 1, 3, 1, 1, 1),
    ('权限新增', 'permission:add', NULL, NULL, NULL, 12, 2, 3, 1, 1, 1),
    ('权限编辑', 'permission:edit', NULL, NULL, NULL, 12, 3, 3, 1, 1, 1),
    ('权限删除', 'permission:delete', NULL, NULL, NULL, 12, 4, 3, 1, 1, 1),
  -- 部门管理菜单
  ('部门管理', 'dept', '/system/dept', 'system/dept/index', 'apartment', 1, 4, 2, 1, 1, 1),
    -- 部门管理按钮
    ('部门查看', 'dept:view', NULL, NULL, NULL, 17, 1, 3, 1, 1, 1),
    ('部门新增', 'dept:add', NULL, NULL, NULL, 17, 2, 3, 1, 1, 1),
    ('部门编辑', 'dept:edit', NULL, NULL, NULL, 17, 3, 3, 1, 1, 1),
    ('部门删除', 'dept:delete', NULL, NULL, NULL, 17, 4, 3, 1, 1, 1);

-- 4. 初始化接口权限数据
INSERT INTO `sys_permission` (`name`, `code`, `path`, `method`, `type`, `status`, `parent_id`, `sort`, `tenant_id`) VALUES 
-- 用户管理接口
('用户列表接口', 'user:list:api', '/api/user', 'GET', 3, 1, 0, 1, 1),
('用户详情接口', 'user:view:api', '/api/user/{id}', 'GET', 3, 1, 0, 2, 1),
('用户新增接口', 'user:add:api', '/api/user', 'POST', 3, 1, 0, 3, 1),
('用户编辑接口', 'user:edit:api', '/api/user/{id}', 'PUT', 3, 1, 0, 4, 1),
('用户删除接口', 'user:delete:api', '/api/user/{id}', 'DELETE', 3, 1, 0, 5, 1),
-- 角色管理接口
('角色列表接口', 'role:list:api', '/api/role', 'GET', 3, 1, 0, 6, 1),
('角色详情接口', 'role:view:api', '/api/role/{id}', 'GET', 3, 1, 0, 7, 1),
('角色新增接口', 'role:add:api', '/api/role', 'POST', 3, 1, 0, 8, 1),
('角色编辑接口', 'role:edit:api', '/api/role/{id}', 'PUT', 3, 1, 0, 9, 1),
('角色删除接口', 'role:delete:api', '/api/role/{id}', 'DELETE', 3, 1, 0, 10, 1),
-- 权限管理接口
('权限列表接口', 'permission:list:api', '/api/permission', 'GET', 3, 1, 0, 11, 1),
('权限详情接口', 'permission:view:api', '/api/permission/{id}', 'GET', 3, 1, 0, 12, 1),
('权限新增接口', 'permission:add:api', '/api/permission', 'POST', 3, 1, 0, 13, 1),
('权限编辑接口', 'permission:edit:api', '/api/permission/{id}', 'PUT', 3, 1, 0, 14, 1),
('权限删除接口', 'permission:delete:api', '/api/permission/{id}', 'DELETE', 3, 1, 0, 15, 1),
-- 部门管理接口
('部门列表接口', 'dept:list:api', '/api/dept', 'GET', 3, 1, 0, 16, 1),
('部门详情接口', 'dept:view:api', '/api/dept/{id}', 'GET', 3, 1, 0, 17, 1),
('部门新增接口', 'dept:add:api', '/api/dept', 'POST', 3, 1, 0, 18, 1),
('部门编辑接口', 'dept:edit:api', '/api/dept/{id}', 'PUT', 3, 1, 0, 19, 1),
('部门删除接口', 'dept:delete:api', '/api/dept/{id}', 'DELETE', 3, 1, 0, 20, 1);

-- 5. 初始化默认用户
-- 密码：admin123（BCrypt加密）
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `phone`, `dept_id`, `type`, `status`, `tenant_id`) VALUES 
('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '超级管理员', 'admin@example.com', '13800138000', 1, 1, 1, 1),
('user', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '普通用户', 'user@example.com', '13800138001', 2, 0, 1, 1),
('dept_admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '部门管理员', 'dept_admin@example.com', '13800138002', 2, 0, 1, 1);

-- 6. 为用户分配角色
SET @admin_role_id = (SELECT id FROM `sys_role` WHERE code = 'ADMIN');
SET @user_role_id = (SELECT id FROM `sys_role` WHERE code = 'USER');
SET @dept_admin_role_id = (SELECT id FROM `sys_role` WHERE code = 'DEPT_ADMIN');

SET @admin_user_id = (SELECT id FROM `sys_user` WHERE username = 'admin');
SET @normal_user_id = (SELECT id FROM `sys_user` WHERE username = 'user');
SET @dept_admin_user_id = (SELECT id FROM `sys_user` WHERE username = 'dept_admin');

INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES 
(@admin_user_id, @admin_role_id),
(@normal_user_id, @user_role_id),
(@dept_admin_user_id, @dept_admin_role_id);

-- 7. 为角色分配菜单
-- 超级管理员拥有所有菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) 
SELECT @admin_role_id, id FROM `sys_menu`;

-- 部门管理员拥有用户管理和部门管理菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) 
SELECT @dept_admin_role_id, id FROM `sys_menu` WHERE code IN 
('system', 'user', 'user:view', 'user:add', 'user:edit', 'dept', 'dept:view');

-- 普通用户拥有基础菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) 
SELECT @user_role_id, id FROM `sys_menu` WHERE code IN ('system', 'user', 'user:view');

-- 8. 为角色分配权限
-- 超级管理员拥有所有权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) 
SELECT @admin_role_id, id FROM `sys_permission`;

-- 部门管理员拥有部门相关权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) 
SELECT @dept_admin_role_id, id FROM `sys_permission` WHERE code IN 
('user:list:api', 'user:view:api', 'user:add:api', 'user:edit:api', 
 'dept:list:api', 'dept:view:api');

-- 普通用户拥有基础权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) 
SELECT @user_role_id, id FROM `sys_permission` WHERE code IN ('user:view:api');

-- 9. 为菜单分配权限（建立菜单与权限的关联）
-- 用户管理菜单关联用户管理接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'user' AND p.code IN ('user:list:api', 'user:view:api', 'user:add:api', 'user:edit:api', 'user:delete:api');

-- 角色管理菜单关联角色管理接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'role' AND p.code IN ('role:list:api', 'role:view:api', 'role:add:api', 'role:edit:api', 'role:delete:api');

-- 权限管理菜单关联权限管理接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'permission' AND p.code IN ('permission:list:api', 'permission:view:api', 'permission:add:api', 'permission:edit:api', 'permission:delete:api');

-- 部门管理菜单关联部门管理接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'dept' AND p.code IN ('dept:list:api', 'dept:view:api', 'dept:add:api', 'dept:edit:api', 'dept:delete:api');

-- 10. 操作日志表
CREATE TABLE `sys_operation_log` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT,
  `username` VARCHAR(50),
  `business_type` TINYINT,
  `title` VARCHAR(100),
  `client_ip` VARCHAR(50),
  `request_params` TEXT,
  `status` TINYINT DEFAULT 0,
  `operate_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` BIGINT NOT NULL,
  FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 11. 数据字典类型表
CREATE TABLE `sys_dict_type` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL COMMENT '字典类型名称',
  `code` VARCHAR(50) NOT NULL COMMENT '字典类型编码',
  `description` VARCHAR(255) COMMENT '字典类型描述',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  UNIQUE KEY `uk_sys_dict_type_code_tenant` (`code`, `tenant_id`),
  KEY `idx_sys_dict_type_status` (`status`),
  KEY `idx_sys_dict_type_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_dict_type_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典类型表';

-- 12. 数据字典值表
CREATE TABLE `sys_dict_value` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `dict_type_id` BIGINT NOT NULL COMMENT '字典类型ID',
  `value` VARCHAR(50) NOT NULL COMMENT '字典值',
  `label` VARCHAR(50) NOT NULL COMMENT '字典标签',
  `description` VARCHAR(255) COMMENT '字典描述',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  KEY `idx_sys_dict_value_dict_type_id` (`dict_type_id`),
  KEY `idx_sys_dict_value_status` (`status`),
  KEY `idx_sys_dict_value_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_dict_value_dict_type` FOREIGN KEY (`dict_type_id`) REFERENCES `sys_dict_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_dict_value_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典值表';

-- 13. 初始化数据字典类型数据
INSERT INTO `sys_dict_type` (`name`, `code`, `description`, `sort`, `status`, `tenant_id`) VALUES 
('内容类型', 'content_type', '内容分享平台的内容类型', 1, 1, 1),
('内容状态', 'content_status', '内容的发布状态', 2, 1, 1),
('用户类型', 'user_type', '用户类型', 3, 1, 1),
('性别', 'gender', '用户性别', 4, 1, 1),
('设备类型', 'device_type', '访问设备类型', 5, 1, 1),
('操作类型', 'operation_type', '系统操作类型', 6, 1, 1);

-- 14. 初始化数据字典值数据
-- 内容类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'novel', '小说', '小说内容', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'article', '文章', '文章内容', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'comic', '漫画', '漫画内容', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';

-- 内容状态
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'draft', '草稿', '草稿状态', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'pending', '待审核', '待审核状态', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'published', '已发布', '已发布状态', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'rejected', '已驳回', '已驳回状态', 4, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';

-- 用户类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '0', '普通用户', '普通注册用户', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'user_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '1', '管理员', '系统管理员', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'user_type';

-- 性别
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '0', '未知', '未知性别', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'gender';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '1', '男', '男性', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'gender';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '2', '女', '女性', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'gender';

-- 设备类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'pc', 'PC端', '电脑设备', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'device_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'mobile', '移动端', '手机设备', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'device_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'tablet', '平板端', '平板设备', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'device_type';

-- 操作类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '1', '新增', '新增操作', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '2', '编辑', '编辑操作', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '3', '删除', '删除操作', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '4', '查询', '查询操作', 4, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '5', '导出', '导出操作', 5, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '6', '导入', '导入操作', 6, 1, 1 FROM `sys_dict_type` WHERE code = 'operation_type';

-- 15. 为超级管理员添加数据字典相关权限
-- 添加数据字典菜单
INSERT INTO `sys_menu` (`name`, `code`, `path`, `component`, `icon`, `parent_id`, `sort`, `type`, `visible`, `status`, `tenant_id`) VALUES 
-- 数据字典管理菜单
('数据字典管理', 'dict', '/system/dict', 'system/dict/index', 'document', 1, 5, 2, 1, 1, 1),
  -- 数据字典类型管理
  ('字典类型管理', 'dict:type', '/system/dict/type', 'system/dict/type/index', NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict'), 1, 2, 1, 1, 1),
    ('字典类型查看', 'dict:type:view', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:type'), 1, 3, 1, 1, 1),
    ('字典类型新增', 'dict:type:add', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:type'), 2, 3, 1, 1, 1),
    ('字典类型编辑', 'dict:type:edit', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:type'), 3, 3, 1, 1, 1),
    ('字典类型删除', 'dict:type:delete', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:type'), 4, 3, 1, 1, 1),
  -- 数据字典值管理
  ('字典值管理', 'dict:value', '/system/dict/value', 'system/dict/value/index', NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict'), 2, 2, 1, 1, 1),
    ('字典值查看', 'dict:value:view', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:value'), 1, 3, 1, 1, 1),
    ('字典值新增', 'dict:value:add', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:value'), 2, 3, 1, 1, 1),
    ('字典值编辑', 'dict:value:edit', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:value'), 3, 3, 1, 1, 1),
    ('字典值删除', 'dict:value:delete', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'dict:value'), 4, 3, 1, 1, 1);

-- 添加数据字典接口权限
INSERT INTO `sys_permission` (`name`, `code`, `path`, `method`, `type`, `status`, `parent_id`, `sort`, `tenant_id`) VALUES 
-- 字典类型管理接口
('字典类型列表接口', 'dict:type:list:api', '/api/dict/type', 'GET', 3, 1, 0, 21, 1),
('字典类型详情接口', 'dict:type:view:api', '/api/dict/type/{id}', 'GET', 3, 1, 0, 22, 1),
('字典类型新增接口', 'dict:type:add:api', '/api/dict/type', 'POST', 3, 1, 0, 23, 1),
('字典类型编辑接口', 'dict:type:edit:api', '/api/dict/type/{id}', 'PUT', 3, 1, 0, 24, 1),
('字典类型删除接口', 'dict:type:delete:api', '/api/dict/type/{id}', 'DELETE', 3, 1, 0, 25, 1),
-- 字典值管理接口
('字典值列表接口', 'dict:value:list:api', '/api/dict/value', 'GET', 3, 1, 0, 26, 1),
('字典值详情接口', 'dict:value:view:api', '/api/dict/value/{id}', 'GET', 3, 1, 0, 27, 1),
('字典值新增接口', 'dict:value:add:api', '/api/dict/value', 'POST', 3, 1, 0, 28, 1),
('字典值编辑接口', 'dict:value:edit:api', '/api/dict/value/{id}', 'PUT', 3, 1, 0, 29, 1),
('字典值删除接口', 'dict:value:delete:api', '/api/dict/value/{id}', 'DELETE', 3, 1, 0, 30, 1);

-- 16. 添加日志管理相关权限和菜单
-- 日志管理菜单
INSERT INTO `sys_menu` (`name`, `code`, `path`, `component`, `icon`, `parent_id`, `sort`, `type`, `visible`, `status`, `tenant_id`) VALUES 
('日志管理', 'log', '/system/log', 'system/log/index', 'document-copy', 1, 6, 2, 1, 1, 1),
  ('日志查看', 'log:view', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'log'), 1, 3, 1, 1, 1),
  ('日志删除', 'log:delete', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'log'), 2, 3, 1, 1, 1),
  ('日志清空', 'log:clear', NULL, NULL, NULL, (SELECT id FROM `sys_menu` WHERE code = 'log'), 3, 3, 1, 1, 1);

-- 日志管理接口权限
INSERT INTO `sys_permission` (`name`, `code`, `path`, `method`, `type`, `status`, `parent_id`, `sort`, `tenant_id`) VALUES 
('日志列表接口', 'log:list:api', '/api/log', 'GET', 3, 1, 0, 31, 1),
('日志详情接口', 'log:view:api', '/api/log/{id}', 'GET', 3, 1, 0, 32, 1),
('日志删除接口', 'log:delete:api', '/api/log/{id}', 'DELETE', 3, 1, 0, 33, 1),
('日志清空接口', 'log:clear:api', '/api/log/clear', 'DELETE', 3, 1, 0, 34, 1);

-- 为超级管理员分配数据字典和日志管理权限
SET @admin_role_id = (SELECT id FROM `sys_role` WHERE code = 'ADMIN');

-- 分配数据字典菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT @admin_role_id, id FROM `sys_menu` WHERE code IN ('dict', 'dict:type', 'dict:type:view', 'dict:type:add', 'dict:type:edit', 'dict:type:delete', 'dict:value', 'dict:value:view', 'dict:value:add', 'dict:value:edit', 'dict:value:delete');

-- 分配日志管理菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT @admin_role_id, id FROM `sys_menu` WHERE code IN ('log', 'log:view', 'log:delete', 'log:clear');

-- 分配数据字典接口权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT @admin_role_id, id FROM `sys_permission` WHERE code IN (
  'dict:type:list:api', 'dict:type:view:api', 'dict:type:add:api', 'dict:type:edit:api', 'dict:type:delete:api',
  'dict:value:list:api', 'dict:value:view:api', 'dict:value:add:api', 'dict:value:edit:api', 'dict:value:delete:api'
);

-- 分配日志管理接口权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT @admin_role_id, id FROM `sys_permission` WHERE code IN ('log:list:api', 'log:view:api', 'log:delete:api', 'log:clear:api');

-- 为菜单分配权限
-- 字典类型管理菜单关联接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'dict:type' AND p.code IN ('dict:type:list:api', 'dict:type:view:api', 'dict:type:add:api', 'dict:type:edit:api', 'dict:type:delete:api');

-- 字典值管理菜单关联接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'dict:value' AND p.code IN ('dict:value:list:api', 'dict:value:view:api', 'dict:value:add:api', 'dict:value:edit:api', 'dict:value:delete:api');

-- 日志管理菜单关联接口权限
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`)
SELECT m.id, p.id FROM `sys_menu` m, `sys_permission` p 
WHERE m.code = 'log' AND p.code IN ('log:list:api', 'log:view:api', 'log:delete:api', 'log:clear:api');

