package com.yice.edu.cn.osp.config;

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
@ComponentScan("com.yice.edu.cn.osp.controller")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket oaApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("oa办公流程")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(or(
                        regex("/schoolProcess/.*"),
                        regex("/datasource/.*"),
                        regex("/myApprove/.*"),
                        regex("/myProcessApply/.*"),
                        regex("/oaStats/.*"),
                        regex("/processBusinessData/.*"),
                        regex("/processCopy/.*"),
                        regex("/processLib/.*"),
                        regex("/processSort/.*"),
                        regex("/schoolProcessApply/.*")
                ))
                .build();

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

    @Bean
    public Docket myWorkbenchApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("我的工作台")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(myWorkbenPaths())
                .build();
    }

    @Bean
    public Docket myLuboApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("我的录播资源")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(myPaths())
                .build();
    }

    private Predicate<String> myPaths() {
        return or(
                regex("/cloudCourseResource/.*")
        );
    }

    @Bean
    public Docket mySchoolYear() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校学年，学期接口测试")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(mySchoolYearPath())
                .build();
    }

    private Predicate<String> mySchoolYearPath() {
        return or(
                regex("/schoolYear/.*")
        );
    }

    @Bean
    public Docket classTestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("课堂检测")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(classTestPaths())
                .build();
    }

    @Bean
    public Docket schoolSpaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("空间开关")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(schoolSpacePaths())
                .build();
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
    public Docket jyResouceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("个人备课我的资源")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(jyResoucePaths())
                .build();
    }


    @Bean
    public Docket qusSurveyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("问卷调查")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(qusSurveyPaths())
                .build();
    }

    @Bean
    public Docket stuEvaluateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生评价")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(stuEvaluatePaths())
                .build();
    }


    @Bean
    public Docket prepareLessonsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("个人备课")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(prepareLessonsPaths())
                .build();
    }

    @Bean
    public Docket prepareCollectiveApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("集体备课")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(prepareCollectivePaths())
                .build();
    }

    @Bean
    public Docket prepareTeacherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(prepareTeacherPaths())
                .build();
    }

    @Bean
    public Docket errorbookanalysisApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("错题本分析模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(errorbookanalysisPaths())
                .build();
    }

    @Bean
    public Docket resourceCenterType() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资源中心")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(resourceCenterTypePaths())
                .build();
    }


    @Bean
    public Docket h5ResourceCenter() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("h5资源中心")
                .apiInfo(apiInfo())
                .select()
                .paths(h5ResourceCenterPaths())
                .build();
    }


    @Bean
    public Docket classScheduleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("课程表")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(classSchedulePaths())
                .build();
    }

    private Predicate<String> classSchedulePaths() {
        return or(
                regex("/classSchedule/.*"),
                regex("/pastClassSchedule/.*"),
                regex("/pastInit/.*"),
                regex("/scheduleList/.*")

        );
    }

    @Bean
    public Docket workerKqApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("职工考勤")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(workerKqPaths())
                .build();
    }

    private Predicate<String> workerKqPaths() {
        return or(
                regex("/workerKqRules/.*"),
                regex("/commenSettings/.*")
        );
    }

    @Bean
    public Docket stuInAndOutBigDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生出入校大数据")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(stuInAndOutBigDataPaths())
                .build();
    }

    private Predicate<String> stuInAndOutBigDataPaths() {
        return or(
                regex("/stuInAndOutBigData/.*")
        );
    }

    @Bean
    public Docket stuInAndOutApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("上课时间")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(stuInAndOutPaths())
                .build();
    }

    private Predicate<String> stuInAndOutPaths() {
        return or(
                regex("/stuInAndOutClassTime/.*"),
                regex("/stuInAndOutStartTime/.*")
        );
    }

    @Bean
    public Docket modeManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级模式")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(modeManagePaths())
                .build();
    }

    private Predicate<String> modeManagePaths() {
        return or(
                regex("/examMode/.*"),
                regex("/notificationMode/.*"),
                regex("/attendClassMode/.*")
        );
    }

    @Bean
    public Docket kqOriginalDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生考勤原始记录")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kqOriginalDataPaths())
                .build();
    }

    private Predicate<String> kqOriginalDataPaths() {
        return or(
                regex("/kqOriginalData/.*")

        );
    }

    @Bean
    public Docket PsycholgConsultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("心理咨询")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(PsycholgConsultPaths())
                .build();
    }

    private Predicate<String> PsycholgConsultPaths() {
        return or(
                regex("/xwPshcholgConsult/.*")

        );
    }

    @Bean
    public Docket SchoolDateCenterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("数据中心")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(SchoolDateCenterPaths())
                .build();
    }

    private Predicate<String> SchoolDateCenterPaths() {
        return or(
                regex("/schoolDateCenter/.*")
        );
    }

    @Bean
    public Docket repairNewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("报修管理New")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(repairNewPaths())
                .build();
    }

    private Predicate<String> repairNewPaths() {
        return or(
                regex("/repairNew/.*")

        );
    }

    @Bean
    public Docket xwClassifiedManagementApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("规章分类管理模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwClassifiedManagementPaths())
                .build();
    }

    private Predicate<String> xwClassifiedManagementPaths() {
        return or(
                regex("/xwClassifiedManagement/.*")

        );
    }


    @Bean
    public Docket xwRegulatoryFrameworkApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("规章制度模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwRegulatoryFrameworkPaths())
                .build();
    }

    private Predicate<String> xwRegulatoryFrameworkPaths() {
        return or(
                regex("/xwRegulatoryFramework/.*")

        );
    }

    @Bean
    public Docket KqDeviceGroupPersonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("考勤设备分组绑定人员模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(KqDeviceGroupPersonPaths())
                .build();
    }

    private Predicate<String> KqDeviceGroupPersonPaths() {
        return or(
                regex("/kqDeviceGroupPerson/.*")

        );
    }

    @Bean
    public Docket schoolQusBankApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校本题库")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(schoolQusBankPaths())
                .build();
    }

    private Predicate<String> schoolQusBankPaths() {
        return or(
                regex("/schoolQusBank/.*"),
                regex("/historyTeacherAes/.*"),
                regex("/topicQuotaResources/.*")

        );
    }

    @Bean
    public Docket kqOriginDataReceiveApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("人脸设备识别记录接收")
                .apiInfo(apiInfo())
                .select()
                .paths(kqOriginDataReceivePaths())
                .build();
    }

    private Predicate<String> kqOriginDataReceivePaths() {
        return or(
                regex("/kqOriginDataReceive/.*")

        );
    }

    @Bean
    public Docket kqDailyStatisticalApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生考勤日统计")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kqDailyStatisticalPaths())
                .build();
    }

    private Predicate<String> kqDailyStatisticalPaths() {
        return or(
                regex("/kqDailyStatistical/.*")

        );
    }


    @Bean
    public Docket wageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("工资管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(wagePaths())
                .build();
    }

    @Bean
    public Docket StuScoreApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生成绩")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(StuScorePaths())
                .build();
    }

    private Predicate<String> StuScorePaths() {
        return or(
                regex("/offlineStuScore/.*")
        );
    }

    private Predicate<String> kwReviewTaskPaths() {
        return or(
                regex("/reviewTask/.*")
        );
    }

    @Bean
    public Docket kwReviewTask() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("阅卷任务")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kwReviewTaskPaths())
                .build();
    }

    @Bean
    public Docket jySchoolResoucesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校本资源")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(jySchoolResoucesPaths())
                .build();
    }

    @Bean
    public Docket marking() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("组卷模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(markingPaths())
                .build();
    }

    private Predicate<String> markingPaths() {
        return or(
                regex("/paper/.*"),
                regex("/paperQuestion/.*"),
                regex("/myPaper/.*")
        );
    }

    @Bean
    public Docket homeworkAnalyseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("作业分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(homeworkAnalysePaths())
                .build();
    }

    private Predicate<String> homeworkAnalysePaths() {
        return or(
                regex("/homeworkAnalyse/.*"),
                regex("/homework/.*")
        );
    }

    @Bean
    public Docket semesterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学期分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(semesterPaths())
                .build();
    }

    private Predicate<String> semesterPaths() {
        return or(
                regex("/semester/.*")
        );
    }

    @Bean
    public Docket studentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(studentPaths())
                .build();
    }

    private Predicate<String> studentPaths() {
        return or(
                regex("/student/.*"),
                regex("/jwStudentGraduation/.*")
        );
    }

    @Bean
    public Docket analysis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生考试分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
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
    public Docket answerSheet() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("答题卡模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(answerSheetPaths())
                .build();
    }

    private Predicate<String> answerSheetPaths() {
        return or(
                regex("/answerSheet/.*")
        );
    }

    @Bean
    public Docket examApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("考试管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(examPaths())
                .build();
    }

    private Predicate<String> examPaths() {
        return or(
                regex("/offLineSchoolExam/.*")
        );
    }


    private Predicate<String> jySchoolResoucesPaths() {
        return or(
                regex("/jySchoolResouces/.*")
        );
    }

    private Predicate<String> wagePaths() {
        return or(
                regex("/wageType/.*"),
                regex("/wageAttribute/.*"),
                regex("/wageTypeTeacher/.*")
        );
    }

    @Bean
    public Docket docApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公文模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(docPaths())
                .build();
    }

    private Predicate<String> docPaths() {
        return or(
                regex("/doc/.*"),
                regex("/docLeader/.*"),
                regex("/docManagement/.*"),
                regex("/writing/.*"),
                regex("/writingLeader/.*"),
                regex("/writingManagement/.*")
        );
    }


    @Bean
    public Docket practiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("实践管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(practicePaths())
                .build();
    }

    private Predicate<String> practicePaths() {
        return or(
                regex("/practice/.*")
        );
    }

    @Bean
    public Docket visitorManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("访客模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
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
    public Docket kqWorkerDailyControllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教职工考勤日统计")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kqWorkerDailyControllerPaths())
                .build();
    }

    private Predicate<String> kqWorkerDailyControllerPaths() {
        return or(
                regex("/kqWorkerDaily/.*"),
                regex("/kqWorkerMonth/.*")

        );
    }

    @Bean
    public Docket kqStuEnterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("考勤学生录入")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kqStuEnterPaths())
                .build();
    }

    private Predicate<String> kqStuEnterPaths() {
        return or(
                regex("/kqStuEnter/.*")

        );
    }

    @Bean
    public Docket watchListApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("值班管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(watchListPaths())
                .build();
    }

    private Predicate<String> watchListPaths() {
        return or(
                regex("/dutyLocation/.*"),
                regex("/allSchoolDuty/.*"),
                regex("/dutyArrment/.*"),
                regex("/personalDuty/.*"),
                regex("/checkedDetail/.*")

        );
    }

    @Bean
    public Docket schoolNotifyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校通知")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
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
    public Docket studentAccountApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生账号")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(studentAccountPaths())
                .build();
    }

    @Bean
    public Docket schoolCmsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("前端接口测试")
                .apiInfo(apiInfo())
                .select()
                .paths(schoolCmsPaths())
                .build();
    }


    private Predicate<String> schoolCmsPaths() {
        return or(
                regex("/xwCmsOfficialWebsite/.*")
        );
    }

    private Predicate<String> studentAccountPaths() {
        return or(
                regex("/studentAccount/.*")
        );
    }

    @Bean
    public Docket schoolPushApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校园通知")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(schoolPushPaths())
                .build();
    }

    private Predicate<String> schoolPushPaths() {
        return or(
                regex("/schoolPush/.*")
        );
    }

    private Predicate<String> prepareLessonsPaths() {
        return or(
                regex("/teachingPlan/.*"),
                regex("/textbookSetting/.*"),
                regex("/materialItem/.*"),
                regex("/itemPackage/.*")
        );
    }

    private Predicate<String> prepareCollectivePaths() {
        return or(
                regex("/collectivePlan/.*"),
                regex("/collectivePlanTeacher/.*"),
                regex("/teacherCollection/.*"),
                regex("/teamTeachingPlan/.*"),
                regex("/jyPrepareLessonsDiscuss/.*"),
                regex("/jyPrepareLessonsDiscussReply/.*")
        );
    }

    private Predicate<String> errorbookanalysisPaths() {
        return or(
                regex("/errorbookanalysis/.*")

        );
    }

    private Predicate<String> resourceCenterTypePaths() {
        return or(
                regex("/h5nl/resourceCenter/.*")

        );
    }

    private Predicate<String> h5ResourceCenterPaths() {
        return or(
                regex("/h5nl/resourceCenter/.*")

        );
    }

    private Predicate<String> prepareTeacherPaths() {
        return or(
                regex("/teacher/.*"),
                regex("/xwTeacherImageInput/.*")
        );
    }


    private Predicate<String> jyResoucePaths() {
        return or(
                regex("/jyResouces/.*")
        );
    }

    private Predicate<String> dmPaths() {
        return or(
                regex("/dmActive/.*"),
                regex("/dmClassCard/.*"),
                regex("/dmCodeResource/.*"),
                regex("/dmCountDownManage/.*"),
                regex("/eccStudentKqRecord/.*"),
                regex("/dmActivityInfo/.*"),
                regex("/dmMeritStudent/.*"),
                regex("/dmFamousTeacher/.*"),
                regex("/dmClassMeeting/.*")
        );
    }

    private Predicate<String> loginPaths() {
        return or(
                regex("/login/.*")
        );
    }

    private Predicate<String> myWorkbenPaths() {
        return or(
                regex("/myWorkbench/.*")
        );
    }

    private Predicate<String> classTestPaths() {
        return or(
                regex("/classTest/.*")
        );
    }

    private Predicate<String> schoolSpacePaths() {
        return or(
                regex("/xwCmsSchoolSpaceConfig/.*")
        );
    }

    private Predicate<String> tPaths() {
        return or(
                regex("/dd/.*")
        );
    }


    private Predicate<String> qusSurveyPaths() {
        return or(
                regex("/qusSurvey/.*"),
                regex("/qusSurveyOption/.*"),
                regex("/qusSurveyQuestion/.*"),
                regex("/qusSurveySendObject/.*"),
                regex("/qusSurveySubmit/.*")
        );
    }

    private Predicate<String> stuEvaluatePaths() {
        return or(
                regex("/stuEvaluate/.*"),
                regex("/stuEvaluateContent/.*"),
                regex("/stuEvaluateSendObject/.*")
        );
    }

    @Bean
    public Docket dmScreenSaverApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("云班牌屏保")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dmScreenSaverPaths())
                .build();
    }

    private Predicate<String> dmScreenSaverPaths() {
        return or(
                regex("/dmScreenSaver/.*")
        );
    }

    @Bean
    public Docket dormApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("宿舍管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(jwDormPaths())
                .build();
    }

    private Predicate<String> jwDormPaths() {
        return or(
                regex("/dormBuildAdmin/.*"),
                regex("/dormPerson/.*"),
                regex("/dorm/.*"),
                regex("/dormStatistics/.*"),
                regex("/dormPersonLog/.*"),
                regex("/dormPersonOut/.*")

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
    public Docket KqApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生考勤")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(kqPaths())
                .build();
    }

    private Predicate<String> kqPaths() {
        return or(
                regex("/kqBasicRules/.*"),
                regex("/kqOriginalData/.*")
        );
    }

    @Bean
    public Docket GroupManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("分组管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(GroupManagePaths())
                .build();
    }

    private Predicate<String> GroupManagePaths() {
        return or(
                regex("/groupManage/.*")
        );
    }

    @Bean
    public Docket LatticePagerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("点阵试卷管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(LatticePagerPaths())
                .build();
    }

    private Predicate<String> LatticePagerPaths() {
        return or(
                regex("/latticePager/.*"),
                regex("/dmPagerBackground/.*"),
                regex("/dmPagerNumber/.*")
        );
    }


    @Bean
    public Docket teacherAttendanceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师考勤")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(teacherAttendancePaths())
                .build();
    }

    private Predicate<String> teacherAttendancePaths() {
        return or(
                regex("/attendanceMachine/.*"),
                regex("/teacherAttendanceRecord/.*"),
                regex("/teacherAttendanceSetting/.*"),
                regex("/teacherAttendanceSpecial/.*"),
                regex("/teacherAttendanceStatisticsEveryday/.*")
        );
    }

    @Bean
    public Docket ccApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("云课堂")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(ccPaths())
                .build();
    }

    private Predicate<String> ccPaths() {
        return or(
                regex("/cloudCourse/.*"),
                regex("/cloudCourseResource/.*"),
                regex("/cloudCourseFileResource/.*"),
                regex("/cloudSubCourse/.*"),
                regex("/cloudSubCourseLessonsFile/.*")


        );
    }

    @Bean
    public Docket cmsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("CMS")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(cmsPaths())
                .build();
    }

    private Predicate<String> cmsPaths() {
        return or(
                regex("/xwCmsStyleConfig/.*"),
                regex("/xwCmsContent/.*"),
                regex("/xwCmsContentFile/.*"),
                regex("/xwCmsColumn/.*"),
                regex("/xwCmsHeaderNavigation/.*"),
                regex("/xwCmsHomeLayout/.*")
        );
    }

    @Bean
    public Docket djApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("党建管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(djPaths())
                .build();
    }

    private Predicate<String> djPaths() {
        return or(
                regex("/djClassify/.*"),
                regex("/xwDjBranchParty/.*"),
                regex("/xwDjStudyResource/.*"),
                regex("/xwDjMyStudyTeacher/.*"),
                regex("/xwDjBranchParty/.*"),
                regex("/xwDjActivity/.*"),
                regex("/xwDjActivityUser/.*"),
                regex("/xwDjBranchParty/.*"),
                regex("/xwDjInformation/.*"),
                regex("/xwDjPhoto/.*"),
                regex("/xwDjAttachmentFile/.*"),
                regex("/xwPartyCommittee/.*"),
                regex("/xwPartyMember/.*"),
                regex("/xwDjAttachmentFile/.*"),
                regex("/xwDjDoc/.*"),
                regex("/xwDjDocLeader/.*"),
                regex("/xwDjDocManagement/.*")
        );
    }

    @Bean
    public Docket dyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("德育系统")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dyPaths())
                .build();
    }


    private Predicate<String> dyPaths() {
        return or(
                regex("/mesCommonConfig/.*"),
                regex("/mesCustomTitle/.*"),
                regex("/mesInstitution/.*"),
                regex("/mesTimeStatus/.*"),
                regex("/mesUserAuthInstitution/.*"),
                regex("/mesInstitutionAudit/.*"),
                regex("/mesOperateRecord/.*"),
                regex("/mesInspectRecord/.*"),
                regex("/mesAppletsRuleRecord/.*"),
                regex("/mesSchoolEvaluation/.*")

        );
    }

    @Bean
    public Docket xwPartyMemberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("党建模块党员管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwPartyMemberPaths())
                .build();
    }

    private Predicate<String> xwPartyMemberPaths() {
        return or(
                regex("/xwPartyMember/.*")
        );
    }

    @Bean
    public Docket xwSearchOwnerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("寻找失主表模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwSearchOwnerPaths())
                .build();
    }

    private Predicate<String> xwSearchOwnerPaths() {
        return or(
                regex("/xwSearchOwner/.*")
        );
    }

    @Bean
    public Docket xwSearchGoodsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("寻找物品表模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwSearchGoodsPaths())
                .build();
    }

    private Predicate<String> xwSearchGoodsPaths() {
        return or(
                regex("/xwSearchGoods/.*")
        );
    }


    @Bean
    public Docket xwAssertsUnitApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产单位")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwAssertsUnit())
                .build();
    }

    private Predicate<String> xwAssertsUnit() {
        return or(
                regex("/assetsUnit/.*")
        );
    }

    @Bean
    public Docket xwAssertsContractApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产合同")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwAssertsContract())
                .build();
    }

    private Predicate<String> xwAssertsContract() {
        return or(
                regex("/assetsContract/.*")
        );
    }

    @Bean
    public Docket xwAssertsOutNewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产出库")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(xwAssertsOutNew())
                .build();
    }


    private Predicate<String> compareQuality() {
        return or(
                regex("/compareQuality/.*")
        );
    }

    @Bean
    public Docket compareQualityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("综合素质")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(compareQuality())
                .build();
    }

    private Predicate<String> xwAssertsOutNew() {
        return or(
                regex("/assetsOutNew/.*")
        );
    }

    @Bean
    public Docket assertsStockDetailApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产明细")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assertsStockDetail())
                .build();
    }

    private Predicate<String> assertsStockDetail() {
        return or(
                regex("/assetsStockDetail/.*")
        );
    }


    @Bean
    public Docket assertsCategoryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产类型")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsCategory())
                .build();
    }

    private Predicate<String> assetsCategory() {
        return or(
                regex("/assetsCategory/.*")
        );
    }

    @Bean
    public Docket assetsWarehouseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产仓库")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsWarehouse())
                .build();
    }

    private Predicate<String> assetsWarehouse() {
        return or(
                regex("/assetsWarehouse/.*")
        );
    }

    @Bean
    public Docket assetsFileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产档案")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsFile())
                .build();
    }

    private Predicate<String> assetsFile() {
        return or(
                regex("/assetsFile/.*")
        );
    }

    @Bean
    public Docket assetsInNewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产入库")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsInNew())
                .build();
    }

    private Predicate<String> assetsInNew() {
        return or(
                regex("/assetsInNew/.*")
        );
    }


    @Bean
    public Docket assetsInOutQueryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("出入库统计")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsInOutQuery())
                .build();
    }

    private Predicate<String> assetsInOutQuery() {
        return or(
                regex("/assetsInOutQuery/.*")
        );
    }

    @Bean
    public Docket repairStaffApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("维修人员")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(repairStaff())
                .build();
    }

    private Predicate<String> repairStaff() {
        return or(
                regex("/repairStaff/.*")
        );
    }


    @Bean
    public Docket assetsStockQueryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资产库存查询")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(assetsStockQuery())
                .build();
    }

    private Predicate<String> assetsStockQuery() {
        return or(
                regex("/assetsStockQuery/.*")
        );
    }

    @Bean
    public Docket webIndexApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("首页接口")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(webIndexPath())
                .build();
    }

    private Predicate<String> webIndexPath() {
        return or(
                regex("/index/.*"),
                regex("/teacherWebShortcut/.*"),
                regex("/webShortcut/.*")
        );
    }

    private Predicate<String> eduEvaluationPath() {
        return or(
                regex("/stuDocument/.*")
        );
    }

    @Bean
    public Docket eduEvaluationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教育评价")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(eduEvaluationPath())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线教育平台,osp")
                .description("<h4>接口里pager对象只在查询列表时用到</h4>")
                .termsOfServiceUrl("http://springfox.io")
                .contact(new Contact("名字", "www.baidu.com", "test@163.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket riseClazz() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("升班接口")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(riseClazzPath())
                .build();
    }

    private Predicate<String> riseClazzPath() {
        return or(
                regex("/riseClazz/.*")
        );
    }

    @Bean
    public Docket publicConditionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("基础条件查询接口")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(publicConditionPaths())
                .build();
    }

    private Predicate<String> publicConditionPaths() {
        return or(
                regex("/publicCondition/.*")

        );
    }

    @Bean
    public Docket classesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(classesPaths())
                .build();
    }

    private Predicate<String> classesPaths() {
        return or(
                regex("/jwClasses/.*")

        );
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
                regex("/notifyPerson/.*"),
                regex("/stuLeave/.*")
        );
    }

    @Bean
    public Docket analyseExamTopicTypeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学情题型分析")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(analyseExamTopicTypePaths())
                .build();
    }

    private Predicate<String> analyseExamTopicTypePaths() {
        return or(
                regex("/analyseExamStuTopicType/.*"),
                regex("/analyseExamTopicType/.*")
        );
    }

    @Bean
    public Docket prepareLessonsNewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("备课2.0")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(prepareLessonsNewPaths())
                .build();
    }

    private Predicate<String> prepareLessonsNewPaths() {
        return or(
                regex("/jyMaterialExtend/.*"),
                regex("/jySubjectMaterialExtend/.*")
        );
    }

    @Bean
    public Docket teacherPostApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师职务管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(teacherPostPaths())
                .build();
    }

    @Bean
    public Docket ycVerifaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("亿策人脸识别")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(ycVerifacePaths())
                .build();
    }

    private Predicate<String> ycVerifacePaths() {
        return or(
                regex("/ycVerifaceBlacklist/.*"),
                regex("/ycVerifaceAlarmReceiver/.*")
        );
    }

    private Predicate<String> teacherPostPaths() {
        return or(
                regex("/teacherPost/.*")
        );
    }

    public Docket electiveCourse() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("选修课管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(electiveCoursePaths())
                .build();
    }

    private Predicate<String> electiveCoursePaths() {
        return or(
                regex("/electiveCourse/.*"),
                regex("/electiveStudent/.*")
        );
    }

    public Docket dmMeritStudent() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("三好学生模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .select()
                .paths(dmMeritStudentPaths())
                .build();
    }

    private Predicate<String> dmMeritStudentPaths() {
        return or(
                regex("/dmMeritStudent/.*")
        );
    }



}
