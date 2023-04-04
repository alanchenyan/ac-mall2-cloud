package com.ac.core.config.swagger;

import com.ac.core.enums.LanguageEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Chen
 * @description Swagger文档配置
 * @date 2019/5/15
 */
@EnableWebMvc
@Configuration
@EnableSwagger2
@ConfigurationProperties("swagger")
@Data
public class SwaggerConfig implements WebMvcConfigurer {

    //取yml配置文件参数
    private boolean enabled;
    private String title;
    private String description;
    private String version;
    private String basePackage;

    /**
     * @return
     */
    @Bean
    public Docket api() {
        List<Parameter> pars = new ArrayList<>();
        pars.add(buildToken().build());
        pars.add(buildLang().build());
        pars.add(buildDemos().build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(Byte.class, Integer.class)
                .select()
                //.apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectors.basePackage(basePackage))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    /**
     * 一些接口文档信息的简介
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl("")
                .version(version)
                .build();
    }

    private ParameterBuilder buildToken() {
        ParameterBuilder pb = new ParameterBuilder();
        pb.name("Authorization")
                .description("授权的token, 格式: Bearer xxx(需通过网关)")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build(); //header中的ticket参数非必填，传空也可以
        return pb;
    }

    private ParameterBuilder buildLang() {
        List<String> languages = new ArrayList<>();
        languages.add(LanguageEnum.zh_CN.getCode());
        languages.add(LanguageEnum.zh_HK.getCode());
        languages.add(LanguageEnum.en_US.getCode());
        ParameterBuilder pb = new ParameterBuilder();
        pb.name("lang")
                .description("当前国际化语言环境")
                .modelRef(new ModelRef("string"))
                .allowableValues(new AllowableListValues(languages, "string"))
                .parameterType("header")
                .required(false)
                .build(); //header中的ticket参数非必填，传空也可以
        return pb;
    }

    private ParameterBuilder buildDemos() {
        List<String> demouis = new ArrayList<>();
        demouis.add("test01");
        demouis.add("test02");
        ParameterBuilder pb = new ParameterBuilder();
        pb.name("Swagger测试用户")
                .description("Swagger模拟用户, 选中时Authorization字段将失效(无需通过网关)")
                .modelRef(new ModelRef("string"))
                .allowableValues(new AllowableListValues(demouis, "string"))
                .parameterType("header")
                .required(false)
                .order(0)
                .build(); //header中的ticket参数非必填，传空也可以
        return pb;
    }

    /**
     * swagger ui资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * swagger-ui.html路径映射，浏览器中使用/api-docs访问
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs", "/swagger-ui.html");
    }
}
