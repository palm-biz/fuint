package com.fuint.framework.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户上下文 - 使用 ThreadLocal 存储当前租户信息
 * 使用阿里的 TransmittableThreadLocal 支持线程池场景
 *
 * @author fuint
 * @since 2.0.0
 */
@Slf4j
public class TenantContext {

    /**
     * 租户 ID ThreadLocal
     */
    private static final TransmittableThreadLocal<Long> TENANT_ID_HOLDER = new TransmittableThreadLocal<>();

    /**
     * 忽略租户过滤标识
     */
    private static final TransmittableThreadLocal<Boolean> IGNORE_TENANT_HOLDER = new TransmittableThreadLocal<>();

    /**
     * 设置租户 ID
     *
     * @param tenantId 租户 ID
     */
    public static void setTenantId(Long tenantId) {
        TENANT_ID_HOLDER.set(tenantId);
        log.debug("Set tenant context: {}", tenantId);
    }

    /**
     * 获取租户 ID
     *
     * @return 租户 ID
     */
    public static Long getTenantId() {
        return TENANT_ID_HOLDER.get();
    }

    /**
     * 清除租户上下文
     */
    public static void clear() {
        TENANT_ID_HOLDER.remove();
        IGNORE_TENANT_HOLDER.remove();
        log.debug("Clear tenant context");
    }

    /**
     * 设置忽略租户过滤
     */
    public static void setIgnoreTenant(boolean ignore) {
        IGNORE_TENANT_HOLDER.set(ignore);
    }

    /**
     * 是否忽略租户过滤
     *
     * @return true=忽略租户过滤
     */
    public static boolean isIgnoreTenant() {
        Boolean ignore = IGNORE_TENANT_HOLDER.get();
        return ignore != null && ignore;
    }

    /**
     * 执行无租户上下文的操作
     *
     * @param runnable 操作
     */
    public static void executeWithoutTenant(Runnable runnable) {
        setIgnoreTenant(true);
        try {
            runnable.run();
        } finally {
            setIgnoreTenant(false);
        }
    }
}
