package com.fuint.framework.tenant.annotation;

import java.lang.annotation.*;

/**
 * 忽略租户过滤注解
 * 用于标记不需要租户过滤的方法
 *
 * @author fuint
 * @since 2.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreTenant {

    /**
     * 说明原因
     */
    String value() default "";
}
