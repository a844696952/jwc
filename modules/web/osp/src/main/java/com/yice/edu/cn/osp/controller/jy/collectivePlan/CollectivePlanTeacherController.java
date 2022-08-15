package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.osp.service.jy.collectivePlan.CollectivePlanTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/collectivePlanTeacher")
@Api(value = "/collectivePlanTeacher",description = "讨论组与教师关联模块")
public class CollectivePlanTeacherController {
    @Autowired
    private CollectivePlanTeacherService collectivePlanTeacherService;

    @PostMapping("/saveCollectivePlanTeacher")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveCollectivePlanTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        CollectivePlanTeacher s=collectivePlanTeacherService.saveCollectivePlanTeacher(collectivePlanTeacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCollectivePlanTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findCollectivePlanTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectivePlanTeacher collectivePlanTeacher=collectivePlanTeacherService.findCollectivePlanTeacherById(id);
        return new ResponseJson(collectivePlanTeacher);
    }

    @PostMapping("/update/updateCollectivePlanTeacher")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateCollectivePlanTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        collectivePlanTeacherService.updateCollectivePlanTeacher(collectivePlanTeacher);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCollectivePlanTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookCollectivePlanTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectivePlanTeacher collectivePlanTeacher=collectivePlanTeacherService.findCollectivePlanTeacherById(id);
        return new ResponseJson(collectivePlanTeacher);
    }

    @PostMapping("/findCollectivePlanTeachersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findCollectivePlanTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        List<CollectivePlanTeacher> data=collectivePlanTeacherService.findCollectivePlanTeacherListByCondition(collectivePlanTeacher);
        long count=collectivePlanTeacherService.findCollectivePlanTeacherCountByCondition(collectivePlanTeacher);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCollectivePlanTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneCollectivePlanTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        CollectivePlanTeacher one=collectivePlanTeacherService.findOneCollectivePlanTeacherByCondition(collectivePlanTeacher);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCollectivePlanTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCollectivePlanTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        collectivePlanTeacherService.deleteCollectivePlanTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findCollectivePlanTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findCollectivePlanTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        List<CollectivePlanTeacher> data=collectivePlanTeacherService.findCollectivePlanTeacherListByCondition(collectivePlanTeacher);
        return new ResponseJson(data);
    }


    @PostMapping("/findOneCollectivePlanByCollectivePlanId")
    @ApiOperation(value = "查询当前讨论组中已选择的教师------------------------------", notes = "返回响应对象")
    public ResponseJson findOneCollectivePlanByCollectivePlanId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        List<CollectivePlanTeacher> data=collectivePlanTeacherService.findOneCollectivePlanByCollectivePlanId(collectivePlanTeacher);
        return new ResponseJson(data);
    }

    @PostMapping("/deleteCollectivePlanTeacherByCondition")
    @ApiOperation(value = "批量删除讨论组中的老师---------------------------------------", notes = "返回响应对象")
    public ResponseJson deleteCollectivePlanTeacherByCondition(
            @ApiParam(value = "被删除记录的讨论组id", required = true)
            @RequestBody CollectivePlanTeacher collectivePlanTeacher){
        //批量删除讨论组的老师id 加入中间表
        String [] teachers = collectivePlanTeacher.getTeacherIds().split(",");
        if(teachers.length>0){
            for (String teacher : teachers) {
                CollectivePlanTeacher ct = new CollectivePlanTeacher();
                ct.setCollectivePlanId(collectivePlanTeacher.getCollectivePlanId());
                ct.setTeacherId(teacher);
                collectivePlanTeacherService.deleteCollectivePlanTeacherByCondition(ct);
            }
        }
        return new ResponseJson();
    }
}
