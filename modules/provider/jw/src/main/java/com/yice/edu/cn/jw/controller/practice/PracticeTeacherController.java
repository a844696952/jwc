package com.yice.edu.cn.jw.controller.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.jw.service.practice.PracticeTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/practiceTeacher")
@Api(value = "/practiceTeacher",description = "模块")
public class PracticeTeacherController {
    @Autowired
    private PracticeTeacherService practiceTeacherService;

    @GetMapping("/findPracticeTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PracticeTeacher findPracticeTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return practiceTeacherService.findPracticeTeacherById(id);
    }

    @PostMapping("/savePracticeTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PracticeTeacher savePracticeTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody PracticeTeacher practiceTeacher){
        practiceTeacherService.savePracticeTeacher(practiceTeacher);
        return practiceTeacher;
    }

    @PostMapping("/findPracticeTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PracticeTeacher> findPracticeTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeTeacher practiceTeacher){
        return practiceTeacherService.findPracticeTeacherListByCondition(practiceTeacher);
    }
    @PostMapping("/findPracticeTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPracticeTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeTeacher practiceTeacher){
        return practiceTeacherService.findPracticeTeacherCountByCondition(practiceTeacher);
    }

    @PostMapping("/updatePracticeTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePracticeTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PracticeTeacher practiceTeacher){
        practiceTeacherService.updatePracticeTeacher(practiceTeacher);
    }

    @GetMapping("/deletePracticeTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePracticeTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        practiceTeacherService.deletePracticeTeacher(id);
    }
    @PostMapping("/deletePracticeTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePracticeTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeTeacher practiceTeacher){
        practiceTeacherService.deletePracticeTeacherByCondition(practiceTeacher);
    }
    @PostMapping("/findOnePracticeTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PracticeTeacher findOnePracticeTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeTeacher practiceTeacher){
        return practiceTeacherService.findOnePracticeTeacherByCondition(practiceTeacher);
    }

    @GetMapping("/findPracticeTeacherListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public List<PracticeTeacher> findPracticeTeacherListById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id){
        return practiceTeacherService.findPracticeTeacherListById(id);
    }

    @GetMapping("/findPracticeTeacherNameById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public List<PracticeTeacher> findPracticeTeacherNameById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id){
        return practiceTeacherService.findPracticeTeacherNameById(id);
    }
}
