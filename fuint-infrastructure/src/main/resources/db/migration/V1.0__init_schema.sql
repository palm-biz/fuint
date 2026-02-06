-- ============================================
-- Fuint 2.0 - 初始化数据库脚本 (PostgreSQL)
-- DDD 架构 + 多租户支持
-- Version: V1.0__init_schema.sql
-- Date: 2026-02-06
-- ============================================

-- 1. 会员地址表
CREATE TABLE IF NOT EXISTS mt_address (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(30) NOT NULL DEFAULT '',
    mobile VARCHAR(20) DEFAULT '',
    province_id INT DEFAULT 0,
    city_id INT DEFAULT 0,
    region_id INT DEFAULT 0,
    detail VARCHAR(255) DEFAULT '',
    is_default CHAR(1) DEFAULT 'N',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status CHAR(1) DEFAULT 'A',
    CONSTRAINT idx_address_tenant_user UNIQUE(tenant_id, id)
);

CREATE INDEX idx_address_tenant ON mt_address(tenant_id);
CREATE INDEX idx_address_user ON mt_address(user_id);
COMMENT ON TABLE mt_address IS '会员地址表';

-- 2. 商户表 (核心租户表)
CREATE TABLE IF NOT EXISTS mt_merchant (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(50),
    phone VARCHAR(20),
    logo VARCHAR(200),
    description TEXT,
    status CHAR(1) DEFAULT 'A',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_merchant_tenant ON mt_merchant(tenant_id);
COMMENT ON TABLE mt_merchant IS '商户表(租户)';

-- 3. 店铺表
CREATE TABLE IF NOT EXISTS mt_store (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    merchant_id BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(50) NOT NULL DEFAULT '',
    contact VARCHAR(30) DEFAULT '',
    phone VARCHAR(20) DEFAULT '',
    license VARCHAR(50) DEFAULT '',
    credit_code VARCHAR(50) DEFAULT '',
    address VARCHAR(255) DEFAULT '',
    hours VARCHAR(100) DEFAULT '',
    latitude VARCHAR(20) DEFAULT '',
    longitude VARCHAR(20) DEFAULT '',
    is_default CHAR(1) DEFAULT 'N',
    description TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operator VARCHAR(30) DEFAULT '',
    status CHAR(1) DEFAULT 'A'
);

CREATE INDEX idx_store_tenant ON mt_store(tenant_id);
CREATE INDEX idx_store_merchant ON mt_store(merchant_id);
COMMENT ON TABLE mt_store IS '店铺表';

-- 4. 会员表
CREATE TABLE IF NOT EXISTS mt_user (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    merchant_id BIGINT DEFAULT 0,
    store_id BIGINT DEFAULT 0,
    mobile VARCHAR(16) DEFAULT '',
    name VARCHAR(30) DEFAULT '',
    avatar VARCHAR(200) DEFAULT '',
    idcard VARCHAR(20) DEFAULT '',
    grade_id INT DEFAULT 0,
    balance DECIMAL(10,2) DEFAULT 0.00,
    point INT DEFAULT 0,
    open_id VARCHAR(100) DEFAULT '',
    union_id VARCHAR(100) DEFAULT '',
    sex INT DEFAULT 0,
    birthday VARCHAR(20) DEFAULT '',
    car_no VARCHAR(10) DEFAULT '',
    address VARCHAR(100) DEFAULT '',
    description TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status CHAR(1) DEFAULT 'A'
);

CREATE INDEX idx_user_tenant ON mt_user(tenant_id);
CREATE INDEX idx_user_mobile ON mt_user(mobile);
CREATE INDEX idx_user_merchant ON mt_user(merchant_id);
COMMENT ON TABLE mt_user IS '会员表';

-- 5. 系统配置表(全局,无租户)
CREATE TABLE IF NOT EXISTS sys_config (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(30) NOT NULL DEFAULT '',
    name VARCHAR(50) NOT NULL DEFAULT '',
    key VARCHAR(50) NOT NULL DEFAULT '',
    value TEXT,
    description VARCHAR(200) DEFAULT '',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status CHAR(1) DEFAULT 'A'
);

COMMENT ON TABLE sys_config IS '系统配置表(全局)';

-- 6. 账号表(包含租户管理员)
CREATE TABLE IF NOT EXISTS t_account (
    acct_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT DEFAULT 0,
    account_key VARCHAR(32) NOT NULL,
    account_name VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    salt VARCHAR(32),
    account_status INT DEFAULT 1,
    is_active INT DEFAULT 1,
    locked_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_account_key UNIQUE(account_key)
);

CREATE INDEX idx_account_tenant ON t_account(tenant_id);
COMMENT ON TABLE t_account IS '账号表';

-- 插入默认管理员账号
INSERT INTO t_account (tenant_id, account_key, account_name, password, salt, account_status, is_active)
VALUES (0, 'admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '', 1, 1)
ON CONFLICT (account_key) DO NOTHING;

-- 插入默认商户(租户1)
INSERT INTO mt_merchant (tenant_id, name, contact, phone, status)
VALUES (1, '演示商户', 'FSQ', '13800138000', 'A')
ON CONFLICT (tenant_id) DO NOTHING;
