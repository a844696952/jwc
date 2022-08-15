package com.yice.edu.cn.jw.controller.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.jw.service.classSchedule.ClassScheduleNoonBreakService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classScheduleNoonBreak")
@Api(value = "/classScheduleNoonBreak",description = "学校午休标志位置表模块")
public class ClassScheduleNoonBreakController {
    @Autowired
    private ClassScheduleNoonBreakService classScheduleNoonBreakService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClassScheduleNoonBreakById/{id}")
    @ApiOperation(value = "通过id查找学校午休标志位置表", notes = "返回学校午休标志位置表对象")
    public ClassScheduleNoonBreak findClassScheduleNoonBreakById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classScheduleNoonBreakService.findClassScheduleNoonBreakById(id);
    }

    @PostMapping("/saveClassScheduleNoonBreak")
    @ApiOperation(value = "保存学校午休标志位置表", notes = "返回学校午休标志位置表对象")
    public ClassScheduleNoonBreak saveClassScheduleNoonBreak(
            @ApiParam(value = "学校午休标志位置表对象", required = true)
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.saveClassScheduleNoonBreak(classScheduleNoonBreak);
        return classScheduleNoonBreak;
    }

    @PostMapping("/findClassScheduleNoonBreakListByCondition")
    @ApiOperation(value = "根据条件查找学校午休标志位置表列表", notes = "返回学校午休标志位置表列表")
    public List<ClassScheduleNoonBreak> findClassScheduleNoonBreakListByCondition(
            @ApiParam(value = "学校午休标志位置表对象")
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        return classScheduleNoonBreakService.findClassScheduleNoonBreakListByCondition(classScheduleNoonBreak);
    }
    @PostMapping("/findClassScheduleNoonBreakCountByCondition")
    @ApiOperation(value = "根据条件查找学校午休标志位置表列表个数", notes = "返回学校午休标志位置表总个数")
    public long findClassScheduleNoonBreakCountByCondition(
            @ApiParam(value = "学校午休标志位置表对象")
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        return classScheduleNoonBreakService.findClassScheduleNoonBreakCountByCondition(classScheduleNoonBreak);
    }

    @PostMapping("/updateClassScheduleNoonBreak")
    @ApiOperation(value = "修改学校午休标志位置表有值的字段", notes = "学校午休标志位置表对象必传")
    public void updateClassScheduleNoonBreak(
            @ApiParam(value = "学校午休标志位置表对象,对象属性不为空则修改", required = true)
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.updateClassScheduleNoonBreak(classScheduleNoonBreak);
    }
    @PostMapping("/updateClassScheduleNoonBreakForAll")
    @ApiOperation(value = "修改学校午休标志位置表所有字段", notes = "学校午休标志位置表对象必传")
    public void updateClassScheduleNoonBreakForAll(
            @ApiParam(value = "学校午休标志位置表对象", required = true)
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.updateClassScheduleNoonBreakForAll(classScheduleNoonBreak);
    }

    @GetMapping("/deleteClassScheduleNoonBreak/{id}")
    @ApiOperation(value = "通过id删除学校午休标志位置表")
    public void deleteClassScheduleNoonBreak(
            @ApiParam(value = "学校午休标志位置表对象", required = true)
            @PathVariable String id){
        classScheduleNoonBreakService.deleteClassScheduleNoonBreak(id);
    }
    @PostMapping("/deleteClassScheduleNoonBreakByCondition")
    @ApiOperation(value = "根据条件删除学校午休标志位置表")
    public void deleteClassScheduleNoonBreakByCondition(
            @ApiParam(value = "学校午休标志位置表对象")
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakService.deleteClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }
    @PostMapping("/findOneClassScheduleNoonBreakByCondition")
    @ApiOperation(value = "根据条件查找单个学校午休标志位置表,结果必须为单条数据", notes = "返回单个学校午休标志位置表,没有时为空")
    public ClassScheduleNoonBreak findOneClassScheduleNoonBreakByCondition(
            @ApiParam(value = "学校午休标志位置表对象")
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak){
        return classScheduleNoonBreakService.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/updateClassScheduleNoobBreakNumber")
    public void updateClassScheduleNoobBreakNumber(
            @RequestBody ClassScheduleNoonBreak classScheduleNoonBreak
    ){
        classScheduleNoonBreakService.updateClassScheduleNoobBreakNumber(classScheduleNoonBreak);
    }
}
