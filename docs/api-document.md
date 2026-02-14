# 内容分享平台 API 文档

## 概述

本文档描述内容分享平台的所有后端API接口。平台基于Spring Cloud Alibaba微服务架构，采用JWT无状态认证方式。

**基础信息：**
- 基础路径: `/api`
- 认证方式: Bearer Token (JWT)
- 数据格式: JSON
- 编码: UTF-8

---

## 通用响应格式

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

**响应码说明：**
| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 401 | 未授权/Token失效 |
| 403 | 权限不足 |
| 500 | 服务器内部错误 |

---

## 1. 认证服务 (Auth Service)

### 1.1 用户登录

**接口地址：** `POST /auth/login`

**描述：** 用户登录认证，获取访问令牌

**请求参数：**
```json
{
  "username": "string",
  "password": "string"
}
```

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

---

### 1.2 用户登出

**接口地址：** `POST /auth/logout`

**描述：** 用户退出登录，注销Token

**请求头：** `Authorization: Bearer {token}`

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功"
}
```

---

### 1.3 获取用户信息

**接口地址：** `GET /auth/profile`

**描述：** 获取当前登录用户的详细信息

**请求头：** `Authorization: Bearer {token}`

**响应示例：**
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "username": "admin",
    "nickname": "管理员",
    "email": "admin@example.com",
    "phone": "13800138000",
    "avatar": "https://...",
    "roles": ["admin"],
    "permissions": ["*:*:*"]
  }
}
```

---

### 1.4 刷新令牌

**接口地址：** `POST /auth/refresh`

**描述：** 使用刷新令牌获取新的访问令牌

**请求参数：**
```json
{
  "refreshToken": "string"
}
```

**响应示例：**
```json
{
  "code": 200,
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 2. 用户管理服务 (User Service)

### 2.1 获取用户列表

**接口地址：** `GET /user`

**权限：** `user:list`

**描述：** 获取所有用户信息列表

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "username": "admin",
      "nickname": "管理员",
      "email": "admin@example.com",
      "phone": "13800138000",
      "status": 1,
      "createTime": "2024-01-01 00:00:00"
    }
  ]
}
```

---

### 2.2 分页获取用户列表

**接口地址：** `GET /user/page`

**权限：** `user:list`

**请求参数：**
| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| page | int | 否 | 1 | 当前页码 |
| size | int | 否 | 10 | 每页大小 |

**响应示例：**
```json
{
  "code": 200,
  "data": {
    "records": [],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

---

### 2.3 根据ID获取用户

**接口地址：** `GET /user/{id}`

**权限：** `user:view`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 用户ID |

---

### 2.4 创建用户

**接口地址：** `POST /user`

**权限：** `user:add`

**请求参数：**
```json
{
  "username": "string",
  "password": "string",
  "nickname": "string",
  "email": "string",
  "phone": "string",
  "deptId": 100,
  "roleIds": [1, 2]
}
```

---

### 2.5 更新用户

**接口地址：** `PUT /user/{id}`

**权限：** `user:edit`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 用户ID |

**请求参数：**
```json
{
  "nickname": "string",
  "email": "string",
  "phone": "string",
  "deptId": 100,
  "roleIds": [1, 2]
}
```

---

### 2.6 删除用户

**接口地址：** `DELETE /user/{id}`

**权限：** `user:delete`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 用户ID |

---

### 2.7 根据用户名获取用户

**接口地址：** `GET /user/username/{username}`

**权限：** `user:view`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| username | String | 用户名 |

---

## 3. 角色管理服务 (Role Service)

### 3.1 获取角色详情

**接口地址：** `GET /role/{id}`

**权限：** `system:role:query`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 角色ID |

---

### 3.2 获取所有启用的角色

**接口地址：** `GET /role/list`

**权限：** `system:role:list`

---

### 3.3 分页查询角色列表

**接口地址：** `GET /role/page`

**权限：** `system:role:list`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleName | String | 否 | 角色名称 |
| status | Integer | 否 | 状态 |
| pageNum | int | 否 | 页码 |
| pageSize | int | 否 | 每页大小 |

---

### 3.4 新增角色

**接口地址：** `POST /role`

**权限：** `system:role:add`

**请求参数：**
```json
{
  "roleName": "string",
  "roleKey": "string",
  "sort": 1,
  "status": 1,
  "remark": "string"
}
```

---

### 3.5 更新角色

**接口地址：** `PUT /role`

**权限：** `system:role:edit`

**请求参数：**
```json
{
  "id": 1,
  "roleName": "string",
  "roleKey": "string",
  "sort": 1,
  "status": 1,
  "remark": "string"
}
```

---

### 3.6 删除角色

**接口地址：** `DELETE /role/{id}`

**权限：** `system:role:delete`

---

### 3.7 批量删除角色

**接口地址：** `DELETE /role/batch`

**权限：** `system:role:delete`

**请求参数：**
```json
[1, 2, 3]
```

---

### 3.8 修改角色状态

**接口地址：** `PUT /role/{id}/status`

**权限：** `system:role:edit`

**请求参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| status | Integer | 状态(0禁用 1启用) |

---

### 3.9 获取角色权限ID列表

**接口地址：** `GET /role/{id}/permissions`

**权限：** `system:role:query`

**响应示例：**
```json
{
  "code": 200,
  "data": [1, 2, 3, 4, 5]
}
```

---

### 3.10 分配角色权限

**接口地址：** `POST /role/{id}/permissions`

**权限：** `system:role:edit`

**请求参数：**
```json
[1, 2, 3, 4, 5]
```

---

## 4. 权限管理服务 (Permission Service)

### 4.1 获取权限详情

**接口地址：** `GET /permission/{id}`

**权限：** `system:permission:query`

---

### 4.2 获取所有启用的权限

**接口地址：** `GET /permission/list`

**权限：** `system:permission:list`

---

### 4.3 分页查询权限列表

**接口地址：** `GET /permission/page`

**权限：** `system:permission:list`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| permissionName | String | 否 | 权限名称 |
| status | Integer | 否 | 状态 |
| pageNum | int | 否 | 页码 |
| pageSize | int | 否 | 每页大小 |

---

### 4.4 获取权限树

**接口地址：** `GET /permission/tree`

**权限：** `system:permission:list`

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "permissionName": "系统管理",
      "permissionKey": "system",
      "children": [
        {
          "id": 2,
          "permissionName": "用户管理",
          "permissionKey": "system:user"
        }
      ]
    }
  ]
}
```

---

### 4.5 新增权限

**接口地址：** `POST /permission`

**权限：** `system:permission:add`

**请求参数：**
```json
{
  "permissionName": "string",
  "permissionKey": "string",
  "parentId": 0,
  "sort": 1,
  "status": 1
}
```

---

### 4.6 更新权限

**接口地址：** `PUT /permission`

**权限：** `system:permission:edit`

---

### 4.7 删除权限

**接口地址：** `DELETE /permission/{id}`

**权限：** `system:permission:delete`

---

### 4.8 批量删除权限

**接口地址：** `DELETE /permission/batch`

**权限：** `system:permission:delete`

---

### 4.9 修改权限状态

**接口地址：** `PUT /permission/{id}/status`

**权限：** `system:permission:edit`

---

## 5. 菜单管理服务 (Menu Service)

### 5.1 获取菜单详情

**接口地址：** `GET /menu/{id}`

**权限：** `system:menu:query`

---

### 5.2 获取所有菜单

**接口地址：** `GET /menu/list`

**权限：** `system:menu:list`

---

### 5.3 获取菜单树

**接口地址：** `GET /menu/tree`

**权限：** `system:menu:list`

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "menuName": "系统管理",
      "path": "/system",
      "children": [
        {
          "id": 2,
          "menuName": "用户管理",
          "path": "/system/user"
        }
      ]
    }
  ]
}
```

---

### 5.4 获取路由菜单

**接口地址：** `GET /menu/routers`

**描述：** 获取当前用户的路由菜单，用于前端动态路由

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "menuName": "系统管理",
      "path": "/system",
      "component": "Layout",
      "children": []
    }
  ]
}
```

---

### 5.5 新增菜单

**接口地址：** `POST /menu`

**权限：** `system:menu:add`

**请求参数：**
```json
{
  "menuName": "string",
  "parentId": 0,
  "orderNum": 1,
  "path": "string",
  "component": "string",
  "menuType": "M",
  "visible": 1,
  "status": 1,
  "perms": "string",
  "icon": "string"
}
```

---

### 5.6 更新菜单

**接口地址：** `PUT /menu`

**权限：** `system:menu:edit`

---

### 5.7 删除菜单

**接口地址：** `DELETE /menu/{id}`

**权限：** `system:menu:delete`

---

### 5.8 修改菜单状态

**接口地址：** `PUT /menu/{id}/status`

**权限：** `system:menu:edit`

---

### 5.9 获取角色菜单ID列表

**接口地址：** `GET /menu/role/{roleId}`

**权限：** `system:menu:query`

---

### 5.10 分配角色菜单

**接口地址：** `POST /menu/role/{roleId}`

**权限：** `system:menu:edit`

**请求参数：**
```json
[1, 2, 3, 4, 5]
```

---

## 6. 部门管理服务 (Dept Service)

### 6.1 获取部门详情

**接口地址：** `GET /dept/{id}`

**权限：** `system:dept:query`

---

### 6.2 获取所有部门

**接口地址：** `GET /dept/list`

**权限：** `system:dept:list`

---

### 6.3 获取部门树

**接口地址：** `GET /dept/tree`

**权限：** `system:dept:list`

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "id": 100,
      "deptName": "总公司",
      "parentId": 0,
      "children": [
        {
          "id": 101,
          "deptName": "研发部",
          "parentId": 100
        }
      ]
    }
  ]
}
```

---

### 6.4 新增部门

**接口地址：** `POST /dept`

**权限：** `system:dept:add`

**请求参数：**
```json
{
  "deptName": "string",
  "parentId": 100,
  "orderNum": 1,
  "leader": "string",
  "phone": "string",
  "email": "string",
  "status": 1
}
```

---

### 6.5 更新部门

**接口地址：** `PUT /dept`

**权限：** `system:dept:edit`

---

### 6.6 删除部门

**接口地址：** `DELETE /dept/{id}`

**权限：** `system:dept:delete`

---

### 6.7 修改部门状态

**接口地址：** `PUT /dept/{id}/status`

**权限：** `system:dept:edit`

---

## 7. 内容管理服务 (Content Service)

### 7.1 获取内容列表

**接口地址：** `GET /content`

**描述：** 获取所有内容信息列表

---

### 7.2 分页获取内容列表

**接口地址：** `GET /content/page`

**请求参数：**
| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| page | int | 否 | 1 | 当前页码 |
| size | int | 否 | 10 | 每页大小 |

---

### 7.3 根据ID获取内容

**接口地址：** `GET /content/{id}`

**描述：** 获取内容详情，同时增加浏览量

---

### 7.4 根据分类ID获取内容

**接口地址：** `GET /content/category/{categoryId}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| categoryId | Long | 分类ID |

---

### 7.5 根据作者ID获取内容

**接口地址：** `GET /content/author/{authorId}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| authorId | Long | 作者ID |

---

### 7.6 根据内容类型获取内容

**接口地址：** `GET /content/type/{type}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| type | Integer | 内容类型 |

---

### 7.7 创建内容

**接口地址：** `POST /content`

**权限：** `content:add`

**请求参数：**
```json
{
  "title": "string",
  "content": "string",
  "summary": "string",
  "coverImage": "string",
  "categoryId": 1,
  "authorId": 1,
  "type": 1,
  "status": 1
}
```

---

### 7.8 更新内容

**接口地址：** `PUT /content/{id}`

**权限：** `content:edit`

---

### 7.9 删除内容

**接口地址：** `DELETE /content/{id}`

**权限：** `content:delete`

---

### 7.10 点赞内容

**接口地址：** `POST /content/{id}/like`

**描述：** 增加内容点赞数

---

### 7.11 评论内容

**接口地址：** `POST /content/{id}/comment`

**描述：** 增加内容评论数

---

## 8. 评论管理服务 (Comment Service)

### 8.1 获取评论列表

**接口地址：** `GET /comment`

---

### 8.2 分页获取评论列表

**接口地址：** `GET /comment/page`

**请求参数：**
| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| page | int | 否 | 1 | 当前页码 |
| size | int | 否 | 10 | 每页大小 |

---

### 8.3 根据ID获取评论

**接口地址：** `GET /comment/{id}`

---

### 8.4 根据内容ID获取评论

**接口地址：** `GET /comment/content/{contentId}`

**描述：** 获取指定内容的所有评论

---

### 8.5 根据用户ID获取评论

**接口地址：** `GET /comment/user/{userId}`

**描述：** 获取用户发表的所有评论

---

### 8.6 获取子评论列表

**接口地址：** `GET /comment/parent/{parentId}`

**描述：** 获取指定评论的回复列表

---

### 8.7 创建评论

**接口地址：** `POST /comment`

**请求参数：**
```json
{
  "contentId": 1,
  "userId": 1,
  "parentId": 0,
  "content": "string"
}
```

---

### 8.8 更新评论

**接口地址：** `PUT /comment/{id}`

---

### 8.9 删除评论

**接口地址：** `DELETE /comment/{id}`

---

### 8.10 点赞评论

**接口地址：** `POST /comment/{id}/like`

---

## 9. 支付服务 (Pay Service)

### 9.1 根据用户ID获取支付记录

**接口地址：** `GET /pay/user/{userId}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |

---

### 9.2 创建支付订单

**接口地址：** `POST /pay`

**请求参数：**
```json
{
  "userId": 1,
  "orderNo": "string",
  "amount": 100.00,
  "payType": 1,
  "subject": "string"
}
```

---

### 9.3 更新支付状态

**接口地址：** `PUT /pay/status`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 是 | 支付订单号 |
| status | Integer | 是 | 支付状态 |
| tradeNo | String | 否 | 交易流水号 |

---

### 9.4 根据订单号获取支付记录

**接口地址：** `GET /pay/order/{orderNo}`

---

### 9.5 获取所有支付记录

**接口地址：** `GET /pay`

---

## 10. 消息服务 (Message Service)

### 10.1 根据接收者ID获取消息

**接口地址：** `GET /message/receiver/{receiverId}`

---

### 10.2 根据接收者和状态获取消息

**接口地址：** `GET /message/receiver/{receiverId}/status/{status}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| receiverId | Long | 接收者ID |
| status | Integer | 消息状态(0未读 1已读) |

---

### 10.3 发送消息

**接口地址：** `POST /message`

**请求参数：**
```json
{
  "senderId": 1,
  "receiverId": 2,
  "title": "string",
  "content": "string",
  "type": 1
}
```

---

### 10.4 标记消息已读

**接口地址：** `PUT /message/read/{id}`

---

### 10.5 批量标记消息已读

**接口地址：** `PUT /message/read/batch`

**请求参数：**
```json
[1, 2, 3, 4, 5]
```

---

### 10.6 获取未读消息数量

**接口地址：** `GET /message/unread/count/{receiverId}`

---

### 10.7 删除消息

**接口地址：** `DELETE /message/{id}`

---

## 11. 搜索服务 (Search Service)

### 11.1 保存搜索历史

**接口地址：** `POST /search`

**请求参数：**
```json
{
  "userId": 1,
  "keyword": "string",
  "type": 1,
  "resultCount": 10
}
```

---

### 11.2 获取用户搜索历史

**接口地址：** `GET /search/history/{userId}`

---

### 11.3 获取热门搜索词

**接口地址：** `GET /search/hot`

**请求参数：**
| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| limit | Integer | 否 | 10 | 返回数量 |

---

### 11.4 清空搜索历史

**接口地址：** `DELETE /search/history/{userId}`

---

### 11.5 搜索内容

**接口地址：** `GET /search/content`

**请求参数：**
| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| keyword | String | 是 | - | 搜索关键词 |
| type | Integer | 否 | 1 | 搜索类型 |

**响应示例：**
```json
{
  "code": 200,
  "data": 10
}
```

---

## 12. 统计服务 (Stat Service)

### 12.1 记录统计数据

**接口地址：** `POST /stat`

**请求参数：**
```json
{
  "type": 1,
  "statDate": "2024-01-01",
  "statValue": 100
}
```

---

### 12.2 根据类型和日期范围获取统计

**接口地址：** `GET /stat/range`

**请求参数：**
| 参数名 | 类型 | 必填 | 格式 | 说明 |
|--------|------|------|------|------|
| type | Integer | 是 | - | 统计类型 |
| startDate | LocalDate | 是 | yyyy-MM-dd | 开始日期 |
| endDate | LocalDate | 是 | yyyy-MM-dd | 结束日期 |

---

### 12.3 根据类型和日期获取统计

**接口地址：** `GET /stat`

**请求参数：**
| 参数名 | 类型 | 必填 | 格式 | 说明 |
|--------|------|------|------|------|
| type | Integer | 是 | - | 统计类型 |
| statDate | LocalDate | 是 | yyyy-MM-dd | 统计日期 |

---

### 12.4 获取统计数据汇总

**接口地址：** `GET /stat/sum`

**请求参数：**
| 参数名 | 类型 | 必填 | 格式 | 说明 |
|--------|------|------|------|------|
| type | Integer | 是 | - | 统计类型 |
| startDate | LocalDate | 是 | yyyy-MM-dd | 开始日期 |
| endDate | LocalDate | 是 | yyyy-MM-dd | 结束日期 |

**响应示例：**
```json
{
  "code": 200,
  "data": 1000
}
```

---

### 12.5 获取多类型统计数据

**接口地址：** `POST /stat/multi`

**请求参数：**
```json
[1, 2, 3]
```

**Query参数：**
| 参数名 | 类型 | 必填 | 格式 | 说明 |
|--------|------|------|------|------|
| statDate | LocalDate | 是 | yyyy-MM-dd | 统计日期 |

**响应示例：**
```json
{
  "code": 200,
  "data": {
    "1": 100,
    "2": 200,
    "3": 300
  }
}
```

---

## 13. 推荐服务 (Recommend Service)

### 13.1 根据推荐类型获取内容

**接口地址：** `GET /recommend/type/{type}`

**路径参数：**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| type | Integer | 推荐类型 |

---

### 13.2 添加推荐内容

**接口地址：** `POST /recommend`

**请求参数：**
```json
{
  "contentId": 1,
  "type": 1,
  "sort": 1,
  "status": 1
}
```

---

### 13.3 更新推荐内容

**接口地址：** `PUT /recommend`

---

### 13.4 删除推荐内容

**接口地址：** `DELETE /recommend/{id}`

---

### 13.5 获取所有推荐内容

**接口地址：** `GET /recommend`

---

## 14. 消息队列服务 (Queue Service)

### 14.1 发送消息

**接口地址：** `POST /api/queue/send`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| exchange | String | 是 | 交换机名称 |
| routingKey | String | 是 | 路由键 |

**请求体：** 消息内容对象

---

### 14.2 发送内容消息

**接口地址：** `POST /api/queue/send/content`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| routingKey | String | 是 | 路由键 |

---

### 14.3 发送用户消息

**接口地址：** `POST /api/queue/send/user`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| routingKey | String | 是 | 路由键 |

---

### 14.4 发送评论消息

**接口地址：** `POST /api/queue/send/comment`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| routingKey | String | 是 | 路由键 |

---

### 14.5 发送通知消息

**接口地址：** `POST /api/queue/send/notification`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| routingKey | String | 是 | 路由键 |

---

## 15. 操作日志服务 (Operation Log Service)

### 15.1 查询日志列表

**接口地址：** `GET /system/log/list`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 否 | 模块标题 |
| businessType | String | 否 | 业务类型 |
| status | Integer | 否 | 操作状态 |
| operName | String | 否 | 操作人员 |
| startTime | String | 否 | 开始时间 |
| endTime | String | 否 | 结束时间 |
| pageNum | int | 否 | 页码 |
| pageSize | int | 否 | 每页大小 |

---

### 15.2 根据ID获取日志详情

**接口地址：** `GET /system/log/{id}`

---

### 15.3 删除日志

**接口地址：** `DELETE /system/log/{id}`

---

### 15.4 批量删除日志

**接口地址：** `DELETE /system/log/batch`

**请求参数：**
```json
[1, 2, 3]
```

---

### 15.5 按日期范围删除日志

**接口地址：** `DELETE /system/log/date`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| startTime | String | 是 | 开始时间 |
| endTime | String | 是 | 结束时间 |

---

### 15.6 清空日志

**接口地址：** `DELETE /system/log/clear`

---

### 15.7 根据用户ID获取日志

**接口地址：** `GET /system/log/user/{userId}`

---

### 15.8 根据业务类型获取日志

**接口地址：** `GET /system/log/businessType/{businessType}`

---

### 15.9 根据状态获取日志

**接口地址：** `GET /system/log/status/{status}`

---

### 15.10 获取日志总数

**接口地址：** `GET /system/log/count`

---

### 15.11 按日期范围统计日志数量

**接口地址：** `GET /system/log/count/date`

**请求参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| startTime | String | 是 | 开始时间 |
| endTime | String | 是 | 结束时间 |

---

### 15.12 按状态统计日志数量

**接口地址：** `GET /system/log/count/status/{status}`

---

## 16. 权限控制服务 (System Permission Service)

### 16.1 获取权限列表

**接口地址：** `GET /system/permission/list`

---

### 16.2 获取当前用户权限

**接口地址：** `GET /system/permission/user`

**响应示例：**
```json
{
  "code": 200,
  "data": ["system:user:list", "system:role:list"]
}
```

---

### 16.3 获取当前用户角色

**接口地址：** `GET /system/permission/user/roles`

**响应示例：**
```json
{
  "code": 200,
  "data": ["admin", "editor"]
}
```

---

### 16.4 刷新权限缓存

**接口地址：** `POST /system/permission/refresh`

---

### 16.5 获取角色权限

**接口地址：** `GET /system/permission/role/{roleId}`

---

### 16.6 分配角色权限

**接口地址：** `POST /system/permission/role/{roleId}`

**请求参数：**
```json
[1, 2, 3, 4, 5]
```

---

## 附录

### A. 权限标识说明

| 权限标识 | 说明 |
|----------|------|
| `user:list` | 用户列表查看 |
| `user:view` | 用户详情查看 |
| `user:add` | 用户新增 |
| `user:edit` | 用户编辑 |
| `user:delete` | 用户删除 |
| `system:role:*` | 角色管理相关权限 |
| `system:permission:*` | 权限管理相关权限 |
| `system:menu:*` | 菜单管理相关权限 |
| `system:dept:*` | 部门管理相关权限 |
| `content:add` | 内容新增 |
| `content:edit` | 内容编辑 |
| `content:delete` | 内容删除 |

### B. 数据状态说明

| 状态值 | 说明 |
|--------|------|
| 0 | 禁用/未读/失败 |
| 1 | 启用/已读/成功 |

### C. 内容类型说明

| 类型值 | 说明 |
|--------|------|
| 1 | 文章 |
| 2 | 视频 |
| 3 | 图片 |
| 4 | 音频 |

### D. 支付状态说明

| 状态值 | 说明 |
|--------|------|
| 0 | 待支付 |
| 1 | 支付成功 |
| 2 | 支付失败 |
| 3 | 已退款 |

---

**文档版本：** v1.0  
**更新日期：** 2024-01-01  
**维护团队：** 内容分享平台开发组
