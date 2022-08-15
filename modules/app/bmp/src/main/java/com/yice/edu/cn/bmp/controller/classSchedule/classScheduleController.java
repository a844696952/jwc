package com.yice.edu.cn.bmp.controller.classSchedule;


import com.yice.edu.cn.bmp.interceptor.LoginInterceptor;
import com.yice.edu.cn.bmp.service.classSchedule.ClassScheduleInitService;
import com.yice.edu.cn.bmp.service.classSchedule.ClassScheduleService;
import com.yice.edu.cn.bmp.service.classSchedule.ScheduleListService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule",description = "学生课程表模块")
public class classScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @Autowired
    private RedisCacheManager  redisCacheManager;
    @Autowired
    private ScheduleListService scheduleListService;

    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    @GetMapping("/ignore/myClassSchedule/{classId}")
    @ApiOperation(value = "传递学生的班级Id查询", notes = "返回班级完整课表")
    public ResponseJson findList(@PathVariable String classId) {
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        classSchedule.setSchoolId(mySchoolId());
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return new ResponseJson(list,classScheduleInits);
    }

    @GetMapping("/todayClassScheduleList/{classId}")
    @ApiOperation(value = "传递学生的班级id查询",notes = "返回学生今日课表")
    public  ResponseJson todayClassScheduleList(@PathVariable String classId){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
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


    @GetMapping("/todayClassScheduleList/{classId}/{weekId}")
    @ApiOperation(value = "传递学生的班级id和周几(1-7,整型)查询",notes = "返回学生今日课表")
    public  ResponseJson todayClassScheduleList(@PathVariable String classId,@PathVariable Integer weekId){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        classSchedule.setWeekId(weekId);
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
        return  new ResponseJson(classSchedules);
    }

       /*@GetMapping("/getIdentifyingCode/{tel}")
    @ApiOperation(value = "传入手机号",notes = "返回发送结果")
    public ResponseJson getIdentifyingCode(
            @ApiParam(value = "传入手机号")
            @PathVariable String tel){
        String code = classScheduleService.getParentIdentifyingCode(tel);
        if(code!=null){
            return new ResponseJson(true,"验证码发送成功");
        }
        return new ResponseJson(false,"验证码发送失败");
    }*/


/*
    @GetMapping("/compareIdentifyingCode/{tel}/{code}")
    @ApiOperation(value = "传入手机号，验证码",notes = "返回验证码是否正确")
    public ResponseJson compareIdentifyingCode(
            @ApiParam(value = "传入手机号")
            @PathVariable String tel,@ApiParam(value = "传入验证码")
            @PathVariable String code
    ){
        Cache cache = redisCacheManager.getCache(Constant.Redis.BMP_VERIFICATION_CODE);

        if(cache==null){
            return  new ResponseJson(false,"验证码错误");
        }
        Cache.ValueWrapper valueWrapper = cache.get(tel);
        if(valueWrapper==null){
            return new ResponseJson(false,"验证码错误");
        }

        Object o = valueWrapper.get();
        if(o==null){
            return  new ResponseJson(false,"验证码错误");
        }

        String code1 = (String) o;
        if(code.equals(code1)){
            return new ResponseJson(true,"验证码正确");
        }else{
            return  new ResponseJson(false,"验证码错误");
        }


    }*/

}
