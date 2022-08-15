package com.yice.edu.cn.ewb.config;

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
@ComponentScan("com.yice.edu.cn.ewb.controller")
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
    @Bean
    public Docket teacherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("老师模块模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(teacherPaths())
                .build();
    }


    @Bean
    public Docket bnUrlApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("获取NB第三方URL连接")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(nbUrlPaths())
                .build();
    }

    @Bean
    public Docket smartPen() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智能笔答题卡模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(smartPenPaths())
                .build();
    }

    private Predicate<String> smartPenPaths() {
        return or(
                regex("/dmAnswerSheet/.*")

        );
    }

    private Predicate<String> teacherPaths() {
        return or(
                regex("/teacher/.*")

        );
    }

    private Predicate<String>  nbUrlPaths() {
        return or(
                regex("/nbCourse/.*")

        );
    }
    @Bean
    public Docket studentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(studentPaths())
                .build();
    }

    @Bean
    public Docket uploadApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("图片上传模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(uploadPaths())
                .build();
    }

    @Bean
    public Docket homeworkApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("作业模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(homeworkPaths())
                .build();
    }


    @Bean
    public Docket classRegisterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("创建上课模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classRegisterPaths())
                .build();
    }
    private Predicate<String> classRegisterPaths() {
        return or(
                regex("/classRegister/.*"),
                regex("/pushIp/.*")
        );
    }

    private Predicate<String> penTracePaths() {
        return or(
                regex("/dmPentrace/.*")
        );
    }

    @Bean
    public Docket penTraceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智能笔轨迹")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(penTracePaths())
                .build();
    }


    @Bean
    public Docket latticePagerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("点阵试卷模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(latticePagerPaths())
                .build();
    }

    private Predicate<String> latticePagerPaths() {
        return or(
                regex("/latticePager/.*")
        );
    }



    private Predicate<String> uploadPaths() {
        return or(
                regex("/upload/.*")
        );
    }

    private Predicate<String> studentPaths() {
        return or(
                regex("/student/.*")

        );
    }

    private Predicate<String> homeworkPaths() {
        return or(
                regex("/homework/.*"),
                regex("/homeworkStuStatus/.*")

        );
    }


    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")

        );
    }


    @Bean
    public Docket teachingPlanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("备课资源模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(teachingPlanPaths())
                .build();
    }

    private Predicate<String> teachingPlanPaths() {
        return or(
                regex("/materialItem/.*"),
                regex("/itemPackage/.*"),
                regex("/textbookSetting/.*"),
                regex("/teachingPlan/.*"),
                regex("/personalTopics/.*"),
                regex("/schoolQusBank/.*")
        );
    }

    @Bean
    public Docket jyResourcesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教研资源模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(jyResourcesPaths())
                .build();
    }

    private Predicate<String> jyResourcesPaths() {
        return or(
                regex("/jyResouces/.*"),
                regex("/jySchoolResouces/.*")
        );
    }

    @Bean
    public Docket wisdomclassroomApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智慧课堂模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(wisdomclassroomPaths())
                .build();
    }

    private Predicate<String> wisdomclassroomPaths() {
        return or(
                regex("/classTest/.*"),
                regex("/groupManage/.*"),
                regex("/topicDetail/.*")
        );
    }

    @Bean
    public Docket classScheduleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("课表模块模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classSchedulePaths())
                .build();
    }

    private Predicate<String> classSchedulePaths() {
        return or(
                regex("/classSchedule/.*")
        );
    }

    @Bean
    public Docket tApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("字典模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(tPaths())
                .build();
    }

    private Predicate<String> tPaths() {
        return or(
                regex("/dd/.*")
        );
    }

    @Bean
    public Docket analysisApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学情分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(analysisPaths())
                .build();
    }
    private Predicate<String> analysisPaths() {
        return or(
                regex("/analysis/.*")
        );
    }

    @Bean
    public Docket coursewareApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("个人备课")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(coursewarePaths())
                .build();
    }
    private Predicate<String> coursewarePaths() {
        return or(
                regex("/courseRes/.*"),
                regex("/courseResType/.*"),
                regex("/courseTestAnswer/.*"),
                regex("/courseWare/.*"),
                regex("/jyMaterialExtend/.*"),
                regex("/jySubjectMaterialExtend/.*"),
                regex("/school/.*"),
                regex("/material/.*"),
                regex("/materialItem/.*"),
                regex("/subjectMaterial/.*")

        );
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("电子白板App ewb api")
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
