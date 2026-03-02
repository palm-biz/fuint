# 掌商云会员营销系统 - 后端服务

## 项目介绍

掌商云会员营销系统是一套基于 Spring Boot 的会员管理和营销系统，为实体店铺提供完整的会员运营、卡券营销、收银结算等核心业务能力。系统采用 DDD (领域驱动设计) 四层架构，适用于生鲜、零售超市、酒吧、酒店、汽车4S店、鲜花店、奶茶店、甜品店、餐饮店、农家乐等各类实体店铺。

### 核心能力

- **会员管理** - 会员信息管理、等级体系、积分系统
- **卡券系统** - 储值卡、电子券、优惠券、集次卡、计次卡
- **订单处理** - 订单创建、支付、核销、退款
- **商品管理** - 商品信息、库存、SKU管理
- **营销工具** - 短信营销、消息推送、活动配置
- **收银结算** - 多种支付方式、挂单、小票打印
- **多商户支持** - 商户隔离、店铺管理、权限控制

## 技术架构

### 技术栈

- **核心框架**: Spring Boot 2.x
- **持久层**: MyBatis Plus 3.x
- **数据库**: MySQL 5.7/8.0
- **缓存**: Redis
- **安全框架**: Spring Security
- **API文档**: Swagger UI 2.x
- **认证授权**: JWT
- **任务调度**: Quartz
- **日志框架**: Slf4j + Logback
- **连接池**: Druid
- **JSON处理**: Fastjson
- **构建工具**: Maven 3.6+

### 项目结构

```
business/
├── biz-interfaces/                  # 接口层（API暴露、启动入口）
├── biz-application/                 # 应用层（用例编排、CQRS）
├── biz-domain/                      # 领域层（核心业务逻辑）
├── biz-infrastructure/              # 基础设施层（技术实现）
├── biz-framework/                   # 框架层（技术框架封装）
├── biz-common/                      # 公共模块（DTO、枚举、工具类）
├── configure/                       # 配置文件目录
├── db/                             # 数据库脚本
└── pom.xml                         # Maven 父工程配置
```

## DDD 架构设计

### 分层架构与依赖关系

本项目采用 **DDD (领域驱动设计)** 架构，严格遵循**依赖倒置原则**（DIP）和**分层架构**原则：

```
                ┌─────────────────────┐
                │   biz-interfaces    │  接口层/用户界面层
                │  - REST Controller  │  - 暴露HTTP接口
                │  - 启动类(Main)      │  - 参数校验、异常处理
                │  - Swagger配置      │  - 接口适配
                └──────────┬──────────┘
                           │ 依赖
                           ▼
                ┌─────────────────────┐
                │  biz-application    │  应用层
                │  - CommandService   │  - 用例编排（Use Case）
                │  - QueryService     │  - 事务控制
                │  - Command/Query    │  - CQRS实现
                │  - DTO/Assembler    │  - 数据转换
                │  - Executor         │  - 命令执行器
                └──────────┬──────────┘
                           │ 依赖
                           ▼
                ┌─────────────────────┐
                │    biz-domain       │  领域层（核心）
                │  - 聚合根(Aggregate)│  - 纯业务逻辑
                │  - 值对象(VO)       │  - 领域模型
                │  - 领域服务         │  - 业务规则
                │  - 仓储接口         │  - 不依赖框架
                └──────────┬──────────┘
                           │ 依赖
                           ▼
                ┌─────────────────────┐
                │    biz-common       │  公共模块
                │  - DTO              │  - 数据传输对象
                │  - Enum             │  - 枚举定义
                │  - Utils            │  - 工具类
                │  - Constants        │  - 常量定义
                └─────────────────────┘


        ┌──────────────────────────────────┐
        │    biz-infrastructure            │  基础设施层（实现层）
        │  - 仓储实现 (RepositoryImpl)     │  - 实现领域层接口
        │  - Mapper/DAO                    │  - 数据持久化
        │  - PO对象                        │  - 外部服务集成
        └──────────┬───────────────────────┘
                   │ 依赖（实现接口）
                   ▼
        ┌─────────────────────┐
        │    biz-domain       │  依赖倒置（DIP）
        │  - Repository接口   │  基础设施层实现领域层定义的接口
        └─────────────────────┘
                   │ 依赖
                   ▼
        ┌─────────────────────┐
        │   biz-framework     │  框架层（技术基础）
        │  - 异常处理框架     │  - 技术框架封装
        │  - 分页框架         │  - 通用组件
        │  - 日志注解         │  - AOP增强
        └──────────┬──────────┘
                   │ 依赖
                   ▼
        ┌─────────────────────┐
        │    biz-common       │
        └─────────────────────┘
```

### 标准 DDD 依赖链路

```
┌─────────────────────────────────────────────────┐
│              业务逻辑层（自上而下）                │
└─────────────────────────────────────────────────┘

biz-interfaces (接口层)
    │
    └──> biz-application (应用层)
            │
            └──> biz-domain (领域层) ← 核心，不依赖任何业务层
                    │
                    └──> biz-common (公共模块)


┌─────────────────────────────────────────────────┐
│              技术实现层（独立依赖）                │
└─────────────────────────────────────────────────┘

biz-infrastructure (基础设施层)
    │
    ├──> biz-domain (实现仓储接口 - 依赖倒置 DIP)
    │
    ├──> biz-framework (框架层)
    │       │
    │       └──> biz-common
    │
    └──> biz-common


┌─────────────────────────────────────────────────┐
│           运行时依赖注入（Spring容器）            │
└─────────────────────────────────────────────────┘

应用层 (Application)
    │ 运行时注入
    ├──> 领域服务 (Domain Service)
    └──> 仓储实现 (Infrastructure Repository) ← Spring注入具体实现
```

### 依赖原则详解

| 层级 | 依赖方向 | 职责 | 关键原则 |
|------|---------|------|---------|
| **biz-interfaces** | ↓ application | 接口暴露、启动入口 | 依赖应用层 |
| **biz-application** | ↓ domain | 用例编排、CQRS | **只依赖领域层接口，不依赖基础设施层** |
| **biz-domain** | ↓ common | 核心业务逻辑 | **不依赖任何其他业务层** |
| **biz-common** | 无 | 公共组件 | 最底层，被所有层依赖 |
| **biz-infrastructure** | ↑ domain + ↓ framework | 技术实现 | **依赖倒置：实现领域层接口** |
| **biz-framework** | ↓ common | 技术框架封装 | 技术基础，不涉及业务 |

### 核心架构原则

#### 1. **依赖倒置原则（DIP - Dependency Inversion Principle）**

```
领域层定义接口：
┌─────────────────────────┐
│ biz-domain              │
│                         │
│ interface Repository {  │  ← 定义接口
│   Member findById();    │
│ }                       │
└─────────────────────────┘

基础设施层实现接口：
┌─────────────────────────┐
│ biz-infrastructure      │
│                         │
│ class RepositoryImpl    │  ← 实现接口
│   implements Repository │
└─────────────────────────┘

应用层只知道接口：
┌─────────────────────────┐
│ biz-application         │
│                         │
│ @Autowired              │  ← Spring注入实现
│ Repository repository;  │     应用层不知道具体实现
└─────────────────────────┘
```

#### 2. **分层隔离原则**

- **应用层不依赖基础设施层**：通过依赖注入（DI）在运行时获取实现
- **领域层完全独立**：不依赖任何技术框架，可独立测试
- **基础设施层可替换**：换数据库、换框架不影响业务逻辑

#### 3. **单向依赖原则**

```
✅ 正确的依赖方向：
interfaces → application → domain → common
infrastructure → domain (实现接口)
infrastructure → framework → common

❌ 禁止的依赖方向：
domain → infrastructure  ✗
domain → application     ✗
application → infrastructure  ✗
common → 任何业务层      ✗
```

#### 4. **六边形架构（端口与适配器）**

```
        ┌─────────────────────────────────┐
        │        biz-interfaces           │  适配器层（输入）
        │     - REST Controller           │  - 将HTTP请求适配为应用层调用
        └─────────────┬───────────────────┘
                      │
        ┌─────────────▼───────────────────┐
        │      biz-application            │  应用层（端口）
        │     - 定义用例                   │  - 编排领域对象
        └─────────────┬───────────────────┘
                      │
        ┌─────────────▼───────────────────┐
        │        biz-domain               │  领域层（核心）
        │     - 业务规则                   │  - 纯业务逻辑
        │     - 定义Repository接口（端口）│
        └─────────────┬───────────────────┘
                      ▲
        ┌─────────────┴───────────────────┐
        │    biz-infrastructure           │  适配器层（输出）
        │  - 实现Repository接口            │  - 将领域调用适配为数据库操作
        │  - Mapper/DAO                   │
        └─────────────────────────────────┘
```

### 为什么这样设计？

1. **业务逻辑稳定**：领域层不依赖技术，技术变更不影响业务
2. **可测试性强**：领域层可以独立单元测试，不需要启动数据库
3. **可替换性好**：可以轻松替换数据库、框架、UI层
4. **职责清晰**：每层职责明确，易于维护和扩展
5. **符合SOLID原则**：特别是依赖倒置（DIP）和单一职责（SRP）

## 模块详解

### 1. biz-domain（领域层）

领域层是系统的核心，包含纯业务逻辑，不依赖任何外部框架。

```
biz-domain/
├── account/                        # 账户领域
│   ├── model/                     # 领域模型（聚合根、值对象、枚举）
│   ├── repository/                # 仓储接口
│   └── service/                   # 领域服务
│
├── member/                         # 会员领域
│   ├── model/
│   ├── repository/
│   └── service/
│
├── order/                          # 订单领域
│   ├── model/
│   ├── repository/
│   └── service/
│
├── goods/                          # 商品领域
│   ├── model/
│   ├── repository/
│   └── service/
│
├── coupon/                         # 卡券领域
│   ├── model/
│   ├── repository/
│   └── service/
│
├── cart/                           # 购物车领域
│   ├── model/
│   ├── repository/
│   └── service/
│
├── store/                          # 店铺领域
│   ├── model/
│   ├── repository/
│   └── service/
│
└── merchant/                       # 商户领域
    ├── model/
    ├── repository/
    └── service/
```

**领域层设计原则：**
1. 聚合根封装完整的业务规则和不变性
2. 值对象是不可变的，保证线程安全
3. 领域服务处理跨聚合的业务逻辑
4. 仓储接口只定义在领域层，实现在基础设施层

### 2. biz-application（应用层）

应用层负责用例编排，实现 CQRS 模式（命令查询职责分离）。

```
biz-application/
├── account/                        # 账户应用服务
│   ├── command/                   # 命令对象（写操作）
│   ├── query/                     # 查询对象（读操作）
│   └── service/                   # 应用服务（CommandService、QueryService）
│
├── member/                         # 会员应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── order/                          # 订单应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── goods/                          # 商品应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── coupon/                         # 卡券应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── cart/                           # 购物车应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── store/                          # 店铺应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
├── merchant/                       # 商户应用服务
│   ├── command/
│   ├── query/
│   └── service/
│
└── common/service/                # 兼容层（Facade模式）
    ├── AccountService.java
    ├── MemberService.java
    └── impl/                      # Facade实现，注入DDD服务
```

**应用层设计原则：**
1. Command 处理写操作（创建、更新、删除）
2. Query 处理读操作（查询、列表）
3. 事务边界在应用服务层
4. 应用服务协调领域对象完成用例
5. 通过 Facade 模式保持向后兼容

### 3. biz-infrastructure（基础设施层）

基础设施层实现技术细节，包括数据持久化、外部服务集成等。

```
biz-infrastructure/
├── mapper/                         # MyBatis Mapper接口 + XML映射文件
│   ├── TAccountMapper.java/xml
│   ├── MtMemberMapper.java/xml
│   ├── MtOrderMapper.java/xml
│   ├── MtGoodsMapper.java/xml
│   ├── MtCouponMapper.java/xml
│   ├── MtCartMapper.java/xml
│   ├── MtStoreMapper.java/xml
│   └── MtMerchantMapper.java/xml
│
├── model/                          # 数据模型（PO - Persistent Object）
│   ├── TAccount.java
│   ├── MtMember.java
│   ├── MtOrder.java
│   ├── MtGoods.java
│   ├── MtCoupon.java
│   ├── MtCart.java
│   ├── MtStore.java
│   └── MtMerchant.java
│
├── repository/                     # 仓储实现（实现领域层仓储接口）
│   ├── AccountRepositoryImpl.java
│   ├── MemberRepositoryImpl.java
│   ├── OrderRepositoryImpl.java
│   ├── GoodsRepositoryImpl.java
│   ├── CouponRepositoryImpl.java
│   ├── CartRepositoryImpl.java
│   ├── StoreRepositoryImpl.java
│   └── MerchantRepositoryImpl.java
│
└── config/                         # 技术配置类
    ├── DataSourceConfig.java
    ├── RedisConfig.java
    └── MyBatisPlusConfig.java
```

**基础设施层职责：**
1. 实现领域层定义的仓储接口
2. 处理领域对象与数据模型的转换（Domain ↔ PO）
3. 封装数据库访问逻辑
4. 集成外部服务（短信、支付等）

### 4. biz-common（公共模块）

```
biz-common/
├── dto/                            # 数据传输对象
│   ├── account/
│   ├── member/
│   ├── order/
│   ├── goods/
│   ├── coupon/
│   ├── cart/
│   ├── store/
│   └── merchant/
│
├── enums/                          # 枚举定义
│   ├── StatusEnum.java
│   ├── YesOrNoEnum.java
│   ├── OrderSettingEnum.java
│   └── PayTypeEnum.java
│
├── utils/                          # 工具类
│   ├── StringUtil.java
│   ├── DateUtil.java
│   └── HttpUtil.java
│
├── exception/                      # 异常定义
│   └── BusinessCheckException.java
│
└── constants/                      # 常量定义
    ├── Constants.java
    └── CacheConstants.java
```

### 5. biz-interfaces（接口层）

```
biz-interfaces/
├── Application.java                # Spring Boot 启动类
│
├── module/                         # 接口模块
│   ├── backendApi/                # 后台管理接口
│   │   └── controller/
│   │       ├── BackendAccountController.java
│   │       ├── BackendMemberController.java
│   │       ├── BackendOrderController.java
│   │       ├── BackendGoodsController.java
│   │       ├── BackendCouponController.java
│   │       ├── BackendStoreController.java
│   │       └── BackendMerchantController.java
│   │
│   └── clientApi/                 # 客户端接口
│       └── controller/
│           ├── ClientMemberController.java
│           ├── ClientOrderController.java
│           ├── ClientGoodsController.java
│           ├── ClientCouponController.java
│           └── ClientCartController.java
│
└── resources/                      # 配置资源
    ├── application.yml
    └── logback-spring.xml
```

### 6. biz-framework（框架层）

```
biz-framework/
├── annoation/                      # 自定义注解
│   └── OperationServiceLog.java
│
├── aspect/                         # AOP切面
│   └── OperationLogAspect.java
│
├── exception/                      # 异常处理
│   ├── BusinessCheckException.java
│   └── GlobalExceptionHandler.java
│
├── pagination/                     # 分页框架
│   ├── PaginationRequest.java
│   └── PaginationResponse.java
│
├── security/                       # 安全框架
│   ├── jwt/                       # JWT认证
│   └── config/                    # Security配置
│
└── config/                         # 框架配置
    ├── SwaggerConfig.java
    ├── WebMvcConfig.java
    └── CorsConfig.java
```

## 核心业务模块

### 已完成 DDD 重构的模块

1. **Account（账户模块）**
   - 聚合根：Account
   - 值对象：AccountId, AccountName, Password, AccountStatus
   - 领域服务：PasswordEncryptionService
   - 应用服务：AccountCommandService, AccountQueryService, AccountAuthService

2. **Member（会员模块）**
   - 聚合根：Member
   - 值对象：MemberId, MemberLevel, Point, Balance, MemberStatus
   - 领域服务：MemberValidationService, PointCalculationService
   - 应用服务：MemberCommandService, MemberQueryService

3. **Order（订单模块）**
   - 聚合根：Order
   - 实体：OrderItem
   - 值对象：OrderNo, Amount, OrderStatus, PaymentMethod
   - 领域服务：OrderCalculationService, OrderValidationService
   - 应用服务：OrderCommandService, OrderQueryService

4. **Goods（商品模块）**
   - 聚合根：Goods
   - 实体：GoodsSku
   - 值对象：GoodsId, GoodsNo, GoodsPrice, GoodsStock, GoodsStatus
   - 领域服务：GoodsPriceService, GoodsValidationService
   - 应用服务：GoodsCommandService, GoodsQueryService

5. **Coupon（卡券模块）**
   - 聚合根：Coupon
   - 值对象：CouponId, CouponAmount, CouponType, CouponStatus
   - 领域服务：CouponValidationService
   - 应用服务：CouponCommandService, CouponQueryService

6. **Cart（购物车模块）**
   - 实体：CartItem
   - 值对象：CartId, CartQuantity, CartStatus
   - 领域服务：CartValidationService
   - 应用服务：CartCommandService, CartQueryService

7. **Store（店铺模块）**
   - 聚合根：Store
   - 值对象：StoreId, Location, PaymentConfig, BankAccount, StoreStatus
   - 领域服务：StoreValidationService
   - 应用服务：StoreCommandService, StoreQueryService

8. **Merchant（商户模块）**
   - 聚合根：Merchant
   - 值对象：MerchantId, MerchantNo, WechatConfig, SettleRate, MerchantStatus
   - 领域服务：MerchantValidationService
   - 应用服务：MerchantCommandService, MerchantQueryService

### 业务模块清单

系统按**领域**分组，共 **13 个领域**，涵盖会员营销系统的完整业务场景。

```
identity                              # 身份域
 ├── user                             # 用户
 ├── account                          # 账户
 └── role                             # 权限

member                                # 会员域

product                               # 商品域
 ├── product                          # 商品
 ├── category                         # 分类
 └── inventory                        # 库存

order                                 # 订单域
 ├── order                            # 订单
 ├── order_item                       # 订单项
 ├── cart                             # 购物车
 ├── aftersale                        # 售后
 └── writeoff                         # 核销

payment                               # 支付域
 ├── payment                          # 支付
 └── refund                           # 退款

marketing                             # 营销域
 ├── coupon                           # 优惠券
 ├── card                             # 卡券
 ├── point                            # 积分
 ├── commission                       # 佣金
 └── activity                         # 活动

finance                               # 财务域
 ├── asset                            # 资产
 ├── balance                          # 余额
 ├── transaction                      # 资金流水
 └── recharge                         # 充值

store                                 # 店铺域
 ├── store                            # 店铺
 ├── merchant                         # 商户
 └── staff                            # 员工

logistics                             # 物流域
 ├── address                          # 地址
 ├── region                           # 地区
 └── shipment                         # 物流

reservation                           # 预约域

content                               # 内容域

message                               # 消息域

system                                # 系统域（配置）

print                                 # 打印域

integration                           # 集成域（第三方平台：微信、支付宝、银联、短信、OSS）
```

## 环境要求

- **JDK**: 1.8+
- **MySQL**: 5.7 或 8.0
- **Redis**: 3.0+
- **Maven**: 3.6+
- **IDE**: IntelliJ IDEA (推荐安装 Lombok 插件)

## 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd fuint
```

### 2. 数据库初始化

```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE palmbiz DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入数据库文件
mysql -u root -p palmbiz < db/palmbiz-db.sql
```

### 3. 配置修改

修改 `configure/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/palmbiz?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

  redis:
    host: localhost
    port: 6379
    password:
    database: 0
```

### 4. 构建项目

```bash
# 安装依赖
mvn clean install -DskipTests

# 打包
mvn clean package -DskipTests
```

### 5. 启动应用

**开发环境：**

```bash
# 方式1：使用Maven
mvn spring-boot:run

# 方式2：使用IDE
# 直接运行 biz-application 模块的 Application 主类
```

**生产环境：**

```bash
cd biz-application/target

# 启动服务
nohup java -Dfile.encoding=UTF-8 \
  -Xmx2048m -Xms2048m -Xss256k -Xmn1024m \
  -jar biz-application-1.0.0.jar > app.log 2>&1 &

# 查看日志
tail -f app.log
```

### 6. 访问系统

- **API文档**: http://localhost:8080/swagger-ui.html
- **健康检查**: http://localhost:8080/actuator/health

> 💡 **提示**: 首次启动会自动初始化数据，默认管理员账号: admin / 123456

## API 文档

系统集成了 Swagger UI，启动后访问 `http://localhost:8080/swagger-ui.html` 查看完整 API 文档。

### 主要 API 模块

- **/backendApi/account/** - 账户管理接口
- **/backendApi/member/** - 会员管理接口
- **/backendApi/order/** - 订单管理接口
- **/backendApi/goods/** - 商品管理接口
- **/backendApi/coupon/** - 卡券管理接口
- **/backendApi/store/** - 店铺管理接口
- **/backendApi/merchant/** - 商户管理接口
- **/clientApi/member/** - 会员端接口
- **/clientApi/cart/** - 购物车接口
- **/clientApi/order/** - 订单接口

## 开发规范

### 代码结构规范

1. **领域层（biz-domain）**
   - 只包含纯业务逻辑，不依赖任何框架
   - 聚合根封装完整的业务规则
   - 值对象保证不可变性
   - 领域服务处理跨聚合的业务逻辑
   - 仓储接口只定义，不实现

2. **应用层（biz-application）**

   应用层按**业务用例（Use Case）**组织，每个模块包含以下子目录：

   ```
   application
   └── {module}
       ├── command/     # 写操作命令对象（Create/Update/Delete）
       ├── query/       # 查询条件对象（PageQuery、DetailQuery）
       ├── dto/         # 数据传输对象（返回给接口层的视图数据）
       ├── assembler/   # 转换器（Domain ⇆ DTO 互转）
       ├── executor/    # 命令/查询执行器（拆分大型用例，避免 Service 臃肿）
       └── service/     # 应用服务入口（CommandService / QueryService）
   ```

   各子目录职责：

   | 目录 | 职责 | 示例 |
   |------|------|------|
   | **command** | 封装写操作的输入参数，表达用户意图 | `CreateOrderCommand`, `UpdateMemberCommand` |
   | **query** | 封装查询条件参数 | `OrderPageQuery`, `MemberDetailQuery` |
   | **dto** | 应用层输出的数据结构，返回给接口层 | `OrderDTO`, `MemberDTO` |
   | **assembler** | 领域对象与 DTO 之间的转换，保持层间隔离 | `OrderAssembler.toDTO(Order)` |
   | **executor** | 将单个用例拆分为独立执行器，避免 Service 过大 | `CreateOrderCommandExecutor`, `OrderQueryExecutor` |
   | **service** | 应用服务入口，编排 domain 层，控制事务边界 | `OrderCommandService`, `OrderQueryService` |

   > **核心原则**：应用层按**用例**组织，而不是按技术类型（controller/service/dao）。
   > Service 只负责编排领域对象，不编写业务规则，业务规则由 domain 层的聚合根和领域服务负责。
   > 当 Service 方法过多时，将每个用例抽取为独立的 Executor，保持单一职责。

3. **基础设施层（biz-infrastructure）**
   - 实现仓储接口（依赖倒置）
   - 处理数据持久化
   - 实现领域对象与数据模型的转换
   - 集成外部服务（短信、支付等）

4. **框架层（biz-framework）**
   - 封装技术框架
   - 提供 AOP 增强
   - 统一异常处理
   - 分页、日志等通用能力

5. **接口层（biz-interfaces）**
   - 暴露 REST API
   - 参数校验
   - 异常处理
   - DTO 转换
   - 应用启动入口

6. **公共层（biz-common）**
   - 提供 DTO 定义
   - 枚举、常量定义
   - 工具类
   - 被所有层依赖

### 模块完整目录结构参考

以 `order`（订单）模块为例，展示生产级 DDD 完整结构：

```
biz-domain/
└── domain/order/
    ├── model/
    │   ├── Order.java                    # 聚合根
    │   ├── OrderId.java                  # 值对象（聚合根ID）
    │   ├── OrderNo.java                  # 值对象（订单编号）
    │   └── OrderStatus.java              # 值对象（订单状态）
    ├── repository/
    │   └── OrderRepository.java          # 仓储接口（DIP）
    └── service/
        └── OrderDomainService.java       # 领域服务（跨聚合业务逻辑）

biz-application/
└── application/order/
    ├── command/
    │   ├── CreateOrderCommand.java       # 创建订单命令
    │   ├── PayOrderCommand.java          # 支付订单命令
    │   └── CancelOrderCommand.java       # 取消订单命令
    ├── query/
    │   ├── OrderDetailQuery.java         # 订单详情查询条件
    │   └── OrderPageQuery.java           # 订单分页查询条件
    ├── dto/
    │   ├── OrderDTO.java                 # 订单数据传输对象
    │   └── OrderDetailDTO.java           # 订单详情数据传输对象
    ├── assembler/
    │   └── OrderAssembler.java           # Order(Domain) ⇆ OrderDTO 转换
    ├── executor/
    │   ├── CreateOrderCommandExecutor.java   # 创建订单用例执行器
    │   ├── PayOrderCommandExecutor.java      # 支付订单用例执行器
    │   └── OrderQueryExecutor.java           # 订单查询执行器
    └── service/
        ├── OrderCommandService.java      # 订单命令服务（写操作入口）
        └── OrderQueryService.java        # 订单查询服务（读操作入口）

biz-infrastructure/
└── infrastructure/
    ├── repository/
    │   └── OrderRepositoryImpl.java      # 仓储接口实现（DIP实现）
    └── mapper/
        └── MtOrderMapper.java            # MyBatis Plus Mapper

biz-interfaces/
└── interfaces/
    └── backendApi/controller/
        └── BackendOrderController.java   # REST Controller（调用应用服务）
```

### 命名规范

**领域层（biz-domain）：**
- 聚合根: 直接使用业务名称，如 `Account`, `Member`, `Order`
- 值对象: 业务名称 + 含义，如 `AccountId`, `MemberLevel`, `OrderNo`
- 领域服务: `Xxx` + `Service`，如 `PasswordEncryptionService`

**应用层（biz-application）：**
- 命令对象: `Xxx` + `Command`，如 `CreateOrderCommand`
- 查询对象: `Xxx` + `Query`，如 `OrderPageQuery`
- 数据传输: `Xxx` + `DTO`，如 `OrderDTO`
- 转换器: `Xxx` + `Assembler`，如 `OrderAssembler`
- 执行器: `Xxx` + `CommandExecutor` / `QueryExecutor`，如 `CreateOrderCommandExecutor`
- 应用服务: `Xxx` + `CommandService` / `QueryService`

**基础设施层（biz-infrastructure）：**
- Mapper: `Mt/T` + 业务名 + `Mapper`，如 `MtMemberMapper`
- 数据模型: `Mt/T` + 业务名，如 `MtMember`
- 仓储实现: 业务名 + `RepositoryImpl`

**接口层（biz-interfaces）：**
- Controller: `Backend` / `Client` + 业务名 + `Controller`
- 启动类: `Application`

**框架层（biz-framework）：**
- 注解: `Xxx` + 用途，如 `OperationServiceLog`
- 切面: `Xxx` + `Aspect`，如 `OperationLogAspect`
- 配置: `Xxx` + `Config`，如 `SwaggerConfig`

### Git 提交规范

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Type 类型：**
- `feat`: 新功能
- `fix`: 修复 bug
- `refactor`: 重构（不改变功能）
- `perf`: 性能优化
- `style`: 代码格式调整
- `docs`: 文档更新
- `test`: 测试用例
- `chore`: 构建工具或辅助工具的变动

**示例：**
```bash
feat(member): 新增会员等级自动升级功能

实现会员消费金额达到阈值后自动升级等级
- 新增 MemberLevelUpgradeService
- 添加定时任务检查会员等级
- 发送升级通知消息

Closes #123
```

### 代码注释规范

```java
/**
 * 会员聚合根
 *
 * 封装会员的核心业务逻辑，包括：
 * - 会员等级管理
 * - 积分获取和消耗
 * - 余额充值和支付
 *
 * @author PalmBiz Team
 * @since 1.0.0
 */
public class Member {

    /**
     * 会员升级
     *
     * 根据消费金额或积分判断是否满足升级条件
     *
     * @param consumeAmount 消费金额
     * @return 是否升级成功
     */
    public boolean upgrade(BigDecimal consumeAmount) {
        // 业务逻辑
    }
}
```

### 测试规范

- 单元测试覆盖率目标：核心业务逻辑 > 80%
- 领域层逻辑必须有单元测试
- 应用服务需要集成测试
- 使用 JUnit 5 + Mockito

## 部署指南

### Docker 部署

```dockerfile
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY biz-application/target/biz-application-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# 构建镜像
docker build -t palmbiz-backend:1.0.0 .

# 运行容器
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -v /data/palmbiz/logs:/app/logs \
  --name palmbiz-backend \
  palmbiz-backend:1.0.0
```

### Nginx 配置

```nginx
upstream palmbiz_backend {
    server 127.0.0.1:8080;
}

server {
    listen 80;
    server_name api.palmbiz.cn;

    location / {
        proxy_pass http://palmbiz_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

## 常见问题

### Q: 启动时报数据库连接失败？
A: 检查数据库配置，确保 MySQL 已启动，用户名密码正确，数据库已创建。

### Q: Redis 连接失败？
A: 确保 Redis 服务已启动，检查配置文件中的 host 和 port。

### Q: Swagger 文档无法访问？
A: 检查是否添加了安全配置，确保 `/swagger-ui.html` 路径未被拦截。

### Q: 如何修改端口？
A: 修改 `application.yml` 中的 `server.port` 配置。

### Q: 如何开启/关闭 SQL 日志？
A: 修改 `logback-spring.xml` 中的日志级别：
```xml
<logger name="cloud.palmbiz.infrastructure.mapper" level="DEBUG"/>
```

---

**PalmBiz 会员营销系统 - 基于 DDD 的企业级解决方案**
