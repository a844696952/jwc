package com.yice.edu.cn.dm.config;

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
@ComponentScan("com.yice.edu.cn.dm.controller")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket dmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("云班牌模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dmPaths())
                .build();
    }
    @Bean
    public Docket wbApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子白板模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(wbPaths())
                .build();
    }

    @Bean
    public Docket spApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智能笔模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(spPaths())
                .build();
    }

    @Bean
    public Docket modeTask() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通知任务")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(modeTaskInfo())
                .build();
    }

    @Bean
    public Docket dmStudentAttendantApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌值日生模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dmStudentAttendantPaths())
                .build();
    }

    @Bean
    public Docket dmMobileRedBannerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌流动红旗模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dmMobileRedBannerPaths())
                .build();
    }

    private Predicate<String> modeTaskInfo() {
        return or(
                regex("/modeTask/.*")

        );
    }


    private Predicate<String> spPaths() {
        return or(
                regex("/dmCodeResource/.*")

        );
    }

    private Predicate<String> dmPaths() {
        return or(
                regex("/dmActive/.*")

        );
    }

    private Predicate<String> wbPaths() {
        return or(
                regex("/classRegister/.*"),
                regex("/helpCenter/.*"),
                regex("/studentAccount/.*")
        );
    }
    private Predicate<String> dmStudentAttendantPaths() {
        return or(
                regex("/dmStudentAttendant/.*")

        );
    }

    private Predicate<String> dmMobileRedBannerPaths() {
        return or(
                regex("/dmMobileRedBanner/.*")

        );
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电子版牌模块")
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
        tokenPar.name(Constant.Redis.OSP_TEACHER_ID_HEADER).description("教师id").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }

}
