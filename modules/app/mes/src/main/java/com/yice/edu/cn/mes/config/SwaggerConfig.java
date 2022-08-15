package com.yice.edu.cn.mes.config;

import com.google.common.base.Predicate;
import com.yice.edu.cn.common.config.swagger.SwaggerPathProvider;
import com.yice.edu.cn.common.pojo.Constant;
import org.springframework.beans.factory.annotation.Autowired;
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
@ComponentScan("com.yice.edu.cn.mes.controller")
@ConditionalOnExpression("'${spring.profiles.active}'!='prod'")
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private SwaggerPathProvider swaggerPathProvider;
    @Bean
    public Docket loginApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("登录模块")
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
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
    public Docket schoolApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolPaths())
                .build();
    }

    private Predicate<String> schoolPaths() {
        return or(
                regex("/school/.*"),
                regex("/appVersionControl/.*")
        );
    }

    @Bean
    public Docket inspectApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("检查模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(inspectPaths())
                .build();
    }

    @Bean
    public Docket ycVerifaceOtherCheckApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("人脸识别")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(ycVerifaceOtherCheckPaths())
                .build();
    }

    private Predicate<String> inspectPaths() {
        return or(
                regex("/mesInspectRecord/.*")
        );
    }

    @Bean
    public Docket appletsRuleRecordApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班管学生检查模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(appletsRuleRecordPaths())
                .build();
    }

    private Predicate<String> appletsRuleRecordPaths() {
        return or(
                regex("/mesAppletsRuleRecord/.*")
        );
    }

    private Predicate<String> ycVerifaceOtherCheckPaths() {
        return or(
                regex("/YcVerifaceOtherCheckController/.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("德育 pad端")
                .description("")
                .termsOfServiceUrl("http://springfox.io")
                .contact(new Contact("名字", "www.baidu.com", "test@163.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0")
                .build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(Constant.TOKEN).description("登录后获取到的token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }

}
