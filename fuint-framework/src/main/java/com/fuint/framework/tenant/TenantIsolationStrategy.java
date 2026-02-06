package com.fuint.framework.tenant;

/**
 * 租户隔离策略
 *
 * @author fuint
 * @since 2.0.0
 */
public enum TenantIsolationStrategy {

    /**
     * 共享数据库 - 通过 tenant_id 字段隔离
     * 适用场景: 小型 SaaS (< 1000 租户)
     */
    SHARED_DATABASE,

    /**
     * 独立 Schema - PostgreSQL Schema 隔离
     * 适用场景: 中型 SaaS,需要一定隔离度
     */
    SEPARATE_SCHEMA,

    /**
     * 独立数据库 - 完全物理隔离
     * 适用场景: 大客户/企业级租户
     */
    SEPARATE_DATABASE
}
