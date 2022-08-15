package com.yice.edu.cn.osp.controller.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleInitService;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleNoonBreakService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/classScheduleNoonBreak")
@Api(value = "/classScheduleNoonBreak",description = "学校午休标志位置表模块")
public class ClassScheduleNoonBreakController {
    @Autowired
    private ClassScheduleNoonBreakService classScheduleNoonBreakService;

    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    @PostMapping("/saveClassScheduleNoonBreak/{number}")
    @ApiOperation(value = "保存学校午休标志位置表对象", notes = "返回保存好的学校午休标志位置表对象", response=ClassScheduleNoonBreak.class)
    public ResponseJson saveClassScheduleNoonBreak(
            @ApiParam(value = "学校午休标志位置表对象", required = true)
            @RequestBody List<ClassScheduleInit> classScheduleInits,
            @PathVariable("number") Integer number){
        classScheduleInits.forEach(f->{
            f.setSchoolId(LoginInterceptor.mySchoolId());
            f.setType(1);
        });
        classScheduleInitService.batchSaveClassScheduleTime(classScheduleInits,number,LoginInterceptor.mySchoolId());
        /*classScheduleNoonBreak.setSchoolId(LoginInterceptor.mySchoolId());
        ClassScheduleNoonBreak s=classScheduleNoonBreakService.saveClassScheduleNoonBreak(classScheduleNoonBreak);*/
        return new ResponseJson();
    }

    @GetMapping("/update/findClassScheduleNoonBreakById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校午休标志位置表", notes = "返回响应对象", response=ClassScheduleNoonBreak.class)
    public ResponseJson findClassScheduleNoonBreakById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassScheduleNoonBreak classScheduleNoonBreak=classScheduleNoonBreakService.findClassScheduleNoonBreakById(id);
        return new ResponseJson(classScheduleNoonBreak);
    }

    @PostMapping("/update/updateClassScheduleNoonBreak")
    @ApiOperation(value = "修改学校午休标志位置表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateClassScheduleNoonBreak(
            @ApiParam(value = "被修改的学校午休标志位置表对象,对象属性不为空则修改", required = true)
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.updateClassScheduleNoonBreak(classScheduleNoonBreak);
        return new ResponseJson();
    }

    @PostMapping("/update/updateClassScheduleNoonBreakForAll")
    @ApiOperation(value = "修改学校午休标志位置表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateClassScheduleNoonBreakForAll(
            @ApiParam(value = "被修改的学校午休标志位置表对象", required = true)
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.updateClassScheduleNoonBreakForAll(classScheduleNoonBreak);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClassScheduleNoonBreakById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校午休标志位置表", notes = "返回响应对象", response=ClassScheduleNoonBreak.class)
    public ResponseJson lookClassScheduleNoonBreakById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassScheduleNoonBreak classScheduleNoonBreak=classScheduleNoonBreakService.findClassScheduleNoonBreakById(id);
        return new ResponseJson(classScheduleNoonBreak);
    }

    //查询课程节数列表
    @GetMapping("/findClassScheduleNoonBreaksByCondition")
    @ApiOperation(value = "查询课程节数年级列表", notes = "返回响应对象", response=ClassScheduleNoonBreak.class)
    public ResponseJson findClassScheduleNoonBreaksByCondition(){
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("number");
        pager.setPaging(false);
        pager.setSortOrder(Pager.ASC);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        ClassScheduleNoonBreak classScheduleNoonBreak = new ClassScheduleNoonBreak();
        classScheduleNoonBreak.setSchoolId(LoginInterceptor.mySchoolId());
        ClassScheduleNoonBreak one=classScheduleNoonBreakService.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
        if(one==null){
            one = new ClassScheduleNoonBreak();
            one.setNumber(Constant.CLASS_SCHEDULE.CLASS_NOON_BREAK);
        }
        return new ResponseJson(classScheduleInits,one);
    }

    //传递学校id
    @PostMapping("/findOneClassScheduleNoonBreakByCondition")
    @ApiOperation(value = "根据条件查找单个学校午休标志位置表,结果必须为单条数据", notes = "没有时返回空", response=ClassScheduleNoonBreak.class)
    public ResponseJson findOneClassScheduleNoonBreakByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        Pager pager = new Pager();
        pager.setIncludes("number");
        classScheduleNoonBreak.setPager(pager);
        ClassScheduleNoonBreak one=classScheduleNoonBreakService.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
        if(one==null){
            one = new ClassScheduleNoonBreak();
            one.setNumber(Constant.CLASS_SCHEDULE.CLASS_NOON_BREAK);
        }
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findClassScheduleInitListByCondition(classScheduleInit);
        return new ResponseJson(classScheduleInits,one);
    }
    @GetMapping("/deleteClassScheduleNoonBreak/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClassScheduleNoonBreak(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        classScheduleNoonBreakService.deleteClassScheduleNoonBreak(id);
        return new ResponseJson();
    }


    @PostMapping("/findClassScheduleNoonBreakListByCondition")
    @ApiOperation(value = "根据条件查找学校午休标志位置表列表", notes = "返回响应对象,不包含总条数", response=ClassScheduleNoonBreak.class)
    public ResponseJson findClassScheduleNoonBreakListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
       classScheduleNoonBreak.setSchoolId(LoginInterceptor.mySchoolId());
        List<ClassScheduleNoonBreak> data=classScheduleNoonBreakService.findClassScheduleNoonBreakListByCondition(classScheduleNoonBreak);
        return new ResponseJson(data);
    }


    @PostMapping("/update/updateSchoolScheduleTime/{number}")
    @ApiOperation(value = "修改对应节数的时间,和午休时间",notes = "无返回")
    public ResponseJson updateSchoolScheduleTime(
            @RequestBody List<ClassScheduleInit> classScheduleInit,
            @PathVariable("number") Integer number
    ){
        return new ResponseJson();
    }

    @PostMapping("/delete/ClassScheduleInitByCondition")
    public ResponseJson deleteClassScheduleInitByCondition(ClassScheduleInit classScheduleInit){
        classScheduleInitService.deleteClassScheduleInitByCondition(classScheduleInit);
        return new ResponseJson();
    }
}
