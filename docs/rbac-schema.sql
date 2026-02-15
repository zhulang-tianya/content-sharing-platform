-- 内容分享平台数据库初始化脚本
-- 数据库名称: ruoyi-flex (使用现有数据库)
-- 创建时间: 2026-02-14

use `ruoyi-flex`;
-- =====================================================
-- 1. 删除已存在的表（按外键依赖顺序）
-- =====================================================
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_role_permission`;
DROP TABLE IF EXISTS `sys_user_role`;
DROP TABLE IF EXISTS `sys_role_menu`;
DROP TABLE IF EXISTS `sys_menu_permission`;
DROP TABLE IF EXISTS `sys_operation_log`;
DROP TABLE IF EXISTS `sys_permission`;
DROP TABLE IF EXISTS `sys_menu`;
DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_dept`;
DROP TABLE IF EXISTS `sys_tenant`;
DROP TABLE IF EXISTS `sys_dict_type`;
DROP TABLE IF EXISTS `sys_dict_value`;

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- 2. 创建租户表
-- =====================================================
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
  KEY `idx_sys_tenant_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- =====================================================
-- 3. 创建部门表
-- =====================================================
CREATE TABLE `sys_dept` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` VARCHAR(50) NOT NULL COMMENT '部门名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父部门ID',
  `ancestors` VARCHAR(500) DEFAULT '' COMMENT '祖级列表',
  `leader` VARCHAR(50) COMMENT '部门负责人',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `email` VARCHAR(50) COMMENT '邮箱',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志：1-已删除，0-正常',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sys_dept_parent_id` (`parent_id`),
  KEY `idx_sys_dept_status` (`status`),
  KEY `idx_sys_dept_deleted` (`deleted`),
  KEY `idx_sys_dept_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- =====================================================
-- 4. 创建用户表
-- =====================================================
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `email` VARCHAR(50) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `avatar` VARCHAR(255) COMMENT '头像',
  `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
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
  KEY `idx_sys_user_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- =====================================================
-- 5. 创建角色表
-- =====================================================
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(255) COMMENT '角色描述',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父角色ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志：1-已删除，0-正常',
  `data_scope` VARCHAR(10) DEFAULT '1' COMMENT '数据范围：1-全部，2-自定义，3-本部门，4-本部门及以下，5-仅本人',
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
  KEY `idx_sys_role_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- =====================================================
-- 6. 创建权限表
-- =====================================================
CREATE TABLE `sys_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `code` VARCHAR(50) NOT NULL COMMENT '权限编码',
  `path` VARCHAR(255) COMMENT '请求路径',
  `method` VARCHAR(10) COMMENT '请求方法',
  `description` VARCHAR(255) COMMENT '权限描述',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父权限ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `type` TINYINT DEFAULT 3 COMMENT '权限类型：1-目录，2-菜单，3-按钮/接口',
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
  KEY `idx_sys_permission_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- =====================================================
-- 7. 创建菜单表
-- =====================================================
CREATE TABLE `sys_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
  `type` TINYINT DEFAULT 1 COMMENT '菜单类型：1-目录，2-菜单，3-按钮',
  `path` VARCHAR(255) COMMENT '路由路径',
  `component` VARCHAR(255) COMMENT '组件路径',
  `query` VARCHAR(255) COMMENT '路由参数',
  `is_frame` TINYINT DEFAULT 0 COMMENT '是否外链：0-否，1-是',
  `is_cache` TINYINT DEFAULT 0 COMMENT '是否缓存：0-否，1-是',
  `visible` TINYINT DEFAULT 1 COMMENT '是否显示：1-显示，0-隐藏',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `perms` VARCHAR(100) COMMENT '权限标识',
  `icon` VARCHAR(50) COMMENT '菜单图标',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `remark` VARCHAR(255) COMMENT '备注',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_sys_menu_parent_id` (`parent_id`),
  KEY `idx_sys_menu_type` (`type`),
  KEY `idx_sys_menu_visible` (`visible`),
  KEY `idx_sys_menu_status` (`status`),
  KEY `idx_sys_menu_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- =====================================================
-- 8. 创建用户角色关联表
-- =====================================================
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_user_role` (`user_id`, `role_id`),
  KEY `idx_sys_user_role_user_id` (`user_id`),
  KEY `idx_sys_user_role_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- =====================================================
-- 9. 创建角色权限关联表
-- =====================================================
CREATE TABLE `sys_role_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_permission` (`role_id`, `permission_id`),
  KEY `idx_sys_role_permission_role_id` (`role_id`),
  KEY `idx_sys_role_permission_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- =====================================================
-- 10. 创建角色菜单关联表
-- =====================================================
CREATE TABLE `sys_role_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_menu` (`role_id`, `menu_id`),
  KEY `idx_sys_role_menu_role_id` (`role_id`),
  KEY `idx_sys_role_menu_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- =====================================================
-- 11. 创建菜单权限关联表
-- =====================================================
CREATE TABLE `sys_menu_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_menu_permission` (`menu_id`, `permission_id`),
  KEY `idx_sys_menu_permission_menu_id` (`menu_id`),
  KEY `idx_sys_menu_permission_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限关联表';

-- =====================================================
-- 12. 创建操作日志表
-- =====================================================
CREATE TABLE `sys_operation_log` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
  `user_id` BIGINT COMMENT '操作用户ID',
  `username` VARCHAR(50) COMMENT '操作用户名',
  `business_type` TINYINT COMMENT '业务类型',
  `title` VARCHAR(100) COMMENT '操作标题',
  `method` VARCHAR(200) COMMENT '方法名称',
  `request_method` VARCHAR(10) COMMENT '请求方式',
  `request_url` VARCHAR(500) COMMENT '请求URL',
  `request_params` TEXT COMMENT '请求参数',
  `response_result` TEXT COMMENT '响应结果',
  `client_ip` VARCHAR(50) COMMENT '客户端IP',
  `status` TINYINT DEFAULT 0 COMMENT '操作状态：0-成功，1-失败',
  `error_msg` TEXT COMMENT '错误信息',
  `operate_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- =====================================================
-- 13. 创建数据字典类型表
-- =====================================================
CREATE TABLE `sys_dict_type` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典类型ID',
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
  KEY `idx_sys_dict_type_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典类型表';

-- =====================================================
-- 14. 创建数据字典值表
-- =====================================================
CREATE TABLE `sys_dict_value` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典值ID',
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
  KEY `idx_sys_dict_value_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典值表';

-- =====================================================
-- 15. 初始化基础数据
-- =====================================================

-- 15.1 初始化默认租户
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `description`, `status`) VALUES 
(1, '默认租户', 'DEFAULT', '系统默认租户', 1);

-- 15.2 初始化默认部门
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `ancestors`, `sort`, `status`, `tenant_id`) VALUES 
(1, '总公司', 0, '0', 1, 1, 1),
(2, '技术部', 1, '0,1', 1, 1, 1),
(3, '运营部', 1, '0,1', 2, 1, 1),
(4, '市场部', 1, '0,1', 3, 1, 1);

-- 15.3 初始化默认角色
INSERT INTO `sys_role` (`id`, `name`, `code`, `description`, `status`, `data_scope`, `tenant_id`) VALUES 
(1, '超级管理员', 'ADMIN', '系统超级管理员，拥有所有权限', 1, '1', 1),
(2, '普通用户', 'USER', '普通用户，拥有基础权限', 1, '5', 1),
(3, '部门管理员', 'DEPT_ADMIN', '部门管理员，拥有部门内用户管理权限', 1, '3', 1);

-- 15.4 初始化默认用户
-- 密码：admin123（BCrypt加密）
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `dept_id`, `type`, `status`, `tenant_id`) VALUES 
(1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '超级管理员', 'admin@example.com', '13800138000', 1, 1, 1, 1),
(2, 'user', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '普通用户', 'user@example.com', '13800138001', 2, 0, 1, 1),
(3, 'dept_admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '部门管理员', 'dept_admin@example.com', '13800138002', 2, 0, 1, 1);

-- 15.5 初始化菜单数据
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `type`, `path`, `component`, `icon`, `sort`, `visible`, `status`, `tenant_id`) VALUES 
-- 系统管理目录
(1, '系统管理', 0, 1, '/system', NULL, 'setting', 1, 1, 1, 1),
-- 用户管理菜单
(2, '用户管理', 1, 2, '/system/user', 'system/user/index', 'user', 1, 1, 1, 1),
-- 用户管理按钮
(3, '用户查看', 2, 3, NULL, NULL, NULL, 1, 1, 1, 1),
(4, '用户新增', 2, 3, NULL, NULL, NULL, 2, 1, 1, 1),
(5, '用户编辑', 2, 3, NULL, NULL, NULL, 3, 1, 1, 1),
(6, '用户删除', 2, 3, NULL, NULL, NULL, 4, 1, 1, 1),
-- 角色管理菜单
(7, '角色管理', 1, 2, '/system/role', 'system/role/index', 'team', 2, 1, 1, 1),
-- 角色管理按钮
(8, '角色查看', 7, 3, NULL, NULL, NULL, 1, 1, 1, 1),
(9, '角色新增', 7, 3, NULL, NULL, NULL, 2, 1, 1, 1),
(10, '角色编辑', 7, 3, NULL, NULL, NULL, 3, 1, 1, 1),
(11, '角色删除', 7, 3, NULL, NULL, NULL, 4, 1, 1, 1),
-- 权限管理菜单
(12, '权限管理', 1, 2, '/system/permission', 'system/permission/index', 'lock', 3, 1, 1, 1),
-- 部门管理菜单
(17, '部门管理', 1, 2, '/system/dept', 'system/dept/index', 'apartment', 4, 1, 1, 1);

-- 15.6 初始化接口权限数据
INSERT INTO `sys_permission` (`id`, `name`, `code`, `path`, `method`, `type`, `status`, `sort`, `tenant_id`) VALUES 
-- 用户管理接口
(1, '用户列表接口', 'user:list', '/api/user', 'GET', 3, 1, 1, 1),
(2, '用户详情接口', 'user:view', '/api/user/{id}', 'GET', 3, 1, 2, 1),
(3, '用户新增接口', 'user:add', '/api/user', 'POST', 3, 1, 3, 1),
(4, '用户编辑接口', 'user:edit', '/api/user/{id}', 'PUT', 3, 1, 4, 1),
(5, '用户删除接口', 'user:delete', '/api/user/{id}', 'DELETE', 3, 1, 5, 1),
-- 角色管理接口
(6, '角色列表接口', 'role:list', '/api/role', 'GET', 3, 1, 6, 1),
(7, '角色详情接口', 'role:view', '/api/role/{id}', 'GET', 3, 1, 7, 1),
(8, '角色新增接口', 'role:add', '/api/role', 'POST', 3, 1, 8, 1),
(9, '角色编辑接口', 'role:edit', '/api/role/{id}', 'PUT', 3, 1, 9, 1),
(10, '角色删除接口', 'role:delete', '/api/role/{id}', 'DELETE', 3, 1, 10, 1),
-- 权限管理接口
(11, '权限列表接口', 'permission:list', '/api/permission', 'GET', 3, 1, 11, 1),
(12, '权限详情接口', 'permission:view', '/api/permission/{id}', 'GET', 3, 1, 12, 1),
-- 部门管理接口
(16, '部门列表接口', 'dept:list', '/api/dept', 'GET', 3, 1, 16, 1),
(17, '部门详情接口', 'dept:view', '/api/dept/{id}', 'GET', 3, 1, 17, 1);

-- 15.7 为用户分配角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES 
(1, 1),
(2, 2),
(3, 3);

-- 15.8 为角色分配菜单
-- 超级管理员拥有所有菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) 
SELECT 1, id FROM `sys_menu`;

-- 部门管理员拥有用户管理和部门管理菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES 
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 17);

-- 普通用户拥有基础菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES 
(2, 1), (2, 2), (2, 3);

-- 15.9 为角色分配权限
-- 超级管理员拥有所有权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) 
SELECT 1, id FROM `sys_permission`;

-- 部门管理员拥有部门相关权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES 
(3, 1), (3, 2), (3, 3), (3, 4), (3, 16), (3, 17);

-- 普通用户拥有基础权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES 
(2, 1), (2, 2);

-- 15.10 初始化数据字典类型
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `description`, `sort`, `status`, `tenant_id`) VALUES 
(1, '内容类型', 'content_type', '内容分享平台的内容类型', 1, 1, 1),
(2, '内容状态', 'content_status', '内容的发布状态', 2, 1, 1),
(3, '用户类型', 'user_type', '用户类型', 3, 1, 1),
(4, '性别', 'gender', '用户性别', 4, 1, 1);

-- 15.11 初始化数据字典值
-- 性别
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `sort`, `status`, `tenant_id`) VALUES 
(4, '0', '未知', 1, 1, 1),
(4, '1', '男', 2, 1, 1),
(4, '2', '女', 3, 1, 1);

-- 用户类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `sort`, `status`, `tenant_id`) VALUES 
(3, '0', '普通用户', 1, 1, 1),
(3, '1', '管理员', 2, 1, 1);

-- 内容类型
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `sort`, `status`, `tenant_id`) VALUES 
(1, 'novel', '小说', 1, 1, 1),
(1, 'article', '文章', 2, 1, 1),
(1, 'comic', '漫画', 3, 1, 1);

-- 内容状态
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `sort`, `status`, `tenant_id`) VALUES 
(2, 'draft', '草稿', 1, 1, 1),
(2, 'pending', '待审核', 2, 1, 1),
(2, 'published', '已发布', 3, 1, 1),
(2, 'rejected', '已驳回', 4, 1, 1);

-- =====================================================
-- 初始化完成
-- =====================================================
