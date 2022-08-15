package com.yice.edu.cn.osp.controller.jw.appIndex;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import com.yice.edu.cn.osp.service.jw.appIndex.SchoolAppIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolAppIndex")
@Api(value = "/schoolAppIndex",description = "学校移动端首页模块")
public class SchoolAppIndexController {
    @Autowired
    private SchoolAppIndexService schoolAppIndexService;

    @PostMapping("/saveSchoolAppIndex")
    @ApiOperation(value = "保存学校移动端首页对象", notes = "返回保存好的学校移动端首页对象", response= SchoolAppIndex.class)
    public ResponseJson saveSchoolAppIndex(
            @ApiParam(value = "学校移动端首页对象", required = true)
            @RequestBody SchoolAppIndex schoolAppIndex){
       schoolAppIndex.setSchoolId(mySchoolId());
        SchoolAppIndex s=schoolAppIndexService.saveSchoolAppIndex(schoolAppIndex);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolAppIndexById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校移动端首页", notes = "返回响应对象", response=SchoolAppIndex.class)
    public ResponseJson findSchoolAppIndexById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolAppIndex schoolAppIndex=schoolAppIndexService.findSchoolAppIndexById(id);
        return new ResponseJson(schoolAppIndex);
    }

    @PostMapping("/update/updateSchoolAppIndex")
    @ApiOperation(value = "修改学校移动端首页对象", notes = "返回响应对象")
    public ResponseJson updateSchoolAppIndex(
            @ApiParam(value = "被修改的学校移动端首页对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.updateSchoolAppIndex(schoolAppIndex);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolAppIndexById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校移动端首页", notes = "返回响应对象", response=SchoolAppIndex.class)
    public ResponseJson lookSchoolAppIndexById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolAppIndex schoolAppIndex=schoolAppIndexService.findSchoolAppIndexById(id);
        return new ResponseJson(schoolAppIndex);
    }

    @PostMapping("/findSchoolAppIndexsByCondition")
    @ApiOperation(value = "根据条件查找学校移动端首页", notes = "返回响应对象", response=SchoolAppIndex.class)
    public ResponseJson findSchoolAppIndexsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolAppIndex schoolAppIndex){
       schoolAppIndex.setSchoolId(mySchoolId());
        List<SchoolAppIndex> data=schoolAppIndexService.findSchoolAppIndexListByCondition(schoolAppIndex);
        long count=schoolAppIndexService.findSchoolAppIndexCountByCondition(schoolAppIndex);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolAppIndexByCondition")
    @ApiOperation(value = "根据条件查找单个学校移动端首页,结果必须为单条数据", notes = "没有时返回空", response=SchoolAppIndex.class)
    public ResponseJson findOneSchoolAppIndexByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolAppIndex schoolAppIndex){
        SchoolAppIndex one=schoolAppIndexService.findOneSchoolAppIndexByCondition(schoolAppIndex);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolAppIndex/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolAppIndex(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolAppIndexService.deleteSchoolAppIndex(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolAppIndexListByCondition")
    @ApiOperation(value = "根据条件查找学校移动端首页列表", notes = "返回响应对象,不包含总条数", response=SchoolAppIndex.class)
    public ResponseJson findSchoolAppIndexListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolAppIndex schoolAppIndex){
       schoolAppIndex.setSchoolId(mySchoolId());
        List<SchoolAppIndex> data=schoolAppIndexService.findSchoolAppIndexListByCondition(schoolAppIndex);
        return new ResponseJson(data);
    }

    @PostMapping("/moveAppIndexes")
    @ApiOperation(value = "前端拖拽排序后，重新修改row字段")
    public ResponseJson moveAppIndexes(@RequestBody List<SchoolAppIndex> schoolAppIndices){
        schoolAppIndexService.moveAppIndexes(schoolAppIndices);
        return new ResponseJson();
    }

    @PostMapping("/upsertSchoolAppIndex")
    public ResponseJson upsertSchoolAppIndex(@RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.upsertSchoolAppIndex(schoolAppIndex);
        return new ResponseJson();
    }


}
