package com.fuint.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flyway 数据库迁移配置
 *
 * @author fuint
 * @since 2.0.0
 */
@Slf4j
@Configuration
public class FlywayConfig {

    /**
     * Flyway 迁移策略
     * 在启动时自动执行数据库迁移
     */
    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // 修复 Flyway 版本冲突(可选)
            // flyway.repair();

            // 执行迁移
            flyway.migrate();

            log.info("Flyway migration completed successfully");
        };
    }
}
