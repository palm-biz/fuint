# fuint会员营销系统介绍

#### 介绍
fuint会员营销系统是一套基于**领域驱动设计(DDD)**的开源实体店铺会员管理和营销SaaS系统。系统基于前后端分离的架构，后端采用<b>Java SpringBoot</b> + <b>PostgreSQL/MySQL</b> + <b>MyBatis Plus</b>，前端基于当前流行的<b>Uniapp</b>，<b>Element UI</b>，支持小程序、h5。

**核心特性:**
- 🏗️ **DDD 分层架构**: 采用领域驱动设计,清晰的分层架构(接口层、应用层、领域层、基础设施层)
- 🏢 **多租户支持**: 基于 tenant_id 的数据隔离,支持 SaaS 多租户模式
- 🗄️ **数据库迁移**: 集成 Flyway,支持版本化的数据库变更管理
- 🔄 **PostgreSQL 优先**: 原生支持 PostgreSQL,兼容 MySQL
- 🔒 **严格分层**: DO/Entity 分离,领域模型与数据模型解耦

主要功能包含电子优惠券、储值卡、实体卡、集次卡（计次卡）、短信发送、储值卡、会员积分、会员等级权益体系，支付收款等会员日常营销工具。本系统适用于各类实体店铺，如生鲜、零售超市、酒吧、酒店、汽车4S店、鲜花店、奶茶店、甜品店、餐饮店、农家乐等，是实体店铺会员营销必备的一款利器。
以下是前台的页面展示：
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/g1.png?v=1" alt="前台页面1"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/g2.png?v=2" alt="前台页面2"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/g3.png?v=2" alt="前台页面3"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/g4.png?v=1" alt="前台页面4"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/g5.png?v=1" alt="前台页面4"></p>

fuint侧重于线下实体店的私域流量的运营，同时提供会员端小程序和收银系统的线上线下统一渠道，帮助商户降低获客成本。顾客通过扫码支付成为私域流量，支付即可成为会员。积分和卡券功能建立起会员等级体系，通过消息推送和短信营销方便触达用户。
<p>1、会员运营自动化：商家通过日常活动设置，如开卡礼设置，沉睡唤醒等，成为会员后自动给顾客送优惠券，让顾客更有黏性，提升会员运营效率。</p>
<p>2、打通收银系统和会员营销的壁垒，代客下单收银，支付即成为会员。</p>
<p>3、会员体系完整化：积分兑换、积分转赠、会员等级权益、积分加速、买单折扣。</p>
<p>4、会员卡券齐全：储值卡、电子券、优惠券、集次卡、计次卡、实体卡购买并兑换、会员充值、余额支付。</p>
<p>5、线上代客下单收银系统，后台管理员可帮助临柜的会员下单、扫码支付。</p>
<p>6、支持手机短信、站内弹框消息、微信订阅消息：支持包括发货消息、卡券到期提醒、活动提醒、会员到期提醒、积分余额变动提醒等消息。</p>
<p>7、具有门店预约、到店支付等线上线下结合的o2o能力。</p>
<p>小程序前端仓库：https://gitee.com/fuint/fuint-uniapp</p>
<b>扫码小程序演示：</b><br>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/miniapp.png" alt="小程序演示"></p>
<br>
<b>官网演示地址：</b><br>
<p>
   1、官网：<a target="_blank" href="https://www.fuint.cn">https://www.fuint.cn</a> 点击 -> 系统演示，演示账号：fuint / 123456<br>
   2、swagger接口文档：<a target="_blank" href="https://www.fuint.cn/fuint-application/swagger-ui.html">https://www.fuint.cn/fuint-application/swagger-ui.html</a>
</p>

#### 软件架构

**DDD 分层架构:**
```
fuint/
├── fuint-boot                  # 🚀 启动模块 (唯一 SpringBootApplication)
├── fuint-common                # 🧰 纯工具 & 常量 (无业务依赖)
├── fuint-framework             # 🧱 技术基础设施 (多租户、安全、缓存)
├── fuint-domain                # 🧠 领域层 (业务核心逻辑)
├── fuint-infrastructure        # 🗄️ 基础设施实现 (DB/Redis/MQ/OSS)
├── fuint-application           # 🎯 应用层 (用例编排)
└── fuint-interface             # 🌐 接口层 (REST API)
```

**技术栈:**

后端技术<br>
1.1 SpringBoot 2.5.12<br>
1.2 MyBatis Plus 3.5+ (多租户插件)<br>
1.3 PostgreSQL 14+ / MySQL 8.0+<br>
1.4 Flyway (数据库版本管理)<br>
1.5 SpringSecurity + JWT<br>
1.6 Redis<br>
1.7 HikariCP (连接池)<br>
1.8 Swagger UI<br>
1.9 Maven<br>

前端技术<br>
2.1 Vue 2.x<br>
2.2 Uniapp (跨平台)<br>
2.3 Element UI<br>
2.4 Node.js<br>

<p>后台截图：</p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/login.png?v=fuint" alt="登录界面"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/homeV2.png?v=fuint" alt="首页"></p>

**架构特点:**

1. **领域驱动设计(DDD)**
   - 清晰的分层架构,职责明确
   - DO(数据对象) 与 Entity(领域实体) 分离
   - 仓储模式,领域层定义接口,基础设施层实现

2. **多租户支持**
   - 基于 `tenant_id` 的共享数据库隔离
   - MyBatis Plus 自动拦截 SQL,注入租户条件
   - 支持混合隔离策略 (共享DB/独立Schema/独立DB)

3. **数据库迁移**
   - Flyway 版本化管理数据库变更
   - 支持多环境 (开发/测试/生产)
   - 可追溯的变更历史

4. **依赖关系**
   ```
   fuint-boot
       ↓
   fuint-interface
       ↓
   fuint-application
       ↓
   fuint-domain  ←←←  fuint-infrastructure
       ↓                      ↓
   fuint-common  ←←←  fuint-framework
   ```


#### 安装步骤

**环境要求:**
- JDK 1.8+
- PostgreSQL 14+ 或 MySQL 8.0+
- Redis 5.0+
- Maven 3.6+

**数据库初始化 (推荐 PostgreSQL):**
```bash
# PostgreSQL 安装后创建数据库
createdb fuint_db

# 使用 Flyway 自动迁移
# 系统启动时会自动执行 db/migration 下的 SQL 脚本
```

**配置文件:**
```bash
# 修改 config/dev/application-dev.yml 或 config/prod/application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fuint_db
    username: postgres
    password: your_password

  # 多租户配置
  fuint:
    tenant:
      enabled: true
      default-strategy: SHARED_DATABASE  # 共享数据库模式
```

**编译运行:**
```bash
# 1. 克隆项目
git clone https://github.com/your-repo/fuint.git

# 2. 安装依赖
mvn clean install -DskipTests

# 3. 启动项目 (开发环境)
cd fuint-boot
mvn spring-boot:run -Dspring.profiles.active=dev

# 4. 生产部署 (打包)
mvn clean package -DskipTests
nohup java -Dfile.encoding=UTF-8 \
  -Xmx2048m -Xms2048m -Xss256k -Xmn1024m \
  -Dspring.profiles.active=prod \
  -jar fuint-boot/target/fuint-boot-1.0.0.jar &
```

**使用宝塔部署:**
<p>无后端和linux基础的朋友，推荐使用<b>宝塔面板</b>部署，非常方便简单。</p>

**访问系统:**
- 后台管理: http://localhost:8080
- 接口文档: http://localhost:8080/swagger-ui.html
- 默认账号: admin / 123456


#### 前台使用说明

1.  会员登录，登录成功后可看到会员的卡券列表。
2.  卡券领取和购买，预存券的充值等。
3.  核销卡券，会员在前台出示二维码，管理员用微信扫一扫即可核销。
4.  卡券转赠，会员可将自己的卡券转赠给其他用户，输入对方的手机号即可完成转赠，获赠的好友会收到卡券赠送的短信。

<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/create.png?v=fuint" alt="卡券创建界面"></p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/member.png?v=fuint" alt="卡券创建界面"></p>

#### 后台使用
1. 会员管理：会员新增、导入、禁用等。
2. 内容管理：焦点图管理、文章管理等。
3. 卡券管理：电子券管理为2层结构，即电子券组和电子券。
4. 会员积分：会员积分管理，会员积分的操作，会员积分明细查看。
5. 转赠管理：卡券转赠记录。
6. 短信管理：短信营销功能，已发送的短信列表。
7. 系统配置：配置系统管理员权限等。
8. 店铺管理：支持多店铺模式。
9. 核销管理员:核销人员管理主要包含3个功能：核销人员列表、核销人员审核、核销人员信息编辑。
10. 短信模板管理：可配置不同场景和业务的短信内容。
11. 卡券发放：单独发放、批量发放，发放成功后给会员发送短信通知
12. 操作日志主要针对电子券系统后台的一些关键操作进行日志记录，方便排查相关操作人的行为等问题。
13. 发券记录主要根据发券的实际操作情况来记录，分为单用户发券和批量发券，同时可针对该次发券记录进行作废操作。
14. 代客下单、收银功能，支持小票打印，包括本地打印和云打印（对接芯烨云打印服务）。
<p>卡券营销：</p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/coupon-list.png?v=fuint" alt="卡券列表"></p>

<p>收银代客下单功能：店员角色登录后台，从首页的“下单首页”菜单可进入代客收银下单界面，完成代客下单收银的流程。</p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/cashier.png?v=fuint3.0.8" alt="收银界面"></p>
<p>发起结算：</p>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/cashier-1.png?v=fuint3.0.8" alt="收银结算"></p>

#### 开发计划
1. ✅ DDD 架构重构,清晰的分层设计；
2. ✅ 多租户支持,SaaS 模式；
3. ✅ PostgreSQL 原生支持；
4. ✅ Flyway 数据库版本管理；
5. 🔄 完善的报表统计；
6. 🔄 分享助力、分享领券、分享获得积分；
7. 🔄 员工提成、分销功能；
8. 🔄 更多营销工具，比如签到等；
9. 🔄 GraphQL API 支持；
10. 🔄 微服务架构演进 (Spring Cloud)。


#### 允许使用范围：
1.  允许个人学习使用
2.  允许用于毕业设计、论文参考代码
3.  推荐Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持
4.  希望大家多多支持原创软件
5.  请勿去除版权标签，要商用请购买源码授权（非常便宜），感谢理解！

不足和待完善之处请谅解！源码仅供学习交流，更多功能欢迎进群咨询讨论，或需安装帮助请联系我们（<b>麻烦先点star！！！！！！</b>）。<br>
官方网站：https://www.fuint.cn <br>
开源不易，感谢支持！<br>
<b>作者wx：fsq_better：</b><br>
<p><img src="https://fuint-cn.oss-cn-shenzhen.aliyuncs.com/screenshots/qr.png" alt="公众号二维码"></p>


特别鸣谢：<br>
Mybaits Plus: https://github.com/baomidou/mybatis-plus<br>
Vue: https://github.com/vuejs/vue<br>
Element UI: https://element.eleme.cn
