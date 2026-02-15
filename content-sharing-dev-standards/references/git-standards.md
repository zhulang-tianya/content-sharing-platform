# Git操作规范

## 目录

1. [分支管理规范](#分支管理规范)
2. [提交规范](#提交规范)
3. [合并规范](#合并规范)
4. [标签规范](#标签规范)
5. [常见问题处理](#常见问题处理)

---

## 分支管理规范

### 分支命名规范

| 分支类型 | 命名格式 | 说明 | 示例 |
|---------|---------|------|------|
| 主分支 | master/main | 生产环境代码 | master |
| 开发分支 | dev/develop | 开发环境代码 | dev |
| 测试分支 | test | 测试环境代码 | test |
| 生产分支 | prod | 生产环境代码 | prod |
| 功能分支 | feature/功能名 | 新功能开发 | feature/user-auth |
| 修复分支 | fix/问题描述 | Bug修复 | fix/login-error |
| 发布分支 | release/版本号 | 版本发布准备 | release/v1.2.0 |
| 热修复分支 | hotfix/问题描述 | 紧急修复 | hotfix/security-patch |

### 分支流程图

```
                    ┌─────────────────────────────────────────────┐
                    │                  master                      │
                    │              (生产环境稳定版)                  │
                    └─────────────────────┬───────────────────────┘
                                          │
                    ┌─────────────────────▼───────────────────────┐
                    │                   prod                       │
                    │              (生产环境最新版)                  │
                    └─────────────────────┬───────────────────────┘
                                          │
                    ┌─────────────────────▼───────────────────────┐
                    │                   test                       │
                    │              (测试环境)                       │
                    └─────────────────────┬───────────────────────┘
                                          │
                    ┌─────────────────────▼───────────────────────┐
                    │                    dev                       │
                    │              (开发环境)                       │
                    └─────────────────────┬───────────────────────┘
                                          │
          ┌───────────────────────────────┼───────────────────────────────┐
          │                               │                               │
┌─────────▼─────────┐         ┌─────────▼─────────┐         ┌─────────▼─────────┐
│ feature/user-auth │         │ feature/payment   │         │ fix/login-error   │
│   (功能分支)       │         │   (功能分支)       │         │   (修复分支)       │
└───────────────────┘         └───────────────────┘         └───────────────────┘
```

### 分支操作命令

```bash
# 创建并切换到新分支
git checkout -b feature/new-feature

# 从远程分支创建本地分支
git checkout -b feature/new-feature origin/feature/new-feature

# 推送新分支到远程
git push -u origin feature/new-feature

# 切换分支
git checkout dev

# 删除本地分支
git branch -d feature/old-feature

# 删除远程分支
git push origin --delete feature/old-feature

# 查看所有分支
git branch -a

# 查看分支追踪关系
git branch -vv
```

---

## 提交规范

### Commit Message 格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Type 类型说明

| Type | 说明 | 示例 |
|------|------|------|
| feat | 新功能 | feat: 新增用户登录功能 |
| fix | Bug修复 | fix: 修复登录验证码失效问题 |
| docs | 文档更新 | docs: 更新API文档 |
| style | 代码格式（不影响功能） | style: 格式化代码缩进 |
| refactor | 重构（不新增功能、不修复Bug） | refactor: 重构用户服务代码结构 |
| perf | 性能优化 | perf: 优化数据库查询性能 |
| test | 测试相关 | test: 添加用户服务单元测试 |
| chore | 构建/工具相关 | chore: 更新Maven依赖版本 |
| revert | 回滚提交 | revert: 回滚用户登录功能 |

### Scope 范围说明

| Scope | 说明 |
|-------|------|
| user | 用户模块 |
| content | 内容模块 |
| comment | 评论模块 |
| pay | 支付模块 |
| gateway | 网关模块 |
| framework | 框架模块 |
| entity | 实体模块 |
| common | 公共模块 |

### Subject 主题要求

- 使用简洁明了的语言描述
- 不超过50个字符
- 使用动词开头，第一人称现在时
- 结尾不加句号

### Commit Message 示例

```bash
# 新功能
git commit -m "feat(user): 新增用户注册功能"

# Bug修复
git commit -m "fix(gateway): 修复路由转发丢失请求头问题"

# 重构
git commit -m "refactor(entity): 创建独立实体模块，优化架构设计

架构优化：
- 新建 content-entity 模块统一管理所有共享实体类
- 将 User, Role, Dept, Menu, Permission 迁移到 entity 模块
- 删除分散在各模块中的重复实体类定义
- 更新所有模块的依赖关系和导入路径

设计原则：
- 单一职责：每个模块职责清晰
- 依赖倒置：业务模块依赖实体模块
- 开闭原则：新增实体只需修改entity模块"

# 文档更新
git commit -m "docs: 更新部署文档，添加Kubernetes配置说明"

# 性能优化
git commit -m "perf(content): 优化内容列表查询，添加索引"
```

### 提交最佳实践

```bash
# 1. 提交前检查
git status
git diff

# 2. 暂存相关文件
git add src/main/java/com/content/user/

# 3. 提交（不要一次性提交过多文件）
git commit -m "feat(user): 新增用户头像上传功能"

# 4. 推送前拉取最新代码
git pull origin dev --rebase

# 5. 推送到远程
git push origin dev
```

---

## 合并规范

### 合并流程

```
开发完成后：
feature → dev → test → prod
```

### 合并命令

```bash
# 1. 确保当前分支是最新的
git checkout dev
git pull origin dev

# 2. 合并功能分支到开发分支
git merge feature/new-feature --no-ff -m "merge: 合并用户认证功能到dev"

# 3. 解决冲突（如果有）
# 手动解决冲突后
git add .
git commit -m "merge: 解决合并冲突"

# 4. 推送到远程
git push origin dev

# 5. 合并dev到test
git checkout test
git merge dev -m "merge: 合并dev到test"
git push origin test

# 6. 合并test到prod
git checkout prod
git merge test -m "merge: 合并test到prod"
git push origin prod
```

### 合并冲突处理

```bash
# 查看冲突文件
git status

# 查看冲突内容
git diff

# 使用IDE解决冲突后
git add <resolved-file>

# 继续合并
git merge --continue

# 或者中止合并
git merge --abort
```

### 变基操作（Rebase）

```bash
# 将当前分支变基到目标分支
git checkout feature/new-feature
git rebase dev

# 解决冲突后继续
git add .
git rebase --continue

# 跳过当前提交
git rebase --skip

# 中止变基
git rebase --abort
```

---

## 标签规范

### 标签命名规范

| 标签类型 | 格式 | 说明 | 示例 |
|---------|------|------|------|
| 正式版本 | v主版本.次版本.修订号 | 正式发布版本 | v1.0.0, v1.2.3 |
| 预发布版本 | v主版本.次版本.修订号-标识 | 测试版本 | v1.0.0-beta.1, v1.0.0-rc.1 |
| 开发版本 | v主版本.次版本.修订号-dev | 开发版本 | v1.0.0-dev.1 |

### 版本号规则

- **主版本号**：不兼容的API变更
- **次版本号**：向下兼容的功能新增
- **修订号**：向下兼容的问题修复

### 标签操作命令

```bash
# 创建轻量标签
git tag v1.0.0

# 创建附注标签（推荐）
git tag -a v1.0.0 -m "Release v1.0.0: 初始版本发布

功能列表：
- 用户认证与授权
- 内容管理
- 评论系统
- 支付集成"

# 推送标签到远程
git push origin v1.0.0

# 推送所有标签
git push origin --tags

# 删除本地标签
git tag -d v1.0.0

# 删除远程标签
git push origin --delete v1.0.0

# 查看所有标签
git tag -l

# 查看标签详情
git show v1.0.0
```

---

## 常见问题处理

### 撤销操作

```bash
# 撤销工作区修改
git restore <file>
git checkout -- <file>  # 旧语法

# 撤销暂存区
git restore --staged <file>
git reset HEAD <file>  # 旧语法

# 撤销最近一次提交（保留修改）
git reset --soft HEAD~1

# 撤销最近一次提交（丢弃修改）
git reset --hard HEAD~1

# 修改最近一次提交信息
git commit --amend -m "新的提交信息"

# 撤销已推送的提交
git revert <commit-hash>
```

### 文件恢复

```bash
# 从Git历史恢复已删除的文件
git checkout <commit-hash> -- <file-path>

# 恢复整个目录
git restore <directory-path>

# 从特定提交恢复文件
git show <commit-hash>:<file-path> > <file-path>
```

### 储藏操作（Stash）

```bash
# 储藏当前修改
git stash

# 带描述的储藏
git stash save "WIP: 用户认证功能"

# 查看储藏列表
git stash list

# 应用最近的储藏
git stash pop

# 应用指定储藏
git stash apply stash@{0}

# 删除储藏
git stash drop stash@{0}

# 清空所有储藏
git stash clear
```

### 清理操作

```bash
# 删除未跟踪的文件（预览）
git clean -n

# 删除未跟踪的文件
git clean -f

# 删除未跟踪的文件和目录
git clean -fd

# 删除被忽略的文件
git clean -fX
```

### 历史查看

```bash
# 查看提交历史
git log --oneline -20

# 查看文件修改历史
git log --follow -p <file-path>

# 查看特定作者的提交
git log --author="username"

# 查看特定时间段的提交
git log --since="2024-01-01" --until="2024-12-31"

# 查看文件每次提交的差异
git log -p <file-path>

# 图形化显示分支历史
git log --graph --oneline --all
```

---

## .gitignore 配置规范

### 标准配置

```gitignore
# 编译输出
target/
build/
*.class
*.jar
*.war

# IDE配置
.idea/
*.iml
.vscode/
*.swp
*.swo

# 日志文件
logs/
*.log

# 系统文件
.DS_Store
Thumbs.db

# 敏感配置
application-local.yml
application-secret.yml
*.pem
*.key

# 临时文件
*.tmp
*.temp
*.bak

# 依赖目录
node_modules/
.npm/

# 构建工具
.m2/
.gradle/

# Trae IDE配置
.trae/

# 本地开发技能
content-sharing-dev-standards/
content-sharing-dev-standards.skill
content-codegen-skill/
```

### 检查被忽略的文件

```bash
# 检查文件是否被忽略
git check-ignore -v <file-path>

# 查看所有被忽略的文件
git ls-files --others --ignored --exclude-standard
```

---

## 团队协作规范

### 代码提交前检查清单

- [ ] 代码已通过本地编译
- [ ] 代码已通过单元测试
- [ ] 代码已通过代码规范检查
- [ ] 敏感信息已从代码中移除
- [ ] 新代码已添加必要的注释
- [ ] 提交信息符合规范格式
- [ ] 只提交相关的文件变更

### Pull Request 规范

```markdown
## 变更类型
- [ ] 新功能
- [ ] Bug修复
- [ ] 重构
- [ ] 文档更新
- [ ] 其他

## 变更说明
简要描述本次变更的内容和原因

## 关联Issue
Closes #123

## 测试说明
- [ ] 已添加单元测试
- [ ] 已进行集成测试
- [ ] 已进行手动测试

## 影响范围
列出可能受影响的模块或功能

## 截图（如有必要）
添加相关截图
```

### Code Review 规范

1. **代码质量**：检查代码是否符合规范
2. **逻辑正确性**：检查业务逻辑是否正确
3. **安全性**：检查是否存在安全漏洞
4. **性能**：检查是否存在性能问题
5. **可维护性**：检查代码是否易于维护
6. **测试覆盖**：检查是否有足够的测试覆盖
