package com.fuint.framework.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * MyBatis Plus 租户处理器
 * 自动在 SQL 中注入 tenant_id 条件
 *
 * @author fuint
 * @since 2.0.0
 */
@Slf4j
@Component
public class TenantHandler implements TenantLineHandler {

    /**
     * 不需要租户过滤的表
     */
    private static final List<String> IGNORE_TABLES = Arrays.asList(
            "sys_config",           // 系统配置表
            "sys_dict",             // 数据字典表
            "sys_region",           // 区域表
            "sys_setting",          // 系统设置表
            "t_account",            // 账号表(包含租户管理员)
            "t_duty",               // 职务表
            "t_action_log"          // 操作日志表
    );

    /**
     * 获取租户 ID 值
     *
     * @return 租户 ID 表达式
     */
    @Override
    public Expression getTenantId() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            log.warn("Tenant ID is null in TenantContext, this may cause data leak!");
            // 返回一个不存在的租户 ID,防止数据泄露
            return new LongValue(-1);
        }
        return new LongValue(tenantId);
    }

    /**
     * 获取租户字段名
     *
     * @return 租户字段名
     */
    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    /**
     * 是否忽略该表的租户过滤
     *
     * @param tableName 表名
     * @return true=忽略,false=不忽略
     */
    @Override
    public boolean ignoreTable(String tableName) {
        // 如果设置了忽略租户标识,则忽略所有表
        if (TenantContext.isIgnoreTenant()) {
            return true;
        }

        // 系统表不拦截
        return IGNORE_TABLES.contains(tableName.toLowerCase());
    }
}
