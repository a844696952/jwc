package com.yice.edu.cn.jw.controller.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electiveSet")
@Api(value = "/electiveSet",description = "选修课设置模块")
public class ElectiveSetController {
    @Autowired
    private ElectiveSetService electiveSetService;

    @GetMapping("/findElectiveSetById/{id}")
    @ApiOperation(value = "通过id查找选修课设置", notes = "返回选修课设置对象")
    public ElectiveSet findElectiveSetById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return electiveSetService.findElectiveSetById(id);
    }

    @PostMapping("/saveElectiveSet")
    @ApiOperation(value = "保存选修课设置", notes = "返回选修课设置对象")
    public ElectiveSet saveElectiveSet(
            @ApiParam(value = "选修课设置对象", required = true)
            @RequestBody ElectiveSet electiveSet){
        electiveSetService.saveElectiveSet(electiveSet);
        return electiveSet;
    }

    @PostMapping("/findElectiveSetListByCondition")
    @ApiOperation(value = "根据条件查找选修课设置列表", notes = "返回选修课设置列表")
    public List<ElectiveSet> findElectiveSetListByCondition(
            @ApiParam(value = "选修课设置对象")
            @RequestBody ElectiveSet electiveSet){
        return electiveSetService.findElectiveSetListByCondition(electiveSet);
    }
    @PostMapping("/findElectiveSetCountByCondition")
    @ApiOperation(value = "根据条件查找选修课设置列表个数", notes = "返回选修课设置总个数")
    public long findElectiveSetCountByCondition(
            @ApiParam(value = "选修课设置对象")
            @RequestBody ElectiveSet electiveSet){
        return electiveSetService.findElectiveSetCountByCondition(electiveSet);
    }

    @PostMapping("/updateElectiveSet")
    @ApiOperation(value = "修改选修课设置", notes = "选修课设置对象必传")
    public void updateElectiveSet(
            @ApiParam(value = "选修课设置对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveSet electiveSet){
        electiveSetService.updateElectiveSet(electiveSet);
    }

    @GetMapping("/deleteElectiveSet/{id}")
    @ApiOperation(value = "通过id删除选修课设置")
    public void deleteElectiveSet(
            @ApiParam(value = "选修课设置对象", required = true)
            @PathVariable String id){
        electiveSetService.deleteElectiveSet(id);
    }
    @PostMapping("/deleteElectiveSetByCondition")
    @ApiOperation(value = "根据条件删除选修课设置")
    public void deleteElectiveSetByCondition(
            @ApiParam(value = "选修课设置对象")
            @RequestBody ElectiveSet electiveSet){
        electiveSetService.deleteElectiveSetByCondition(electiveSet);
    }
    @PostMapping("/findOneElectiveSetByCondition")
    @ApiOperation(value = "根据条件查找单个选修课设置,结果必须为单条数据", notes = "返回单个选修课设置,没有时为空")
    public ElectiveSet findOneElectiveSetByCondition(
            @ApiParam(value = "选修课设置对象")
            @RequestBody ElectiveSet electiveSet){
        return electiveSetService.findOneElectiveSetByCondition(electiveSet);
    }
}
