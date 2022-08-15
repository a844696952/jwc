package com.yice.edu.cn.yed.config;

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
@ComponentScan("com.yice.edu.cn.yed.controller")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket standardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("增删改查")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(standardPaths())
                .build();
    }
    private Predicate<String> standardPaths() {
        return or(
                regex("/standard/.*")
        );
    }

    @Bean
    public Docket holidayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("节假日")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(holidayPaths())
                .build();
    }

    @Bean
    public Docket applyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("第三方应用绑定")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(applyPaths())
                .build();
    }

    private Predicate<String> applyPaths() {
        return or(
                regex("/thirdParty/.*"),
                regex("/applySchool/.*"),
                regex("/applySchoolTeacher/.*")
        );
    }
    private Predicate<String> holidayPaths() {
        return or(
                regex("/holiday/.*")
        );
    }
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
    public Docket cmsSchoolSpaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校空间模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(schoolSpacePaths())
                .build();
    }

    private Predicate<String> schoolSpacePaths() {
        return or(
                regex("/xwCmsSchoolSpace/.*")
        );
    }


    @Bean
    public Docket daPingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("大屏")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(daPingPaths())
                .build();
    }
    private Predicate<String> daPingPaths() {
        return or(
                regex("/yedHomePage/.*")
        );
    }

    @Bean
    public Docket homepageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("首页模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(homepagePaths())
                .build();
    }
    private Predicate<String> homepagePaths() {
        return or(
                regex("/yedHomePage/.*")
        );
    }

    @Bean
    public Docket dataCenterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("数据中心")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dataCenterPaths())
                .build();
    }
    private Predicate<String> dataCenterPaths() {
        return or(
                regex("/schoolCompusCenter/.*")
        );
    }
    @Bean
    public Docket appPrem() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("appPerm模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(appPremPaths())
                .build();
    }
    private Predicate<String> appPremPaths() {
        return or(
                regex("/appPerm/.*")
        );
    }
    @Bean
    public Docket subjectResourceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("题库资源模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(subjectResourcePaths())
                .build();
    }
    private Predicate<String> subjectResourcePaths() {
        return or(
                regex("/material/.*"),
                regex("/examinationPoint/.*"),
                regex("/materialItem/.*"),
                regex("/subjectMaterial/.*"),
                regex("/resourcePlatform/.*"),
                regex("/historyTitleQuota/.*"),
                regex("/topicQuotaResources/.*")

        );
    }
    
    @Bean
    public Docket ddApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("数据字典模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(ddPaths())
                .build();
    }
    private Predicate<String> ddPaths() {
        return or(
                regex("/dd/.*")
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
                regex("/eehSchool/.*")
        );
    }

    @Bean
    public Docket dyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("德育模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dyPaths())
                .build();
    }
    private Predicate<String> dyPaths() {
        return or(
                regex("/mesAppletsRule/.*")
        );
    }





    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统管理后台")
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
