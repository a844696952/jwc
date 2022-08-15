package com.yice.edu.cn.tap.controller.classSchedule;


import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.classSchedule.ClassScheduleService;
import com.yice.edu.cn.tap.service.classSchedule.ScheduleListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule",description = "教师课程表模块")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;
    @Autowired
    private ScheduleListService scheduleListService;

    @GetMapping("/ignore/myClassSchedule")
    @ApiOperation(value = "无需传参直接调用", notes = "返回教师对应的课表")
    public ResponseJson findList(){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTeacherId(LoginInterceptor.myId());
        classSchedule.setSchoolId(mySchoolId());
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);

        return new ResponseJson(list);
    }

    @GetMapping("/todayClassScheduleList")
    @ApiOperation(value = "无需参数直接调用",notes = "返回今日课表")
    public  ResponseJson todayClassScheduleList(){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTeacherId(LoginInterceptor.myId());
        classSchedule.setSchoolId(mySchoolId());
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        Date date = new Date();
        int s = date.getDay();
        if(s==0){
            s=7;
        }
        classSchedule.setWeekId(s);
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
        return  new ResponseJson(classSchedules);

    }



    @GetMapping("/look/somedayClassScheduleList/{weekId}")
    @ApiOperation(value = "传递1-7",notes = "返回对应周几课表")
    public ResponseJson somedayClassScheduleList(
            @ApiParam(value = "周几",required = true)
            @PathVariable Integer weekId
    ){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTeacherId(LoginInterceptor.myId());
        classSchedule.setSchoolId(mySchoolId());
        classSchedule.setWeekId(weekId);
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        Pager pager = new Pager();
        pager.setIncludes("id,weekId,numberId");
        classSchedule.setPager(pager);
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
        return  new ResponseJson(classSchedules);
    }

}
