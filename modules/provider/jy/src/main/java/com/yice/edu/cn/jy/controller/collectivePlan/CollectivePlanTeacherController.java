package com.yice.edu.cn.jy.controller.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.jy.service.collectivePlan.CollectivePlanTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivePlanTeacher")
@Api(value = "/collectivePlanTeacher",description = "模块")
public class CollectivePlanTeacherController {
    @Autowired
    private CollectivePlanTeacherService collectivePlanTeacherService;

    @GetMapping("/findCollectivePlanTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public CollectivePlanTeacher findCollectivePlanTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return collectivePlanTeacherService.findCollectivePlanTeacherById(id);
    }

    @PostMapping("/saveCollectivePlanTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public CollectivePlanTeacher saveCollectivePlanTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        collectivePlanTeacherService.saveCollectivePlanTeacher(collectivePlanTeacher);
        return collectivePlanTeacher;
    }

    @PostMapping("/findCollectivePlanTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<CollectivePlanTeacher> findCollectivePlanTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        return collectivePlanTeacherService.findCollectivePlanTeacherListByCondition(collectivePlanTeacher);
    }
    @PostMapping("/findCollectivePlanTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findCollectivePlanTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        return collectivePlanTeacherService.findCollectivePlanTeacherCountByCondition(collectivePlanTeacher);
    }

    @PostMapping("/updateCollectivePlanTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateCollectivePlanTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        collectivePlanTeacherService.updateCollectivePlanTeacher(collectivePlanTeacher);
    }

    @GetMapping("/deleteCollectivePlanTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteCollectivePlanTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        collectivePlanTeacherService.deleteCollectivePlanTeacher(id);
    }
    @PostMapping("/deleteCollectivePlanTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteCollectivePlanTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        collectivePlanTeacherService.deleteCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }
    @PostMapping("/findOneCollectivePlanTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public CollectivePlanTeacher findOneCollectivePlanTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        return collectivePlanTeacherService.findOneCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }

    @PostMapping("/findOneCollectivePlanByCollectivePlanId")
    @ApiOperation(value = "查询当前讨论组中的老师列表", notes = "返回列表")
    public List<CollectivePlanTeacher> findOneCollectivePlanByCollectivePlanId(
            @ApiParam(value = "对象")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        return collectivePlanTeacherService.findOneCollectivePlanByCollectivePlanId(collectivePlanTeacher);
    }
}
