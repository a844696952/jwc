package com.yice.edu.cn.ecc.controller.classSchedule;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.ecc.interceptor.LoginInterceptor;
import com.yice.edu.cn.ecc.service.classSchedule.ClassScheduleInitService;
import com.yice.edu.cn.ecc.service.classSchedule.ClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule", description = "学校班级课表模块")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @PostMapping("/findList")
    @ApiOperation(value = "根据班级编号，获取班级的课表", notes = "返回响应对象")
    public ResponseJson findList(
            @ApiParam(value = "通过班级编号，classId即可查询当前班级的课表信息")
            @RequestBody  ClassSchedule classSchedule) {
        if(StringUtils.isBlank(classSchedule.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            try{
                List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
                return new ResponseJson(list);
            }catch (Exception e){
                return new ResponseJson(false,"数据异常");
            }
        }

    }
    @PostMapping("/todayAndTomorrowClassSchede")
    @ApiOperation(value = "根据班级编号，获取班级今天和明天的课表", notes = "返回响应对象，data 今天课程，data2明天课程")
    public ResponseJson todayAndTomorrowClassSchede(
            @ApiParam(value = "通过班级编号，classId即可查询当前班级今天和明天的课表信息")
            @RequestBody  ClassSchedule classSchedule) {
        if(StringUtils.isBlank(classSchedule.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            try{
                return classScheduleService.todayAndTomorrowClassSchede(classSchedule);
            }catch (Exception e){
                return new ResponseJson(false,"数据异常");
            }
        }

    }


    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    @GetMapping("/ignore/myClassSchedule/{classId}")
    @ApiOperation(value = "传递学生的班级Id查询", notes = "返回班级完整课表")
    public ResponseJson findList(@PathVariable String classId) {
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
        Integer maxNumber = -1;
        lab:for (int i = list.size() -1; i >= 0; i--) {
            List<ClassSchedule> classScheduleList = list.get(i);
            for (int j = 0; j < classScheduleList.size(); j++) {
                if(classScheduleList.get(j) != null){
                    maxNumber = classScheduleList.get(j).getNumberId();
                    break lab;
                }
                if(j == classScheduleList.size() -1){
                    list.remove(i);
                }
            }
        }
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        if(maxNumber > 0){
            Integer finalMaxNumber = maxNumber;
            for (int i = classScheduleInits.size()-1; i >= 0 ; i--) {
                ClassScheduleInit item = classScheduleInits.get(i);
                if(finalMaxNumber < Integer.valueOf(item.getNumber())){
                    classScheduleInits.remove(item);
                }
            }
        }else{
            classScheduleInits.clear();
        }
        return new ResponseJson(list,classScheduleInits);
    }

    @GetMapping("/todayClassScheduleList/{classId}")
    @ApiOperation(value = "传递学生的班级id查询",notes = "返回学生今日课表")
    public  ResponseJson todayClassScheduleList(@PathVariable String classId){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        Date date = new Date();
        int s = date.getDay();
        if(s==0){
            s=7;
        }
        classSchedule.setWeekId(s);
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
        if(CollectionUtil.isNotEmpty(classSchedules)){
            classSchedules.forEach(m->m.setTimestamp(System.currentTimeMillis()));
        }

        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return  new ResponseJson(classSchedules,classScheduleInits);
    }


    @GetMapping("/todayClassScheduleList/{classId}/{weekId}")
    @ApiOperation(value = "传递学生的班级id和周几(1-7,整型)查询",notes = "返回学生今日课表")
    public  ResponseJson todayClassScheduleList(@PathVariable String classId,@PathVariable Integer weekId){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        classSchedule.setWeekId(weekId);
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);

        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return  new ResponseJson(classSchedules,classScheduleInits);
    }

    @GetMapping("/findTodayClassScheduleListByUserName/{userName}")
    @ApiOperation(value = "根据班牌用户名查询今日课表",notes = "返回学生今日课表")
    public ResponseJson findTodayClassScheduleListByUserName(
            @ApiParam(value = "班牌用户名", required = true)
            @PathVariable String userName
    ){

        List<ClassSchedule> classSchedules = classScheduleService.findTodayClassScheduleListByUserName(userName);

        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return  new ResponseJson(classSchedules,classScheduleInits);
    }
}
