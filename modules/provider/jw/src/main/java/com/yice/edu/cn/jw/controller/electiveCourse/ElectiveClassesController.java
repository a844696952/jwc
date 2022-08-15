package com.yice.edu.cn.jw.controller.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electiveClasses")
@Api(value = "/electiveClasses",description = "选修班级关联表模块")
public class ElectiveClassesController {
    @Autowired
    private ElectiveClassesService electiveClassesService;

    @GetMapping("/findElectiveClassesById/{id}")
    @ApiOperation(value = "通过id查找选修班级关联表", notes = "返回选修班级关联表对象")
    public ElectiveClasses findElectiveClassesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return electiveClassesService.findElectiveClassesById(id);
    }

    @PostMapping("/saveElectiveClasses")
    @ApiOperation(value = "保存选修班级关联表", notes = "返回选修班级关联表对象")
    public ElectiveClasses saveElectiveClasses(
            @ApiParam(value = "选修班级关联表对象", required = true)
            @RequestBody ElectiveClasses electiveClasses){
        electiveClassesService.saveElectiveClasses(electiveClasses);
        return electiveClasses;
    }

    @PostMapping("/findElectiveClassesListByCondition")
    @ApiOperation(value = "根据条件查找选修班级关联表列表", notes = "返回选修班级关联表列表")
    public List<ElectiveClasses> findElectiveClassesListByCondition(
            @ApiParam(value = "选修班级关联表对象")
            @RequestBody ElectiveClasses electiveClasses){
        return electiveClassesService.findElectiveClassesListByCondition(electiveClasses);
    }
    @PostMapping("/findElectiveClassesCountByCondition")
    @ApiOperation(value = "根据条件查找选修班级关联表列表个数", notes = "返回选修班级关联表总个数")
    public long findElectiveClassesCountByCondition(
            @ApiParam(value = "选修班级关联表对象")
            @RequestBody ElectiveClasses electiveClasses){
        return electiveClassesService.findElectiveClassesCountByCondition(electiveClasses);
    }

    @PostMapping("/updateElectiveClasses")
    @ApiOperation(value = "修改选修班级关联表", notes = "选修班级关联表对象必传")
    public void updateElectiveClasses(
            @ApiParam(value = "选修班级关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveClasses electiveClasses){
        electiveClassesService.updateElectiveClasses(electiveClasses);
    }

    @GetMapping("/deleteElectiveClasses/{id}")
    @ApiOperation(value = "通过id删除选修班级关联表")
    public void deleteElectiveClasses(
            @ApiParam(value = "选修班级关联表对象", required = true)
            @PathVariable String id){
        electiveClassesService.deleteElectiveClasses(id);
    }
    @PostMapping("/deleteElectiveClassesByCondition")
    @ApiOperation(value = "根据条件删除选修班级关联表")
    public void deleteElectiveClassesByCondition(
            @ApiParam(value = "选修班级关联表对象")
            @RequestBody ElectiveClasses electiveClasses){
        electiveClassesService.deleteElectiveClassesByCondition(electiveClasses);
    }
    @PostMapping("/findOneElectiveClassesByCondition")
    @ApiOperation(value = "根据条件查找单个选修班级关联表,结果必须为单条数据", notes = "返回单个选修班级关联表,没有时为空")
    public ElectiveClasses findOneElectiveClassesByCondition(
            @ApiParam(value = "选修班级关联表对象")
            @RequestBody ElectiveClasses electiveClasses){
        return electiveClassesService.findOneElectiveClassesByCondition(electiveClasses);
    }
}
