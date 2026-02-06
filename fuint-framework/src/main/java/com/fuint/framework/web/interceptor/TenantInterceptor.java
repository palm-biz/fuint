package com.fuint.framework.web.interceptor;

import com.fuint.framework.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户拦截器
 * 从请求中提取租户信息并设置到上下文
 *
 * @author fuint
 * @since 2.0.0
 */
@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    /**
     * 租户 ID 请求头
     */
    private static final String TENANT_ID_HEADER = "X-Tenant-Id";

    /**
     * 租户 ID 请求参数
     */
    private static final String TENANT_ID_PARAM = "tenantId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从请求头获取租户 ID
        String tenantIdStr = request.getHeader(TENANT_ID_HEADER);

        // 2. 如果请求头没有,从请求参数获取
        if (tenantIdStr == null || tenantIdStr.trim().isEmpty()) {
            tenantIdStr = request.getParameter(TENANT_ID_PARAM);
        }

        // 3. 如果有租户 ID,设置到上下文
        if (tenantIdStr != null && !tenantIdStr.trim().isEmpty()) {
            try {
                Long tenantId = Long.parseLong(tenantIdStr);
                TenantContext.setTenantId(tenantId);
                log.debug("Tenant interceptor set tenant: {}, uri: {}", tenantId, request.getRequestURI());
            } catch (NumberFormatException e) {
                log.warn("Invalid tenant id: {}", tenantIdStr);
            }
        } else {
            // TODO: 在实际项目中,应该从 JWT Token 或 Session 中获取租户 ID
            // 这里暂时使用默认租户 ID
            log.debug("No tenant id in request, uri: {}", request.getRequestURI());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清除租户上下文,防止内存泄漏
        TenantContext.clear();
    }
}
