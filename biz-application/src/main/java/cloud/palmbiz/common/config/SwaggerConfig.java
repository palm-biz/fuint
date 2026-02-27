package cloud.palmbiz.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger接口文档
 */
@Configuration
public class SwaggerConfig {

     @Bean
     public Docket createRestApi() {
         return new Docket(DocumentationType.OAS_30)
         .apiInfo(apiInfo())
         .enable(true)
         .select()
         .apis(RequestHandlerSelectors.basePackage("cloud.palmbiz.module"))
         .paths(PathSelectors.any())
         .build();
     }

     @Bean
     public ApiInfo apiInfo() {
         return new ApiInfoBuilder()
         .title("会员营销系统接口文档")
         .description("会员营销系统接口文档，“/client”目录接口为会员端相关接口，“/backendApi”目录接口为后台管理端相关接口。")
         .version("1.0")
         .build();
     }
}