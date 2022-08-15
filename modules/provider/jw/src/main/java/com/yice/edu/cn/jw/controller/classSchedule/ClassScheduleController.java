package com.yice.edu.cn.jw.controller.classSchedule;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ImportClassSchedule;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.jw.service.classSchedule.ClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule", description = "模块")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @GetMapping("/findClassScheduleById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ClassSchedule findClassScheduleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return classScheduleService.findClassScheduleById(id);
    }

    @PostMapping("/saveClassSchedule")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ClassSchedule saveClassSchedule(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassSchedule classSchedule) {
        classScheduleService.saveClassSchedule(classSchedule);
        return classSchedule;
    }

    @PostMapping("/findClassScheduleListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassSchedule> findClassScheduleListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule classSchedule) {
        return classScheduleService.findClassScheduleListByCondition(classSchedule);
    }

    @PostMapping("/findClassScheduleCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClassScheduleCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule classSchedule) {
        return classScheduleService.findClassScheduleCountByCondition(classSchedule);
    }

    @PostMapping("/updateClassSchedule")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateClassSchedule(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClassSchedule classSchedule) {
        classScheduleService.updateClassSchedule(classSchedule);
    }

    @GetMapping("/deleteClassSchedule/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteClassSchedule(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        classScheduleService.deleteClassSchedule(id);
    }

    @PostMapping("/deleteClassScheduleByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteClassScheduleByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule classSchedule) {
        classScheduleService.deleteClassScheduleByCondition(classSchedule);
    }

    @PostMapping("/findOneClassScheduleByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ClassSchedule findOneClassScheduleByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule classSchedule) {
        return classScheduleService.findOneClassScheduleByCondition(classSchedule);
    }

    @GetMapping("/findGradesBySchoolId/{id}/{scheduleId}")
    @ApiOperation(value = "根据学校id获取学校的班级")
    public List<Dd> findGradesBySchoolId(@PathVariable String id,@PathVariable("scheduleId")String scheduleId) {
        List<Dd> dds = classScheduleService.findGradesBySchoolId(id,scheduleId);
        return dds;
    }

    @PostMapping("/getTeacherNameAndCourseAndCount")
    @ApiOperation(value = "传值", notes = "返回对应的课时信息")
    public List<ClassSchedule> getTeacherNameAndCourseAndCount(@RequestBody ClassSchedule classSchedule) {
        return classScheduleService.getTeacherNameAndCourseAndCount(classSchedule);
    }

    @PostMapping("/updateorfind")
    @ApiOperation(value = "需要CRUD的数据", notes = "添加或者修改数据库的信息")
    public void updateOrFind(
            @ApiParam(value = "需要CRUD的数据", required = true)
            @RequestBody ClassSchedule classSchedule
            ) {
        classScheduleService.updateOrFind(classSchedule);
    }

    @PostMapping("/deletebatchdelete")
    @ApiOperation(value = "删除某个班的课程表", notes = "无返回")
    public void batchdelete(@RequestBody ClassSchedule classSchedule
    ) {
        classScheduleService.deletebatchdelete(classSchedule);
    }


    @PostMapping("/findList")
    @ApiOperation(value = "获取对应的课程表信息", notes = "返回对应的课程信息")
    public List<List<ClassSchedule>> findList(@RequestBody ClassSchedule classSchedule) {
        return classScheduleService.findList(classSchedule);
    }

    @PostMapping("/findClassScheduleListByConditions")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassSchedule> findClassScheduleListByConditions(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule classSchedule) {
        return classScheduleService.findClassScheduleListByConditions(classSchedule);
    }

    @PostMapping("/todayClassScheduleList")
    @ApiOperation(value = "根据条件查询今日列表",notes = "返回列表")
    public List<ClassSchedule> todayClassScheduleList(
            @ApiParam(value = "对象")
            @RequestBody ClassSchedule  classSchedule
    ){
      List<ClassSchedule> classSchedules =  classScheduleService.todayClassScheduleList(classSchedule);
      ClassScheduleService.romoveClassSchedule(classSchedules,false);
      return classSchedules;
    }


    @PostMapping("/todayAndTomorrowClassSchede")
    @ApiOperation(value = "根据条件查询今天和明天课程",notes = "返回列表")
    public ResponseJson  todayAndTomorrowClassSchede(
            @ApiParam(value = "返回今明两个课程")
            @RequestBody ClassSchedule classSchedule
    ){
        return classScheduleService.todayAndTomorrowClassSchede(classSchedule);

    }


    @PostMapping("/batchSaveClassSchedule")
    @ApiOperation(value = "批量添加课程表",notes = "返回执行后结果")
    public void batchSaveClassSchedule(
            @ApiParam(value = "返回执行后结果")
            @RequestBody List<ClassSchedule> classSchedules
            ){
        classScheduleService.batchSaveClassSchedule(classSchedules);
    }


    @GetMapping("/verifyImport/{classesId}")
    @ApiOperation(value = "验证单元格数据是否正确",notes = "返回数据")
    public List<ClassSchedule> verifyImport(
           @ApiParam(value = "班级Id",required = true)
           @PathVariable String classesId
    ){
        return classScheduleService.verifyImport(classesId);
    }

    @PostMapping("/conversionSchedule")
    public Map<String,Object> conversionSchedule(
            @ApiParam(value = "导入文件参数校正")
            @RequestBody ImportClassSchedule importClassSchedule
    ){
        return classScheduleService.conversionSchedule(importClassSchedule);
    }

    @PostMapping("/getTeacherThisWeekClassSchedule")
    @ApiOperation(value = "获取对应的课表")
    public List<List<ClassSchedule>> getTeacherThisWeekClassSchedule(
            @RequestBody ClassSchedule classSchedule
    ){
        List<List<ClassSchedule>> classScheduleList = classScheduleService.findList(classSchedule);
        classScheduleList = classScheduleService.hangzhuanlie(classScheduleList,classSchedule.getSchoolId());
        return classScheduleService.removeNullClassSchedule(classScheduleList,true);
    }

    @PostMapping("/getTeacherList")
    @ApiOperation(value = "通过课程获取对应的教师，班会课另做处理")
    public ResponseJson getTeacherList(
            @RequestBody ClassSchedule classSchedule
    ){
       return classScheduleService.getTeacherList(classSchedule);
    }

    @PostMapping("/findCourseTeacherByClass")
    public List<Map<String,String>> findCourseTeacherByClass(
            @RequestBody TeacherClasses teacherClasses){
        return classScheduleService.findCourseTeacherByClass(teacherClasses);
    }

    @GetMapping("/findTodayClassScheduleListByUserName/{userName}")
    @ApiOperation(value = "根据班牌用户名查询今日课表",notes = "返回学生今日课表")
    public List<ClassSchedule> findTodayClassScheduleListByEname(
            @ApiParam(value = "班牌用户名", required = true)
            @PathVariable("userName") String userName
    ){
        String classId = classScheduleService.findClassIdByUserName(userName);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        Date date = new Date();
        int s = date.getDay();
        if(s==0){
            s=7;
        }
        classSchedule.setWeekId(s);
        List<ClassSchedule> classSchedules =  classScheduleService.todayClassScheduleList(classSchedule);
        if(CollectionUtil.isNotEmpty(classSchedules)){
            classSchedules.forEach(m->m.setTimestamp(System.currentTimeMillis()));
        }
        ClassScheduleService.romoveClassSchedule(classSchedules,false);
        return classSchedules;
    }

    @PostMapping("/deleteSchoolScheduleInClassId")
    public void deleteSchoolScheduleInClassId(@RequestBody List<String> classId){
        classScheduleService.deleteSchoolScheduleInClassId(classId);
    }

    @PostMapping("/findClassScheduleGroupClassId")
    public List<ClassSchedule> findClassScheduleGroupClassId(@RequestBody ClassSchedule classSchedule){
        return classScheduleService.findClassScheduleGroupClassId(classSchedule);
    }

    @PostMapping("/conversionScheduleNew")
    public Map<String,Object> conversionScheduleNew(@RequestBody ImportClassSchedule importClassSchedule){
        return classScheduleService.conversionScheduleNew(importClassSchedule);
    }
}
