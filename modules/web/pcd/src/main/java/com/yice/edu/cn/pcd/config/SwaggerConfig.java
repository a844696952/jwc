package com.yice.edu.cn.pcd.config;

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

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@ConditionalOnExpression("'${spring.profiles.active}'!='prod'")
@ComponentScan("com.yice.edu.cn.pcd.controller")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket loginApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("登录模块")
                .apiInfo(apiInfo())
                .select()
                .paths(loginPaths())
                .build();
    }
    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")
        );
    }


    @Bean
    public Docket eehApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电教馆模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(eehPaths())
                .build();
    }
    private Predicate<String> eehPaths() {
        return or(
                regex("/eehTree/.*"),
                regex("/eehAccount/.*"),
                regex("/repairNew/.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电教馆平台")
                .description("<h4>接口里pager对象只在查询列表时用到</h4>")
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
        tokenPar.name(Constant.TOKEN).description("登录后获取到的token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }



}
