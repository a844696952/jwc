package com.yice.edu.cn.ecc.config;

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
@ComponentScan("com.yice.edu.cn.ecc.controller")
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private SwaggerPathProvider swaggerPathProvider;
    @Bean
    public Docket dmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校活动模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmPaths())
                .build();
    }
    @Bean
    public Docket checkInApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("考勤打卡模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(checkInPaths())
                .build();
    }

    @Bean
    public Docket schoolApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校简介模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolInPaths())
                .build();
    }
    @Bean
    public Docket schoolHonourApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校荣誉模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolHonourInPaths())
                .build();
    }
    @Bean
    public Docket schoolBigNewsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校大事件模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolBigNewsInPaths())
                .build();
    }
    @Bean
    public Docket classCardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌登陆模块")
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classCardPaths())
                .build();
    }


    @Bean
    public Docket countDownManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌3.0校园信息管理")
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(countDownManagePaths())
                .build();
    }

    @Bean
    public Docket classesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级信息管理模块")
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
                .groupName("学校通知推送模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(schoolPush())
                .build();
    }
    @Bean
    public Docket weatherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("天气预报模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(weatherPush())
                .build();
    }
    @Bean
    public Docket classSchedulerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学校班级课表模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(classSchedulerPush())
                .build();
    }
    @Bean
    public Docket teacherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教师模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(teacherPaths())
                .build();
    }

    @Bean
    public Docket dmScreenSaverApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌屏保")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmScreenSaverPaths())
                .build();
    }

    @Bean
    public Docket parentMsgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家长消息")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(parentMsgPaths())
                .build();
    }

    @Bean
    public Docket sturespMsgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生班牌消息回复")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(sturespMsgPaths())
                .build();
    }
    @Bean
    public Docket xwSearchGoodsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("寻找物品模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwSearchGoods())
                .build();
    }
    @Bean
    public Docket xwSearchOwnerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("寻找失主模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(xwSearchOwner())
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
    public Docket dmHonourRollApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("光荣榜")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmHonourRollPaths())
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
    public Docket dmStudentAspectApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("学生人脸特征")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmStudentAspect())
                .build();
    }

    private Predicate<String> dmStudentAspect() {
        return or(
                regex("/dmStudentAspect/.*")
        );
    }


    @Bean
    public Docket dmMeritStudentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("三好学生模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmMeritStudent())
                .build();
    }

    private Predicate<String> dmMeritStudent() {
        return or(
                regex("/dmMeritStudent/.*")
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






    @Bean
    public Docket modeManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级模式")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(modeManagePaths())
                .build();
    }

    private Predicate<String> modeManagePaths() {
        return or(
                regex("/notificationMode/.*"),
                regex("/examMode/.*")
        );
    }

    @Bean
    public Docket dmMobileRedBannerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("流动红旗")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmMobileRedBannerPaths())
                .build();
    }

    @Bean
    public Docket dmStudentAttendantApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("值日生")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmStudentAttendantPaths())
                .build();
    }

    @Bean
    public Docket dmCameraApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌摄像头管理")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmCameraPaths())
                .build();
    }

    private Predicate<String> dmCameraPaths() {
        return or(
                regex("/dmCamera/.*")
        );
    }

    @Bean
    public Docket eccStudentFaceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌人脸")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(eccStudentFacePaths())
                .build();
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

    private Predicate<String> eccStudentFacePaths() {
        return or(
                regex("/kqEccStudentFace/.*"),
                regex("/studentKq/.*")
        );
    }

    @Bean
    public Docket jwClassesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("班级信息模块")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(jwClassesPaths())
                .build();
    }

    private Predicate<String> jwClassesPaths() {
        return or(
                regex("/jwClasses/.*")
        );
    }

    @Bean
    public Docket dmClassMeetingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("电子班牌班会")
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo())
                .pathProvider(swaggerPathProvider)
                .select()
                .paths(dmClassMeetingPaths())
                .build();
    }

    private Predicate<String> dmClassMeetingPaths() {
        return or(
                regex("/dmClassMeeting/.*")
        );
    }


    private Predicate<String> countDownManagePaths() {
        return or(
                regex("/dmCountDownManage/.*")
        );
    }

    private Predicate<String> dmHonourRollPaths() {
        return or(
                regex("/dmHonourRoll/.*")
        );
    }

    private Predicate<String> dmScreenSaverPaths() {
        return or(
                regex("/dmScreenSaver/.*")
        );
    }
    private Predicate<String> teacherPaths() {
        return or(
                regex("/teacher/.*"),
                regex("/dmFamousTeacher/.*")
        );
    }

    private Predicate<String> classSchedulerPush() {
        return or(
                regex("/classSchedule/.*")
        );
    }
    private Predicate<String> weatherPush() {
        return or(
                regex("/weather/.*")
        );
    }
    private Predicate<String> dmPaths() {
        return or(
                regex("/dmActive/.*")
        );
    }
    private Predicate<String> checkInPaths() {
        return or(
                regex("/check/.*")
        );
    }
    private Predicate<String> schoolInPaths() {
        return or(
                regex("/school/.*")
        );
    }
    private Predicate<String> schoolHonourInPaths() {
        return or(
                regex("/dmSchoolHonour/.*")
        );
    }
    private Predicate<String> schoolBigNewsInPaths() {
        return or(
                regex("/dmSchoolBigNews/.*")
        );
    }
    private Predicate<String> classCardPaths() {
        return or(
                regex("/dmClassCard/.*")
        );
    }

    private Predicate<String> classesPaths() {
        return or(
                regex("/dmClassInfMgr/.*")
        );
    }

    private Predicate<String> schoolPush() {
        return or(
                regex("/schoolPush/.*")
        );
    }

    private Predicate<String> parentMsgPaths() {
        return or(
                regex("/parentmsg/.*"),
                regex("/dmParentQuickReply/.*"),
                regex("/dmParentRelation/.*")
        );
    }

    private Predicate<String> sturespMsgPaths() {
        return or(
                regex("/sturespmsg/.*")
        );
    }
    private Predicate<String> xwSearchGoods() {
        return or(
                regex("/xwSearchGoods/.*")
        );
    }
    private Predicate<String> xwSearchOwner() {
        return or(
                regex("/xwSearchOwner/.*")
        );
    }

    private Predicate<String> dmClassVideoPaths() {
        return or(
                regex("/dmClassVideo/.*")
        );
    }



    private Predicate<String> dmMobileRedBannerPaths() {
        return or(
                regex("/dmMobileRedBanner/.*")
        );
    }

    private Predicate<String> dmStudentAttendantPaths() {
        return or(
                regex("/dmStudentAttendant/.*")
        );
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("电子班牌 ecc api")
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
