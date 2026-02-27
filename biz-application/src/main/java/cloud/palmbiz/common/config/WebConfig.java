package cloud.palmbiz.common.config;

import cloud.palmbiz.common.web.AdminUserInterceptor;
import cloud.palmbiz.common.web.CommandInterceptor;
import cloud.palmbiz.common.web.ClientUserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import java.util.concurrent.TimeUnit;

/**
 * web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "classpath:/other-resources/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public CommandInterceptor commandInterceptor() {
        return new CommandInterceptor();
    }

    @Bean
    public AdminUserInterceptor adminUserInterceptor() {
        return new AdminUserInterceptor();
    }

    @Bean
    public ClientUserInterceptor portalUserInterceptor() {
        return new ClientUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Command
        registry.addInterceptor(commandInterceptor())
                .addPathPatterns("/cmd/**");

        // 后台拦截
        registry.addInterceptor(adminUserInterceptor())
                .addPathPatterns("/backendApi/**")
                .excludePathPatterns("/client/captcha/**")
                .excludePathPatterns("/backendApi/captcha/**")
                .excludePathPatterns("/backendApi/userCoupon/exportList")
                .excludePathPatterns("/backendApi/order/export")
                .excludePathPatterns("/backendApi/goods/goods/downloadTemplate")
                .excludePathPatterns("/backendApi/member/downloadTemplate")
                .excludePathPatterns("/backendApi/login/**");

        // 客户端拦截
        registry.addInterceptor(portalUserInterceptor())
                .addPathPatterns("/client/**")
                .excludePathPatterns("/client/sign/**")
                .excludePathPatterns("/client/page/home")
                .excludePathPatterns("/client/captcha/**")
                .excludePathPatterns("/client/goodsApi/**")
                .excludePathPatterns("/client/coupon/list")
                .excludePathPatterns("/client/coupon/detail")
                .excludePathPatterns("/client/cart/**")
                .excludePathPatterns("/client/user/**")
                .excludePathPatterns("/client/settlement/submit")
                .excludePathPatterns("/client/pay/doPay")
                .excludePathPatterns("/client/pay/weixinCallback")
                .excludePathPatterns("/client/pay/weixinRefundNotify")
                .excludePathPatterns("/client/pay/aliPayCallback")
                .excludePathPatterns("/client/order/todoCounts")
                .excludePathPatterns("/client/order/detail")
                .excludePathPatterns("/client/store/**")
                .excludePathPatterns("/client/article/**")
                .excludePathPatterns("/client/message/getOne")
                .excludePathPatterns("/client/message/wxPush")
                .excludePathPatterns("/client/sms/sendVerifyCode")
                .excludePathPatterns("/client/book/list")
                .excludePathPatterns("/client/book/detail")
                .excludePathPatterns("/client/book/cateList");
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
