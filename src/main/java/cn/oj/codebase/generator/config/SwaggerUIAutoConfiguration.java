package cn.oj.codebase.generator.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerUIAutoConfiguration {


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("代码快速开发库")
                .title("代码快速开发库")
                .version("0.0.1-SNAPSHOT")
                .build();
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.any())
                .apis(selectorApis())
                .build()
                .securitySchemes(securitySchemes())
                .apiInfo(apiInfo());
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }


    //扫描包含swagger注解的实例
    private Predicate<RequestHandler> selectorApis() {
        return Predicates.or(Arrays.asList(RequestHandlerSelectors.basePackage("cn.cary.codebase")
                , RequestHandlerSelectors.basePackage("cn.cary.codebase.generator.sys.entity")));
    }

}
