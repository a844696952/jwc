package com.yice.edu.cn.jw.config;

import com.google.common.base.Predicate;
import com.yice.edu.cn.common.pojo.Constant;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnExpression("'${spring.profiles.active}'!='prod'")
@ComponentScan("com.yice.edu.cn.jw.controller")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket bigScreenApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("大屏展示")
                .apiInfo(apiInfo())
                .select()
                .paths(bigScreenPaths())
                .build();
    }

    private Predicate<String> bigScreenPaths() {
        return or(
                regex("/yedHomePage/.*")
        );
    }

    private Predicate<String> standardPaths() {
        return or(
                regex("/standard/.*")
        );
    }
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户管理")
                .apiInfo(apiInfo())
                .select()
                .paths(userPaths())
                .build();
    }
    private Predicate<String> userPaths() {
        return or(
                regex("/admin/.*"),
                regex("/adminSysRole/.*")
        );
    }
    @Bean
    public Docket classesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级管理")
                .apiInfo(apiInfo())
                .globalOperationParameters(setHeaderToken())
                .select()
                .paths(classesPaths())
                .build();
    }
    private Predicate<String> classesPaths() {
        return or(
                regex("/jwClasses/.*")
        );
    }
    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("角色")
                .apiInfo(apiInfo())
                .select()
                .paths(rolePaths())
                .build();
    }
    private Predicate<String> rolePaths() {
        return or(
                regex("/sysRole/.*"),
                regex("/adminSysRole/.*")
        );
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台用户api")
                .description("接口里page,pageSize,sortField,sortOrder四个字段除了查询列表接口外,请忽略")
                .termsOfServiceUrl("http://springfox.io")
                .contact(new Contact("名字","www.baidu.com","test@163.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0")
                .build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(Constant.Redis.OSP_TEACHER_ID_HEADER).description("登录用户id").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }


}
