# 实体批量迁移到 DDD 架构 - 完成报告

## 迁移概览

成功将 **54 个业务实体** 从旧架构迁移到新的 DDD 架构。

**源位置**: `/root/GitHub/palm-biz/fuint/fuint-repository/src/main/java/com/fuint/repository/model/`

**目标位置**:
- **领域实体**: `/root/GitHub/palm-biz/fuint/fuint-domain/src/main/java/com/fuint/domain/model/`
- **数据对象 (DO)**: `/root/GitHub/palm-biz/fuint/fuint-infrastructure/src/main/java/com/fuint/infrastructure/persistence/entity/`

---

## 迁移统计

### 按模块分类

| 模块 | 实体数量 | 说明 |
|------|---------|------|
| **user** | 11 个 | 会员相关实体 |
| **coupon** | 8 个 | 卡券相关实体 |
| **order** | 8 个 | 订单相关实体 |
| **goods** | 7 个 | 商品相关实体 |
| **store** | 3 个 | 店铺相关实体 |
| **commission** | 5 个 | 分佣相关实体 |
| **booking** | 3 个 | 预约相关实体 |
| **common** | 8 个 | 通用功能实体 |
| **merchant** | 1 个 | 商户相关实体 |
| **总计** | **54 个** | - |

---

## 详细实体清单

### 1. USER 模块 (11 个实体)

会员相关的核心业务实体：

```
✓ MtUser          - 会员个人信息
✓ MtAddress       - 会员地址
✓ MtUserGrade     - 会员等级
✓ MtUserGroup     - 会员分组
✓ MtUserAction    - 会员行为记录
✓ MtUserBalance   - 会员余额变动
✓ MtUserCoupon    - 会员卡券
✓ MtBalance       - 余额记录
✓ MtPoint         - 积分记录
✓ MtMessage       - 站内消息
✓ MtVerifyCode    - 验证码
```

**包路径**: `com.fuint.domain.model.user`

---

### 2. COUPON 模块 (8 个实体)

卡券管理相关实体：

```
✓ MtCoupon        - 卡券信息
✓ MtCouponGroup   - 卡券组
✓ MtCouponGoods   - 卡券适用商品
✓ MtGive          - 转赠记录
✓ MtGiveItem      - 转赠明细
✓ MtOpenGift      - 开卡礼
✓ MtOpenGiftItem  - 开卡礼明细
✓ MtSendLog       - 发券日志
```

**包路径**: `com.fuint.domain.model.coupon`

---

### 3. ORDER 模块 (8 个实体)

订单处理相关实体：

```
✓ MtOrder          - 订单主表
✓ MtOrderGoods     - 订单商品明细
✓ MtOrderAddress   - 订单地址
✓ MtRefund         - 退款记录
✓ MtCart           - 购物车
✓ MtConfirmLog     - 核销日志
✓ MtSettlement     - 结算记录
✓ MtSettlementOrder- 结算订单关联
```

**包路径**: `com.fuint.domain.model.order`

---

### 4. GOODS 模块 (7 个实体)

商品管理相关实体：

```
✓ MtGoods         - 商品信息
✓ MtGoodsCate     - 商品分类
✓ MtGoodsSku      - 商品 SKU
✓ MtGoodsSpec     - 商品规格
✓ MtStock         - 库存记录
✓ MtStockItem     - 库存明细
✓ MtStoreGoods    - 店铺商品关联
```

**包路径**: `com.fuint.domain.model.goods`

---

### 5. STORE 模块 (3 个实体)

店铺管理相关实体：

```
✓ MtStore         - 店铺信息
✓ MtStaff         - 员工信息
✓ MtPrinter       - 打印机配置
```

**包路径**: `com.fuint.domain.model.store`

---

### 6. COMMISSION 模块 (5 个实体)

分佣提成相关实体：

```
✓ MtCommissionCash     - 分佣提现
✓ MtCommissionLog      - 分佣日志
✓ MtCommissionRule     - 分佣规则
✓ MtCommissionRuleItem - 分佣规则明细
✓ MtCommissionRelation - 分佣关系
```

**包路径**: `com.fuint.domain.model.commission`

---

### 7. BOOKING 模块 (3 个实体)

预约管理相关实体：

```
✓ MtBook          - 预约项目
✓ MtBookCate      - 预约分类
✓ MtBookItem      - 预约明细
```

**包路径**: `com.fuint.domain.model.booking`

---

### 8. COMMON 模块 (8 个实体)

通用功能相关实体：

```
✓ MtArticle           - 文章
✓ MtBanner            - 轮播图
✓ MtRegion            - 地区信息
✓ MtSetting           - 系统配置
✓ MtSmsTemplate       - 短信模板
✓ MtSmsSendedLog      - 短信发送日志
✓ MtUploadShippingLog - 物流上传日志
✓ MtInvoice           - 发票信息
```

**包路径**: `com.fuint.domain.model.common`

---

### 9. MERCHANT 模块 (1 个实体)

商户管理相关实体：

```
✓ MtMerchant      - 商户信息
```

**包路径**: `com.fuint.domain.model.merchant`

---

## 迁移转换规则

### 领域实体 (Domain Entity)

**文件位置**: `fuint-domain/src/main/java/com/fuint/domain/model/{module}/`

**转换规则**:
1. ✅ 移除所有 MyBatis Plus 注解 (`@TableName`, `@TableId`, `@TableField`)
2. ✅ 移除所有 Swagger 注解 (`@ApiModel`, `@ApiModelProperty`)
3. ✅ 保留业务逻辑相关注解 (`@JsonFormat`)
4. ✅ 将 `Date` 类型转换为 `LocalDateTime`
5. ✅ 将 `Integer` 类型统一为 `Long`
6. ✅ 添加 `tenantId` 字段（如果原实体缺失）

**示例**:
```java
package com.fuint.domain.model.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MtUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String userNo;
    private String mobile;
    // ... 其他字段
}
```

---

### 数据对象 (Data Object - DO)

**文件位置**: `fuint-infrastructure/src/main/java/com/fuint/infrastructure/persistence/entity/`

**转换规则**:
1. ✅ 保留 MyBatis Plus 注解 (`@TableName`, `@TableId`, `@TableField`)
2. ✅ 移除 Swagger 注解 (`@ApiModel`, `@ApiModelProperty`)
3. ✅ 类名添加 `DO` 后缀
4. ✅ 将 `Date` 类型转换为 `LocalDateTime`
5. ✅ 将 `Integer` 类型统一为 `Long`
6. ✅ 添加 `tenantId` 字段（如果原实体缺失）

**示例**:
```java
package com.fuint.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("mt_user")
public class MtUserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String userNo;
    private String mobile;
    // ... 其他字段
}
```

---

## 目录结构

```
fuint-domain/src/main/java/com/fuint/domain/model/
├── booking/        # 预约模块 (3 个实体)
├── commission/     # 分佣模块 (5 个实体)
├── common/         # 通用模块 (8 个实体)
├── coupon/         # 卡券模块 (8 个实体)
├── goods/          # 商品模块 (7 个实体)
├── merchant/       # 商户模块 (1 个实体)
├── order/          # 订单模块 (8 个实体)
├── store/          # 店铺模块 (3 个实体)
└── user/           # 会员模块 (11 个实体)

fuint-infrastructure/src/main/java/com/fuint/infrastructure/persistence/entity/
├── MtAddressDO.java
├── MtArticleDO.java
├── ... (共 54 个 DO 文件)
└── MtVerifyCodeDO.java
```

---

## 关键改进点

### 1. 多租户支持
所有实体都添加了 `tenantId` 字段，为多租户 SaaS 架构提供基础支持。

### 2. 类型统一
- 主键和外键统一使用 `Long` 类型（原 `Integer` 类型）
- 时间字段统一使用 `LocalDateTime` 类型（原 `Date` 类型）

### 3. 职责分离
- **领域实体**: 纯 POJO，专注业务逻辑
- **数据对象**: 负责数据库映射，包含持久化注解

### 4. 模块化组织
按业务领域划分模块，清晰的目录结构便于维护和扩展。

---

## 下一步工作

基于本次实体迁移，后续需要完成：

1. ✅ **实体迁移** (已完成)
   - 54 个领域实体
   - 54 个 DO 对象

2. ⏳ **生成 Mapper 和转换器**
   - 为每个实体生成 MyBatis Mapper 接口
   - 创建实体与 DO 之间的转换器

3. ⏳ **迁移仓储实现**
   - 迁移现有 Repository 实现到新架构
   - 使用新的 DO 对象替换旧实体

4. ⏳ **迁移业务 Service**
   - 调整业务层使用新的领域实体
   - 通过仓储访问数据

5. ⏳ **迁移 Controller 到接口层**
   - 重构控制器使用新的应用服务
   - 适配新的数据结构

---

## 总结

本次批量迁移工作成功完成了 **54 个业务实体** 从旧架构到新 DDD 架构的转换，为系统的长期可维护性和扩展性奠定了坚实基础。

**迁移成果**:
- ✅ 54 个领域实体（按模块组织）
- ✅ 54 个数据对象（DO）
- ✅ 统一的类型系统（Long、LocalDateTime）
- ✅ 完整的多租户支持（tenantId）
- ✅ 清晰的分层架构（领域层 vs 基础设施层）

---

**迁移完成时间**: 2026-02-06
**脚本位置**: `/tmp/migrate_entities_final.py`
