# Fuint 2.0 架构重构完成说明

## 📊 重构概览

本次重构基于 **DDD(领域驱动设计)** 架构,实现了清晰的分层设计和多租户 SaaS 能力。

## 🏗️ 新架构模块

```
fuint/
├── fuint-boot                  # 🚀 启动模块 (唯一 SpringBootApplication)
│   └── FuintApplication.java  # 启动类
│
├── fuint-common                # 🧰 纯工具 & 常量
│   ├── constants/              # 常量定义
│   ├── enums/                  # 枚举
│   ├── utils/                  # 工具类
│   └── exception/              # 通用异常
│
├── fuint-framework             # 🧱 技术基础设施
│   ├── tenant/                 # ⭐ 多租户核心
│   │   ├── TenantContext       # 租户上下文
│   │   ├── TenantHandler       # MyBatis Plus 租户处理器
│   │   └── TenantInterceptor   # Web 拦截器
│   ├── mybatis/                # MyBatis Plus 配置
│   ├── web/                    # Web 通用能力
│   ├── security/               # 认证鉴权
│   └── response/               # 统一响应
│
├── fuint-domain                # 🧠 领域层 (业务核心)
│   ├── model/                  # 领域实体
│   │   └── merchant/           # 商户聚合根 (示例)
│   ├── repository/             # 仓储接口 (只定义)
│   ├── service/                # 领域服务
│   └── valueobject/            # 值对象
│
├── fuint-infrastructure        # 🗄️ 基础设施实现
│   ├── persistence/            # 持久化实现
│   │   ├── entity/             # DO (数据对象)
│   │   ├── mapper/             # MyBatis Mapper
│   │   ├── converter/          # DO ↔ Entity 转换
│   │   └── repository/         # 仓储实现
│   ├── redis/                  # Redis 实现
│   ├── oss/                    # OSS 实现
│   └── resources/
│       └── db/migration/       # ⭐ Flyway 迁移脚本
│
├── fuint-application           # 🎯 应用层 (用例编排)
│   ├── service/                # 应用服务
│   ├── command/                # 写操作
│   ├── query/                  # 读操作
│   └── assembler/              # DTO ↔ Domain 转换
│
└── fuint-interface             # 🌐 接口层 (REST API)
    ├── web/
    │   ├── backend/            # 后台 API
    │   ├── client/             # C 端 API
    │   └── merchant/           # 商户 API
    └── dto/                    # API DTO
```

## ⭐ 核心特性

### 1. 多租户支持

**实现方式**: 基于 `tenant_id` 的共享数据库隔离

**关键组件**:
- `TenantContext`: ThreadLocal 存储租户信息
- `TenantHandler`: MyBatis Plus 自动注入 `WHERE tenant_id = ?`
- `TenantInterceptor`: 从请求中提取租户 ID

**使用示例**:
```java
// 1. 请求时自动设置租户上下文
// Header: X-Tenant-Id: 1

// 2. MyBatis Plus 自动拦截 SQL
// SELECT * FROM mt_user WHERE id = 1
// 👇 自动转换为
// SELECT * FROM mt_user WHERE id = 1 AND tenant_id = 1

// 3. 忽略租户过滤
TenantContext.executeWithoutTenant(() -> {
    // 查询所有租户数据
});
```

### 2. Flyway 数据库迁移

**位置**: `fuint-infrastructure/src/main/resources/db/migration/`

**命名规范**:
- `V1.0__init_schema.sql` - 初始化数据库
- `V1.1__add_tenant_id.sql` - 添加租户字段
- `V2.0__refactor_user_table.sql` - 重构用户表

**特点**:
- 版本化管理数据库变更
- 支持 PostgreSQL / MySQL
- 自动执行迁移脚本
- 可追溯的变更历史

### 3. DO/Entity 分离

**设计理念**: 数据模型与领域模型解耦

```java
// ❌ 旧方式: Entity 既是领域模型又是数据模型
@Entity
@Table(name = "mt_merchant")
public class Merchant {
    // 混合了业务逻辑和数据库注解
}

// ✅ 新方式: 分离
// 1. 领域实体 (Domain Entity)
public class Merchant {
    // 纯业务逻辑,无数据库依赖
    public void activate() { ... }
}

// 2. 数据对象 (DO)
@TableName("mt_merchant")
public class MerchantDO {
    // 纯数据映射
}

// 3. 转换器
public class MerchantConverter {
    public Merchant toDomain(MerchantDO DO) { ... }
    public MerchantDO toDataObject(Merchant entity) { ... }
}
```

### 4. 依赖倒置原则

**仓储模式**:
```
fuint-domain (定义接口)
    ↓ 定义
MerchantRepository (interface)
    ↑ 实现
fuint-infrastructure (实现接口)
    ↓
MerchantRepositoryImpl
```

**好处**:
- 领域层不依赖基础设施
- 易于测试 (Mock 仓储)
- 符合 DDD 理念

## 🚀 快速开始

### 1. 环境准备

```bash
# 安装 PostgreSQL 14+
brew install postgresql@14

# 创建数据库
createdb fuint_db

# 安装 Redis
brew install redis
redis-server
```

### 2. 配置

编辑 `config/dev/application-dev.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fuint_db
    username: postgres
    password: postgres

  redis:
    host: localhost
    port: 6379
```

### 3. 编译运行

```bash
# 编译
mvn clean install -DskipTests

# 运行 (开发环境)
cd fuint-boot
mvn spring-boot:run -Dspring.profiles.active=dev

# 访问
# API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### 4. 测试多租户

```bash
# 租户1的请求
curl -H "X-Tenant-Id: 1" http://localhost:8080/api/merchant/list

# 租户2的请求 (数据隔离)
curl -H "X-Tenant-Id: 2" http://localhost:8080/api/merchant/list
```

## 📌 模块依赖关系

```
fuint-boot
    ↓ 依赖
fuint-interface
    ↓ 依赖
fuint-application
    ↓ 依赖
fuint-domain  ←←←←  fuint-infrastructure
    ↓                      ↓ 依赖
fuint-common  ←←←←  fuint-framework
```

**规则**:
- ✅ 上层可以依赖下层
- ✅ infrastructure 实现 domain 的接口
- ❌ domain 不能依赖 infrastructure
- ❌ 不能跨层依赖

## 🔄 迁移指南

### 旧代码迁移步骤

1. **保留旧模块** (兼容期)
   - `fuint-application` (旧) 保持不变
   - `fuint-repository` 保持不变
   - `fuint-utils` 保持不变

2. **逐步迁移**
   ```
   旧代码 → 新架构
   Service → Application Service + Domain Service
   Entity → Domain Entity
   Mapper → Infrastructure Mapper + DO
   ```

3. **双轨运行**
   - 新功能用新架构
   - 旧功能逐步重构

## 📚 示例代码

已完成的示例:
- ✅ `Merchant` 领域实体
- ✅ `MerchantRepository` 仓储接口
- ✅ `MerchantRepositoryImpl` 仓储实现
- ✅ `MerchantDO` 数据对象
- ✅ `MerchantConverter` 转换器
- ✅ `TenantContext` 租户上下文
- ✅ `MybatisPlusConfig` 多租户配置

参考这些示例,迁移其他业务模块。

## 🎯 后续任务

- [ ] 迁移 `mt_user` (会员) 模块
- [ ] 迁移 `mt_coupon` (卡券) 模块
- [ ] 迁移 `mt_order` (订单) 模块
- [ ] 完善应用层服务
- [ ] 创建接口层 Controller
- [ ] 编写单元测试
- [ ] 完善 Flyway 脚本 (从现有 SQL 转换)

## 📖 参考资料

- [领域驱动设计 (DDD)](https://www.infoq.cn/article/ddd-in-practice)
- [MyBatis Plus 多租户插件](https://baomidou.com/pages/aef2f2/)
- [Flyway 文档](https://flywaydb.org/documentation/)
- [PostgreSQL 最佳实践](https://www.postgresql.org/docs/)

---

**作者**: FSQ
**日期**: 2026-02-06
**版本**: 2.0.0
