package com.yice.edu.cn.tap.config;

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
@ComponentScan("com.yice.edu.cn.tap.controller")
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
    public Docket classScheduleApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师课程表模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classSchedulePaths())
                .build();
    }

    @Bean
    public Docket correspondenceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通讯录模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(correspondencePaths())
                .build();
    }

    @Bean
    public Docket ycVerifaceOtherCheckApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("人脸识别模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(ycVerifaceOtherCheckPaths())
                .build();
    }

    @Bean
    public Docket microLessonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微课导出文档模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(microLessonPaths())
                .build();
    }

    @Bean
    public Docket dyClassRuleRecordApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("德育小程序班级榜单")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dyClassRuleRecordPaths())
                .build();
    }




    @Bean
    public Docket feedbackApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户反馈模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(feedbackPaths())
                .build();
    }
    private Predicate<String> feedbackPaths() {
        return or(
                regex("/feedback/.*")

        );
    }
    @Bean
    public Docket appGuiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app功能引导模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(appGuiPaths())
                .build();
    }
    private Predicate<String> appGuiPaths() {
        return or(
                regex("/appGuidance/.*")

        );
    }
    @Bean
    public Docket watchListApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("值班反馈模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(watchListPaths())
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
    public Docket classesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classesPaths())
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
    public Docket repairNewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产维修New")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(repairNewPaths())
                .build();
    }


    @Bean
    public Docket oaApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("oa办公流程")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(oaPaths())
                .build();
    }

    @Bean
    public Docket reviewTaskApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("阅卷任务")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(reviewTaskPaths())
                .build();
    }

    private Predicate<String> reviewTaskPaths() {
        return or(
                regex("/reviewTask/.*")
        );
    }

    @Bean
    public Docket dmIotApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("物联网获取")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmIotPaths())
                .build();
    }

    private Predicate<String> dmIotPaths() {
        return or(
                regex("/dmIot/.*")
        );
    }
    private Predicate<String> oaPaths() {
        return or(
                regex("/schoolProcess/.*"),
                regex("/datasource/.*")
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
    private Predicate<String> prepLessonResourceFilePaths() {
        return or(
                regex("/prepLessonResourceFile/.*"),
                regex("/lessonResMediaFile/.*")

        );
    }

    @Bean
    public Docket djApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("党建APP管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(djPaths())
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

    private Predicate<String> djPaths() {
        return or(
                regex("/djClassify/.*"),
                regex("/xwDjActivity/.*"),
                regex("/xwDjActivityUser/.*"),
                regex("/xwDjMyStudyTeacher/.*"),
                regex("/xwDjDocLeader/.*"),
                regex("/xwDjStudyResource/.*"),
                regex("/xwDjDoc/.*"),
                regex("/xwDjDocLeader/.*"),
                regex("/xwDjDocManagement/.*")
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

    private Predicate<String> qusSurveyPaths() {
        return or(
                regex("/qusSurvey/.*")
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
    public Docket kqWorkerDailyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师考勤手机打卡")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(kqWorkerDailyPaths())
                .build();
    }
    private Predicate<String> kqWorkerDailyPaths() {
        return or(
                regex("/kqWorkerDaily/.*")

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
                .paths(dmkqPaths())
                .build();
    }


    private Predicate<String> dmkqPaths() {
        return or(
                regex("/studentKq/.*")
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
                regex("/schoolNotify/.*"),
                regex("/schoolNotifySendObject/.*")
        );
    }
    @Bean
    public Docket xwStudentKqApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校务学生考勤模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwStudentKqPaths())
                .build();
    }

    private Predicate<String> xwStudentKqPaths() {
        return or(
                regex("/xwStudentKq/.*")
                );
    }
    @Bean
    public Docket schoolActiveApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校班牌活动模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolActivePaths())
                .build();
    }

    private Predicate<String> schoolActivePaths() {
        return or(
                regex("/dmActivityInfo/.*"),
                regex("/dmActivityItem/.*"),
                regex("/dmActivitySiginUp/.*")
        );
    }

    @Bean
    public Docket Doc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公文模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(docWritingPaths())
                .build();
    }

    private Predicate<String> docWritingPaths() {
        return or(
                regex("/writing/.*"),
                regex("/writingLeader/.*"),
                regex("/writingManagement/.*"),
                regex("/doc/.*"),
                regex("/docLeader/.*"),
                regex("/docManagement/.*")
        );
    }

    private Predicate<String> departmentPaths() {
        return or(
                regex("/department/.*"),
                regex("/departmentTeacher/.*")
        );

    }

    @Bean
    public Docket departmentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("组织架构")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(departmentPaths())
                .build();
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
                regex("/stuEvaluate/.*"),
                regex("/stuEvaluateContent/.*"),
                regex("/stuEvaluateSendObject/.*")
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
    public Docket classImgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级照片模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classImgPaths())
                .build();
    }

    private Predicate<String> classImgPaths() {
        return or(
                regex("/dmClassPhoto/.*")
        );
    }

    @Bean
    public Docket xwTeacherImgInputApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师图像录入模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwTeacherImgInputPaths())
                .build();
    }

    private Predicate<String> xwTeacherImgInputPaths() {
        return or(
                regex("/xwTeacherImageInput/.*")
        );
    }

    @Bean
    public Docket dutyManagementApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("新值班管理模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dutyManagementPaths())
                .build();
    }
    private Predicate<String> dutyManagementPaths() {
        return or(
                regex("/dutyManagement/.*")
        );
    }


    @Bean
    public Docket DyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("德育常规检查模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(DyPaths())
                .build();
    }
    private Predicate<String> DyPaths() {
        return or(
                regex("/dyIndex/.*"),
                regex("/mesInspectRecord/.*"),
                regex("/mesAttachFile/.*"),
                regex("/mesInstitutionAudit/.*")
        );
    }
















    @Bean
    public Docket dmScreenSaveApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("云班牌屏保管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmScreenSaverPaths())
                .build();
    }

    private Predicate<String> dmScreenSaverPaths() {
        return or(
                regex("/dmScreenSaver/.*")
        );
    }


    private Predicate<String> repairNewPaths() {
        return or(
                regex("/repairNew/.*")
        );
    }
    private Predicate<String> uploadPaths() {
        return or(
                regex("/upload/.*")
        );
    }
    private Predicate<String> schoolPushPaths() {
        return or(
                regex("/schoolPush/.*")

        );
    }
    private Predicate<String> studentPaths() {
        return or(
                regex("/student/.*")

        );
    }
    private Predicate<String> spacePaths() {
        return or(
                regex("/journal/.*")

        );
    }

    private Predicate<String> homeworkPaths() {
        return or(
                regex("/homework/.*"),
                regex("/homeworkStuStatus/.*"),
                regex("/jyKnowledge/.*"),
                regex("/material/.*"),
                regex("/materialItem/.*"),
                regex("/subjectMaterial/.*")

        );
    }

    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")

        );
    }

    private Predicate<String> correspondencePaths() {
        return or(
                regex("/correspondence/.*")

        );
    }

    private Predicate<String> ycVerifaceOtherCheckPaths() {
        return or(
                regex("/YcVerifaceOtherCheckController/.*")

        );
    }

    private Predicate<String> watchListPaths() {
        return or(
                regex("/watchList/.*")

        );
    }

    private Predicate<String> microLessonPaths() {
        return or(
                regex("/MicroLessonController/.*")

        );
    }

    private Predicate<String> dyClassRuleRecordPaths() {
        return or(
                regex("/mesAppletsRuleRecord/.*"),
                regex("/mesAppletsClassRule/.*")

        );
    }

    private Predicate<String> classSchedulePaths(){
        return  or(
                regex("/classSchedule/.*")
        );
    }

    private Predicate<String> indexPaths() {
        return or(
                regex("/index/.*"),
                regex("/appIndex/.*")

        );
    }
    
    private Predicate<String> classesPaths() {
        return or(
                regex("/jwClasses/.*")

        );
    }

    @Bean
    public Docket xwTeacherKqApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师考勤")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(teacherKqPaths())
                .build();
    }

    private Predicate<String> teacherKqPaths() {
        return or(
                regex("/teacherAttendanceStatisticsEveryday/.*")

        );
    }


    @Bean
    public Docket assetsStockDetailApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产扫码")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(assetsStockDetailApiPaths())
                .build();
    }

    private Predicate<String> assetsStockDetailApiPaths() {
        return or(
                regex("/assetsStockDetail/.*")
        );
    }

    @Bean
    public Docket pushDetaiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("推送消息")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(pushDetaiPaths())
                .build();
    }

    @Bean
    public Docket dmClassVideoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班牌班级视频")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmClassVideoPaths())
                .build();
    }

    @Bean
    public Docket dmLockScreenPhotoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("屏保相册")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmLockScreenPhotoPaths())
                .build();
    }

    @Bean
    public Docket dmClassCardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("云班牌管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmClassCardPaths())
                .build();
    }

    private Predicate<String> pushDetaiPaths() {
        return or(
                regex("/pushDetail/.*")

        );
    }

    private Predicate<String> dmClassVideoPaths() {
        return or(
                regex("/dmClassVideo/.*")
        );
    }

    private Predicate<String> dmLockScreenPhotoPaths() {
        return or(
                regex("/dmLockScreenPhoto/.*")
        );
    }

    private Predicate<String> dmClassCardPaths() {
        return or(
                regex("/dmClassCard/.*")
        );
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("教师端App tap api")
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
    public Docket xwDormManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("宿舍管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwDormManage())
                .build();
    }

    private Predicate<String> xwDormManage() {
        return or(
                regex("/dorm/.*"),
                regex("/dormPerson/.*")
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

    @Bean
    public Docket locConfigApplicanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校打卡配置")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(locConfigApplicanPaths())
                .build();
    }
    private Predicate<String> locConfigApplicanPaths() {
        return or(
                regex("/locConfig/.*")
        );
    }

    @Bean
    public Docket ycVerifaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("yc人脸识别陌生人")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(ycVerifacePaths())
                .build();
    }
    private Predicate<String> ycVerifacePaths() {
        return or(
                regex("/kqOriginalData/.*")
        );
    }
}
