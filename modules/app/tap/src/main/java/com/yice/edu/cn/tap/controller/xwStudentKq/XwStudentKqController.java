package com.yice.edu.cn.tap.controller.xwStudentKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import com.yice.edu.cn.tap.service.xwStudentKq.XwStudentKqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.tap.controller.xwStudentKq
 * @Author: Administrator
 * @CreateTime: 2019-03-07 12:40
 * @Description: ${Description}
 */
@RestController
@RequestMapping("/xwStudentKq")
@Api(value = "/xwStudentKq", description = "校务学生考勤模块")
public class XwStudentKqController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private XwStudentKqService xwStudentKqService;

    @PostMapping("/findClassTeacherIsDirector")
    @ApiOperation(value = "查找该教师当班主任的班级", notes = "返回响应对象,不包含总条数")
    public ResponseJson findClassTeacherIsDirector(
            @ApiParam(value = "传教师id id")
            @RequestBody Teacher teacher) {
        if (teacher.getId() == null) {
            teacher.setId(myId());
        }
        List<Teacher> isDis = teacherService.findClassTeacherIsDirector(teacher);
        return new ResponseJson(isDis);
    }

    @PostMapping("/inTimeCountByRules")
    @ApiOperation(value = "即时统计", notes = "返回响应对象,不包含总条数", response = KqOriginalData.class)
    public ResponseJson inTimeCountByRules(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = xwStudentKqService.inTimeCountByRules(kqOriginalData);
        return new ResponseJson(data);
    }
    @PostMapping("/findKqDailyStatisticalsByCondition")
    @ApiOperation(value = "根据条件查找考勤日统计表", notes = "返回响应对象", response=KqDailyStatistical.class)
    public ResponseJson findKqDailyStatisticalsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatistical kqDailyStatistical){
        kqDailyStatistical.getPager().setSortOrder(Pager.DESC);
        kqDailyStatistical.getPager().setSortField("kqDate");
        kqDailyStatistical.setSchoolId(mySchoolId());
        List<KqDailyStatistical> data=xwStudentKqService.findKqDailyStatisticalListByCondition(kqDailyStatistical);
        long count=xwStudentKqService.findKqDailyStatisticalCountByCondition(kqDailyStatistical);
        return new ResponseJson(data,count);
    }

}
