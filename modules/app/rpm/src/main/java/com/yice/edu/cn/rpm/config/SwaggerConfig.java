package com.yice.edu.cn.rpm.config;

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
@ConditionalOnExpression("'${spring.profiles.active}'!='prod'")
@ComponentScan("com.yice.edu.cn.rpm.controller")
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private SwaggerPathProvider swaggerPathProvider;
    @Bean
    public Docket loginApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("登录模块")
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(loginPaths())
                .build();
    }
    @Bean
    public Docket examApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("考试模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(examPaths())
                .build();
    }

    private Predicate<String> examPaths() {
        return or(
                regex("/onlineSchoolExam/.*")

        );
    }


    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")

        );
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("阅卷机 api")
                .description("<ol style=\"margin: 0;padding: 0;\">\n" +
                        "    <li>登录后获取到的token需要放在之后的所有请求的请求头里,请求头名称是token,值由登录后获取,Base URL 在生产环境应该是https协议,host和端口都不一样，因此必须使用变量存储</li>\n" +
                        "    <li>返回json格式固定外面套一个</br>" +
                        "{</br>" +
                        "  &nbsp;\"result\": {//本次请求的响应结果,没问题的情况下success:true,msg不返回,否则msg有相应提示,</br>" +
                        "  &nbsp;&nbsp;  \"success\": false,</br>" +
                        "  &nbsp; &nbsp; \"msg\": \"\",</br>" +
                        "   &nbsp;&nbsp; \"code\": 0</br>" +
                        "  &nbsp;},</br>" +
                        "  &nbsp;\"data\": {},</br>" +
                        "  &nbsp;\"data2\": {},</br>" +
                        "  &nbsp;\"data3\": {},</br>" +
                        "  &nbsp;\"data4\": {},</br>" +
                        "  &nbsp;\"totalCount\": 0//列表页时用到的总条数</br>" +
                        "}</br> 最好对响应结果做一层封装,如果result.success=false,则提示result.msg,抑或根据result.code做某种动作,如果result.success=true则返回</li>\n" +
                        "    <li>接口里pager对象只在查询列表时用到</li>" +
                        "</ol>")
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


