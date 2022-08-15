package com.yice.edu.cn.xw.controller.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.xw.service.stuInAndOut.ClassTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classTime")
@Api(value = "/classTime",description = "模块")
public class ClassTimeController {
    @Autowired
    private ClassTimeService classTimeService;

    @GetMapping("/findClassTimeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ClassTime findClassTimeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classTimeService.findClassTimeById(id);
    }

    @PostMapping("/saveClassTime")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ClassTime saveClassTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassTime classTime){
        classTimeService.saveClassTime(classTime);
        //创建动态定时任务
        classTimeService.createDynamicTask(classTime);
        return classTime;
    }

    @PostMapping("/findClassTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassTime> findClassTimeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassTime classTime){
        return classTimeService.findClassTimeListByCondition(classTime);
    }
    @PostMapping("/findClassTimeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClassTimeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassTime classTime){
        return classTimeService.findClassTimeCountByCondition(classTime);
    }

    @PostMapping("/updateClassTime")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateClassTime(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClassTime classTime){
        classTimeService.updateClassTime(classTime);
        classTimeService.createDynamicTask(classTime);
    }

    @GetMapping("/deleteClassTime/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteClassTime(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        classTimeService.deleteClassTime(id);
    }
    @PostMapping("/deleteClassTimeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteClassTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassTime classTime){
        classTimeService.deleteClassTimeByCondition(classTime);
    }
    @PostMapping("/findOneClassTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ClassTime findOneClassTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassTime classTime){
        return classTimeService.findOneClassTimeByCondition(classTime);
    }
    @PostMapping("/sendToSchoolTeacherStuNowStatus")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public void sendToSchoolTeacherStuNowStatus(@RequestBody String taskId){
        classTimeService.sendToSchoolTeacherStuNowStatus(taskId);
    }
    @PostMapping("/statisStudentNowStatusAfterSchool")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public void statisStudentNowStatusAfterSchool(@RequestBody String taskId){
        classTimeService.statisStudentNowStatusAfterSchool(taskId);
    }


}
