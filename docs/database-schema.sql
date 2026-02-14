-- =====================================================
-- 内容分享平台数据库表结构
-- 数据库名称: content_sharing_platform
-- 字符集: utf8mb4
-- 排序规则: utf8mb4_unicode_ci
-- =====================================================

CREATE DATABASE IF NOT EXISTS `content_sharing_platform` 
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `content_sharing_platform`;

-- =====================================================
-- 表前缀策略:
-- 1. 系统管理相关表使用 sys_ 前缀
-- 2. 用户服务相关表使用 sys_ 前缀（用户属于系统管理）
-- 3. 内容服务相关表使用 content_ 前缀
-- 4. 其他服务按服务名使用相应前缀
-- =====================================================

-- =====================================================
-- 一、租户管理模块
-- =====================================================

-- 1.1 租户表
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

-- =====================================================
-- 二、组织架构模块
-- =====================================================

-- 2.1 部门表
CREATE TABLE `sys_dept` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` VARCHAR(50) NOT NULL COMMENT '部门名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父部门ID',
  `ancestors` VARCHAR(500) DEFAULT '' COMMENT '祖级列表',
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
  CONSTRAINT `fk_sys_dept_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- =====================================================
-- 三、用户管理模块
-- =====================================================

-- 3.1 用户表
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
  KEY `idx_sys_user_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_user_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- =====================================================
-- 四、角色权限模块
-- =====================================================

-- 4.1 角色表
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(255) COMMENT '角色描述',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父角色ID',
  `data_scope` VARCHAR(10) DEFAULT '1' COMMENT '数据范围：1-全部，2-自定义，3-本部门，4-本部门及以下，5-仅本人',
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
  CONSTRAINT `fk_sys_role_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 4.2 权限表
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
  KEY `idx_sys_permission_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_permission_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 4.3 菜单表
CREATE TABLE `sys_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `path` VARCHAR(255) COMMENT '路由地址',
  `component` VARCHAR(255) COMMENT '组件路径',
  `query` VARCHAR(255) COMMENT '路由参数',
  `is_frame` TINYINT DEFAULT 0 COMMENT '是否外链：0-否，1-是',
  `is_cache` TINYINT DEFAULT 0 COMMENT '是否缓存：0-缓存，1-不缓存',
  `visible` TINYINT DEFAULT 1 COMMENT '是否显示：1-显示，0-隐藏',
  `type` TINYINT DEFAULT 1 COMMENT '菜单类型：1-目录，2-菜单，3-按钮',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `perms` VARCHAR(100) COMMENT '权限标识',
  `icon` VARCHAR(100) COMMENT '菜单图标',
  `remark` VARCHAR(500) COMMENT '备注',
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
  KEY `idx_sys_menu_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_menu_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 4.4 用户角色关联表
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
  CONSTRAINT `fk_sys_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 4.5 角色权限关联表
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
  CONSTRAINT `fk_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 4.6 角色菜单关联表
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
  CONSTRAINT `fk_sys_role_menu_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_menu_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 4.7 角色部门关联表（数据权限）
CREATE TABLE `sys_role_dept` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `dept_id` BIGINT NOT NULL COMMENT '部门ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_role_dept` (`role_id`, `dept_id`),
  KEY `idx_sys_role_dept_role_id` (`role_id`),
  KEY `idx_sys_role_dept_dept_id` (`dept_id`),
  CONSTRAINT `fk_sys_role_dept_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_dept_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门关联表';

-- 4.8 菜单权限关联表
CREATE TABLE `sys_menu_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_menu_permission` (`menu_id`, `permission_id`),
  KEY `idx_sys_menu_permission_menu_id` (`menu_id`),
  KEY `idx_sys_menu_permission_permission_id` (`permission_id`),
  CONSTRAINT `fk_sys_menu_permission_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_menu_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限关联表';

-- =====================================================
-- 五、日志管理模块
-- =====================================================

-- 5.1 操作日志表
CREATE TABLE `sys_operation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT COMMENT '操作用户ID',
  `username` VARCHAR(50) COMMENT '操作用户名',
  `business_type` TINYINT COMMENT '业务类型',
  `title` VARCHAR(100) COMMENT '操作标题',
  `method` VARCHAR(200) COMMENT '方法名称',
  `request_method` VARCHAR(10) COMMENT '请求方式',
  `request_url` VARCHAR(255) COMMENT '请求URL',
  `request_params` TEXT COMMENT '请求参数',
  `response_result` TEXT COMMENT '响应结果',
  `client_ip` VARCHAR(50) COMMENT '客户端IP',
  `status` TINYINT DEFAULT 0 COMMENT '操作状态：1-成功，0-失败',
  `error_msg` TEXT COMMENT '错误消息',
  `cost_time` BIGINT COMMENT '消耗时间(毫秒)',
  `operate_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_sys_operation_log_user_id` (`user_id`),
  KEY `idx_sys_operation_log_business_type` (`business_type`),
  KEY `idx_sys_operation_log_status` (`status`),
  KEY `idx_sys_operation_log_operate_time` (`operate_time`),
  KEY `idx_sys_operation_log_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_operation_log_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 5.2 登录日志表
CREATE TABLE `sys_login_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` VARCHAR(50) COMMENT '登录用户名',
  `ipaddr` VARCHAR(50) COMMENT '登录IP地址',
  `login_location` VARCHAR(255) COMMENT '登录地点',
  `browser` VARCHAR(50) COMMENT '浏览器类型',
  `os` VARCHAR(50) COMMENT '操作系统',
  `status` TINYINT DEFAULT 1 COMMENT '登录状态：1-成功，0-失败',
  `msg` VARCHAR(255) COMMENT '提示消息',
  `login_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_sys_login_log_username` (`username`),
  KEY `idx_sys_login_log_status` (`status`),
  KEY `idx_sys_login_log_login_time` (`login_time`),
  KEY `idx_sys_login_log_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_login_log_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- =====================================================
-- 六、数据字典模块
-- =====================================================

-- 6.1 数据字典类型表
CREATE TABLE `sys_dict_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_dict_type_code_tenant` (`code`, `tenant_id`),
  KEY `idx_sys_dict_type_status` (`status`),
  KEY `idx_sys_dict_type_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_dict_type_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典类型表';

-- 6.2 数据字典值表
CREATE TABLE `sys_dict_value` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典值ID',
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
  PRIMARY KEY (`id`),
  KEY `idx_sys_dict_value_dict_type_id` (`dict_type_id`),
  KEY `idx_sys_dict_value_status` (`status`),
  KEY `idx_sys_dict_value_tenant_id` (`tenant_id`),
  CONSTRAINT `fk_sys_dict_value_dict_type` FOREIGN KEY (`dict_type_id`) REFERENCES `sys_dict_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_dict_value_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `sys_tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典值表';

-- =====================================================
-- 七、内容管理模块
-- =====================================================

-- 7.1 内容表
CREATE TABLE `content_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `content_type` VARCHAR(50) NOT NULL COMMENT '内容类型：novel/article/comic',
  `summary` VARCHAR(500) COMMENT '摘要',
  `cover_image` VARCHAR(255) COMMENT '封面图片',
  `author_id` BIGINT NOT NULL COMMENT '作者ID',
  `author_name` VARCHAR(50) COMMENT '作者名称',
  `category_id` BIGINT COMMENT '分类ID',
  `tags` VARCHAR(500) COMMENT '标签，逗号分隔',
  `status` TINYINT DEFAULT 0 COMMENT '状态：0-草稿，1-待审核，2-已发布，3-已驳回',
  `view_count` BIGINT DEFAULT 0 COMMENT '浏览次数',
  `like_count` BIGINT DEFAULT 0 COMMENT '点赞次数',
  `comment_count` BIGINT DEFAULT 0 COMMENT '评论次数',
  `is_top` TINYINT DEFAULT 0 COMMENT '是否置顶',
  `is_recommend` TINYINT DEFAULT 0 COMMENT '是否推荐',
  `publish_time` DATETIME COMMENT '发布时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) COMMENT '创建人',
  `update_by` VARCHAR(50) COMMENT '更新人',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`),
  KEY `idx_content_content_type` (`content_type`),
  KEY `idx_content_author_id` (`author_id`),
  KEY `idx_content_category_id` (`category_id`),
  KEY `idx_content_status` (`status`),
  KEY `idx_content_tenant_id` (`tenant_id`),
  KEY `idx_content_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容表';

-- =====================================================
-- 八、评论管理模块
-- =====================================================

-- 8.1 评论表
CREATE TABLE `content_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `content_id` BIGINT NOT NULL COMMENT '内容ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `like_count` BIGINT DEFAULT 0 COMMENT '点赞次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`),
  KEY `idx_content_comment_content_id` (`content_id`),
  KEY `idx_content_comment_user_id` (`user_id`),
  KEY `idx_content_comment_parent_id` (`parent_id`),
  KEY `idx_content_comment_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- =====================================================
-- 九、支付管理模块
-- =====================================================

-- 9.1 订单表
CREATE TABLE `pay_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `product_type` VARCHAR(50) NOT NULL COMMENT '产品类型',
  `product_id` BIGINT NOT NULL COMMENT '产品ID',
  `product_name` VARCHAR(200) COMMENT '产品名称',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
  `pay_type` TINYINT COMMENT '支付方式：1-支付宝，2-微信，3-余额',
  `pay_amount` DECIMAL(10,2) COMMENT '实付金额',
  `transaction_id` VARCHAR(100) COMMENT '第三方交易号',
  `status` TINYINT DEFAULT 0 COMMENT '状态：0-待支付，1-已支付，2-已取消，3-已退款',
  `pay_time` DATETIME COMMENT '支付时间',
  `expire_time` DATETIME COMMENT '过期时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pay_order_order_no` (`order_no`),
  KEY `idx_pay_order_user_id` (`user_id`),
  KEY `idx_pay_order_status` (`status`),
  KEY `idx_pay_order_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- =====================================================
-- 十、消息管理模块
-- =====================================================

-- 10.1 消息表
CREATE TABLE `content_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` BIGINT COMMENT '发送者ID',
  `receiver_id` BIGINT NOT NULL COMMENT '接收者ID',
  `title` VARCHAR(200) COMMENT '消息标题',
  `content` TEXT COMMENT '消息内容',
  `type` TINYINT DEFAULT 1 COMMENT '消息类型：1-系统消息，2-私信',
  `is_read` TINYINT DEFAULT 0 COMMENT '是否已读',
  `read_time` DATETIME COMMENT '阅读时间',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`),
  KEY `idx_content_message_sender_id` (`sender_id`),
  KEY `idx_content_message_receiver_id` (`receiver_id`),
  KEY `idx_content_message_type` (`type`),
  KEY `idx_content_message_is_read` (`is_read`),
  KEY `idx_content_message_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- =====================================================
-- 初始化数据
-- =====================================================

-- 初始化默认租户
INSERT INTO `sys_tenant` (`name`, `code`, `description`, `status`) VALUES 
('默认租户', 'DEFAULT', '系统默认租户', 1);

-- 初始化默认部门
INSERT INTO `sys_dept` (`name`, `parent_id`, `ancestors`, `sort`, `status`, `tenant_id`) VALUES 
('总公司', 0, '0', 1, 1, 1),
('技术部', 1, '0,1', 2, 1, 1),
('运营部', 1, '0,1', 3, 1, 1),
('市场部', 1, '0,1', 4, 1, 1);

-- 初始化默认角色
INSERT INTO `sys_role` (`name`, `code`, `description`, `data_scope`, `status`, `tenant_id`) VALUES 
('超级管理员', 'ADMIN', '系统超级管理员，拥有所有权限', '1', 1, 1),
('普通用户', 'USER', '普通用户，拥有基础权限', '5', 1, 1),
('部门管理员', 'DEPT_ADMIN', '部门管理员，拥有部门内用户管理权限', '3', 1, 1);

-- 初始化默认用户（密码：admin123）
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `phone`, `dept_id`, `type`, `status`, `tenant_id`) VALUES 
('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '超级管理员', 'admin@example.com', '13800138000', 1, 1, 1, 1),
('user', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '普通用户', 'user@example.com', '13800138001', 2, 0, 1, 1),
('dept_admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.260Lfs7DRsgbX1lfCp9z.KK', '部门管理员', 'dept_admin@example.com', '13800138002', 2, 0, 1, 1);

-- 为用户分配角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES 
(1, 1),
(2, 2),
(3, 3);

-- 初始化数据字典类型
INSERT INTO `sys_dict_type` (`name`, `code`, `description`, `sort`, `status`, `tenant_id`) VALUES 
('内容类型', 'content_type', '内容分享平台的内容类型', 1, 1, 1),
('内容状态', 'content_status', '内容的发布状态', 2, 1, 1),
('用户类型', 'user_type', '用户类型', 3, 1, 1),
('性别', 'gender', '用户性别', 4, 1, 1),
('订单状态', 'order_status', '订单状态', 5, 1, 1);

-- 初始化数据字典值
INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'novel', '小说', '小说内容', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'article', '文章', '文章内容', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, 'comic', '漫画', '漫画内容', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'content_type';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '0', '草稿', '草稿状态', 1, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '1', '待审核', '待审核状态', 2, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '2', '已发布', '已发布状态', 3, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';

INSERT INTO `sys_dict_value` (`dict_type_id`, `value`, `label`, `description`, `sort`, `status`, `tenant_id`)
SELECT id, '3', '已驳回', '已驳回状态', 4, 1, 1 FROM `sys_dict_type` WHERE code = 'content_status';
