package com.fuint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * Fuint 会员营销系统 - 启动类
 * DDD 架构 + 多租户 SaaS 系统
 *
 * @author fuint
 * @version 2.0.0
 * @since 2026-02-06
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.fuint")
public class FuintApplication {

    public static final String REWRITE_FILTER_NAME = "rewriteFilter";
    public static final String REWRITE_FILTER_CONF_PATH = "urlRewrite.xml";

    public static void main(String[] args) {
        SpringApplication.run(FuintApplication.class, args);
        printStartupBanner();
    }

    /**
     * URL 重写过滤器
     */
    @Bean
    public FilterRegistrationBean<UrlRewriteFilter> rewriteFilterConfig() {
        FilterRegistrationBean<UrlRewriteFilter> reg = new FilterRegistrationBean<>();
        reg.setName(REWRITE_FILTER_NAME);
        reg.setFilter(new UrlRewriteFilter());
        reg.addInitParameter("confPath", REWRITE_FILTER_CONF_PATH);
        reg.addInitParameter("confReloadCheckInterval", "-1");
        reg.addInitParameter("statusPath", "/redirect");
        reg.addInitParameter("statusEnabledOnHosts", "*");
        reg.addInitParameter("logLevel", "WARN");
        return reg;
    }

    /**
     * 打印启动横幅
     */
    private static void printStartupBanner() {
        System.out.println("\n" +
                "==============================================================\n" +
                "   _____ _   _ _____ _   _ _____   ____   ___  \n" +
                "  |  ___| | | |_   _| \\ | |_   _| |___ \\ / _ \\ \n" +
                "  | |_  | | | | | | |  \\| | | |     __) | | | |\n" +
                "  |  _| | |_| | | | | |\\  | | |    / __/| |_| |\n" +
                "  |_|    \\___/  |_| |_| \\_| |_|   |_____|\\___/ \n" +
                "\n" +
                "  🚀 Fuint 会员营销系统启动成功！\n" +
                "  📚 架构: DDD 领域驱动设计\n" +
                "  🏢 模式: 多租户 SaaS\n" +
                "  🗄️ 数据库: PostgreSQL / MySQL\n" +
                "\n" +
                "  官网: https://www.fuint.cn\n" +
                "  文档: http://localhost:8080/swagger-ui.html\n" +
                "  作者: FSQ\n" +
                "==============================================================\n");
    }
}
