package com.yice.edu.cn.jw.controller.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.jw.service.classSchedule.ClassScheduleInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classScheduleInit")
@Api(value = "/classScheduleInit",description = "模块")
public class ClassScheduleInitController {
    @Autowired
    private ClassScheduleInitService classScheduleInitService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClassScheduleInitById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ClassScheduleInit findClassScheduleInitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classScheduleInitService.findClassScheduleInitById(id);
    }

    @PostMapping("/saveClassScheduleInit")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ClassScheduleInit saveClassScheduleInit(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.saveClassScheduleInit(classScheduleInit);
        return classScheduleInit;
    }

    @PostMapping("/findClassScheduleInitListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassScheduleInit> findClassScheduleInitListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassScheduleInit classScheduleInit){
        return classScheduleInitService.findClassScheduleInitListByCondition(classScheduleInit);
    }
    @PostMapping("/findClassScheduleInitCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClassScheduleInitCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassScheduleInit classScheduleInit){
        return classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
    }

    @PostMapping("/updateClassScheduleInit")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateClassScheduleInit(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.updateClassScheduleInit(classScheduleInit);
    }
    @PostMapping("/updateClassScheduleInitForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateClassScheduleInitForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.updateClassScheduleInitForAll(classScheduleInit);
    }

    @GetMapping("/deleteClassScheduleInit/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteClassScheduleInit(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        classScheduleInitService.deleteClassScheduleInit(id);
    }
    @PostMapping("/deleteClassScheduleInitByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteClassScheduleInitByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassScheduleInit classScheduleInit){
        classScheduleInitService.deleteClassScheduleInitByCondition(classScheduleInit);
    }
    @PostMapping("/findOneClassScheduleInitByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ClassScheduleInit findOneClassScheduleInitByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassScheduleInit classScheduleInit){
        return classScheduleInitService.findOneClassScheduleInitByCondition(classScheduleInit);
    }

    @PostMapping("/batchSaveClassScheduleInit")
    @ApiOperation(value = "批量保存学校初始化时间段")
    public void batchSaveClassScheduleInit(
            @ApiParam(value = "学校初始化时间段对象集合", required = true)
            @RequestBody List<ClassScheduleInit> classScheduleInits){
        classScheduleInitService.batchSaveClassScheduleInit(classScheduleInits);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/batchSaveClassScheduleTime/{number}/{schoolId}")
    public void batchSaveClassScheduleTime(
            @RequestBody List<ClassScheduleInit> classScheduleInits,
            @PathVariable("number") Integer number,
            @PathVariable("schoolId") String schoolId){
        classScheduleInitService.batchSaveClassScheduleTime(classScheduleInits,number,schoolId);
    }

    @PostMapping("/findListClassScheduleInitBySchool")
    public List<ClassScheduleInit> findListClassScheduleInitBySchool(@RequestBody ClassScheduleInit classScheduleInit){
        return classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
    }
}
