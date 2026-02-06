package com.fuint.framework.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.fuint.framework.tenant.TenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 * 包含多租户插件、分页插件等
 *
 * @author fuint
 * @since 2.0.0
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    private final TenantHandler tenantHandler;

    public MybatisPlusConfig(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
    }

    /**
     * MyBatis Plus 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 多租户插件
        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
        tenantInterceptor.setTenantLineHandler(tenantHandler);
        interceptor.addInnerInterceptor(tenantInterceptor);
        log.info("MyBatis Plus tenant interceptor enabled");

        // 分页插件 (支持 PostgreSQL 和 MySQL)
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 自动识别数据库类型
        paginationInterceptor.setDbType(DbType.POSTGRE_SQL);  // 默认 PostgreSQL
        paginationInterceptor.setOverflow(true);  // 溢出总页数后是否进行处理
        paginationInterceptor.setMaxLimit(1000L);  // 单页最大条数限制
        interceptor.addInnerInterceptor(paginationInterceptor);
        log.info("MyBatis Plus pagination interceptor enabled");

        return interceptor;
    }

    /**
     * 自定义 SQL 注入器(可选)
     */
    // @Bean
    // public ISqlInjector sqlInjector() {
    //     return new LogicSqlInjector();
    // }
}
