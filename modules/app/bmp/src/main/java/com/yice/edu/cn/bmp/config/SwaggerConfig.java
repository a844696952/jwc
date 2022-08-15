package com.yice.edu.cn.bmp.config;

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
@ComponentScan("com.yice.edu.cn.bmp.controller")
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
    public Docket indexApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("首页模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(indexPaths())
                .build();
    }

    @Bean
    public Docket forgetApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("忘记密码模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(forgetPaths())
                .build();
    }

    @Bean
    public Docket registerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家长注册模块")
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(registerPaths())
                .build();
    }

    @Bean
    public Docket corresponseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通讯录")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(corresponse())
                .build();
    }

    @Bean
    public Docket feedbackApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户反馈")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(feedbackPath())
                .build();
    }

    @Bean
    public Docket reviewTaskApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("阅卷模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(reviewTaskPath())
                .build();
    }

    private Predicate<String> reviewTaskPath() {
        return or(
                regex("/reviewTask/.*")

        );
    }
    private Predicate<String> feedbackPath() {
        return or(
                regex("/feedback/.*")

        );
    }

    @Bean
    public Docket appGuiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户功能引导")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(appGuiPath())
                .build();
    }
    private Predicate<String> appGuiPath() {
        return or(
                regex("/appGuidance/.*")

        );
    }
    @Bean
    public Docket classSchedule(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生课程表模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classSchedulePaths())
                .build();

    }


    @Bean
    public Docket analysisApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学情分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(analysisPath())
                .build();

    }
    private Predicate<String> analysisPath() {
        return or(
                regex("/analysis/.*")
        );
    }
    @Bean
    public Docket xwStudentKqApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生考勤")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwStudentKqPath())
                .build();

    }
    private Predicate<String> xwStudentKqPath() {
        return or(
                regex("/xwStudentKq/.*")
        );
    }

    @Bean
    public Docket stuInAndOutApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生出入校模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(stuInAndOutPaths())
                .build();
    }

    private Predicate<String> stuInAndOutPaths() {
        return or(
                regex("/kqOriginalData/.*"),
                regex("/kqStuEnter/.*")
        );
    }


    @Bean
    public Docket visitorManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("访客模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(visitorManagePaths())
                .build();
    }

    private Predicate<String> visitorManagePaths() {
        return or(
                regex("/visitor/.*")
        );
    }



    @Bean
    public Docket appVersionControlApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app版本控制")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(appVersionControlFilePath())
                .build();
    }

    private Predicate<String> appVersionControlFilePath() {
        return or(
                regex("/appVersionControl/.*")

        );
    }
    @Bean
    public Docket bindingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("绑定孩子模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(bindingPaths())
                .build();
    }

    @Bean
    public Docket homeworkStudentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生作业模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(homeworkStudentPaths())
                .build();
    }

    @Bean
    public Docket studentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生信息模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(studentPaths())
                .build();
    }
    @Bean
    public Docket spaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("个人空间")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(spacePaths())
                .build();
    }

    @Bean
    public Docket schoolPushApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校园通知模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolPushPaths())
                .build();
    }

    @Bean
    public Docket qusSurveyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("问卷调查")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(qusSurveyPaths())
                .build();
    }
    @Bean
    public Docket xwSearchApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("失物招领模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwSearchPaths())
                .build();
    }

    private Predicate<String> xwSearchPaths() {
        return or(
                regex("/xwSearchGoods/.*"),
                regex("/xwSearchOwner/.*")
        );
    }


    @Bean
    public Docket prepLessonResourceFileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("运营平台资源模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(prepLessonResourceFilePaths())
                .build();
    }

    @Bean
    public Docket dmMsgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家校互动消息")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmMsgPaths())
                .build();
    }

    private Predicate<String> dmMsgPaths() {
        return or(
                regex("/dmMsg/.*"),
                regex("/receiveMsg/.*")
        );
    }

    @Bean
    public Docket appPermApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("小程序和app端权限树")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(appPermPaths())
                .build();
    }
    private Predicate<String> appPermPaths() {
        return or(
                regex("/appPerm/.*")

        );
    }

    @Bean
    public Docket dmKqStatistics() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌考勤统计")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmKqPaths())
                .build();
    }

    @Bean
    public Docket dmSchoolActive() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌V3.2学校活动")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmSchoolActivePaths())
                .build();
    }


    private Predicate<String> dmKqPaths() {
        return or(
                regex("/studentKq/.*")
        );
    }

    private Predicate<String> dmSchoolActivePaths() {
        return or(
                regex("/dmActivityInfo/.*")
        );
    }



    @Bean
    public Docket dmHonourRollApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("光荣榜列表")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmHonourRollPath())
                .build();
    }
    private Predicate<String> dmHonourRollPath() {
        return or(
                regex("/dmHonourRoll/.*")

        );
    }


    private Predicate<String> prepLessonResourceFilePaths() {
        return or(
                regex("/prepLessonResourceFile/.*"),//文档图片文件
                regex("/lessonResMediaFile/.*"),//多媒体文件
                regex("/collectorFile/.*"),//文件收藏
                regex("/subjectMaterial/.*"),//科目教材
                regex("/DdController/.*")//数据字典
        );
    }



    private Predicate<String> schoolPushPaths() {
        return or(
                regex("/schoolPush/.*")

        );
    }

    private Predicate<String> spacePaths() {
        return or(
                regex("/journal/.*")

        );
    }

    private Predicate<String> corresponse() {
        return or(
                regex("/correspondence/.*")

        );
    }
    private Predicate<String> studentPaths() {
        return or(
                regex("/student/.*")

        );
    }
    private Predicate<String> homeworkStudentPaths() {
        return or(
                regex("/homeworkStudent/.*")

        );
    }
    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")

        );
    }

    private Predicate<String> indexPaths() {
        return or(
                regex("/index/.*"),
                regex("/checkIn/.*"),
                regex("/appIndex/.*")


        );
    }
    private Predicate<String> forgetPaths() {
        return or(
                regex("/forget/.*")

        );
    }
    private Predicate<String> bindingPaths() {
        return or(
                regex("/binding/.*")

        );
    }

    private Predicate<String> registerPaths() {
        return or(
                regex("/register/.*")

        );
    }

    private Predicate<String> classSchedulePaths(){
        return  or(
                regex("/classSchedule/.*")
        );
    }
    private Predicate<String> qusSurveyPaths() {
        return or(
                regex("/qusSurvey/.*")
        );
    }

    @Bean
    public Docket stuEvaluateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生评价")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(stuEvaluatePaths())
                .build();
    }
    private Predicate<String> stuEvaluatePaths() {
        return or(
                regex("/stuEvaluate/.*")
        );
    }


    @Bean
    public Docket schoolNotifyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校通知")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolNotifyPaths())
                .build();
    }
    private Predicate<String> schoolNotifyPaths() {
        return or(
                regex("/schoolNotifySendObject/.*")
        );
    }

    @Bean
    public Docket parentMsgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家长班牌消息")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(parentMsgPaths())
                .build();
    }

    private Predicate<String> parentMsgPaths() {
        return or(
                regex("/parentmsg/.*")
        );
    }

    @Bean
    public Docket dmParentRelationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家长绑定孩子")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmParentRelationFilePath())
                .build();
    }

    private Predicate<String> dmParentRelationFilePath() {
        return or(
                regex("/dmParentRelation/.*")

        );
    }

    @Bean
    public Docket dmParentQuickReplyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家校互联家长设置快捷回复")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmParentQuickReplyFilePath())
                .build();
    }

    private Predicate<String> dmParentQuickReplyFilePath() {
        return or(
                regex("/dmParentQuickReply/.*")

        );
    }
    @Bean
    public Docket pushDetailApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("推送消息")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(pushDetailPath())
                .build();
    }

    private Predicate<String> pushDetailPath() {
        return or(
                regex("/pushDetail/.*")

        );
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("学生端App bmp api")
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

    @Bean
    public Docket stuLeaveManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生请假")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(stuLeaveManageFile())
                .build();
    }

    private Predicate<String> stuLeaveManageFile() {
        return or(
                regex("/stuLeave/.*")
        );
    }
    @Bean
    public Docket electiveCourse() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("选修课管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(electiveCoursePaths())
                .build();
    }

    @Bean
    public Docket dormManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("宿舍管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dormManage())
                .build();
    }

    private Predicate<String> dormManage() {
        return or(
                regex("/dormPerson/.*")
        );
    }

    @Bean
    public Docket mesAppletsRuleRecordApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("德育小程序")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(mesAppletsRuleRecord())
                .build();
    }

    private Predicate<String> mesAppletsRuleRecord() {
        return or(
                regex("/mesAppletsRuleRecord/.*")
        );
    }

    @Bean
    public Docket houseApplicanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("住宿申请管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(jwHouseApplicanPaths())
                .build();
    }
    private Predicate<String> jwHouseApplicanPaths() {
        return or(
                regex("/houseApplican/.*"),
                regex("/houseApplicanStudents/.*"),
                regex("/houseApplicanTeachers/.*"),
                regex("/houseApplicanFiles/.*")

        );
    }

    private Predicate<String> electiveCoursePaths() {
        return or(
                regex("/electiveCourse/.*"),
                regex("/electiveStudent/.*")
        );
    }
}
