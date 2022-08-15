package com.yice.edu.cn.jw.controller.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import com.yice.edu.cn.jw.service.appIndex.SchoolAppIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolAppIndex")
@Api(value = "/schoolAppIndex",description = "学校移动端首页模块")
public class SchoolAppIndexController {
    @Autowired
    private SchoolAppIndexService schoolAppIndexService;

    @GetMapping("/findSchoolAppIndexById/{id}")
    @ApiOperation(value = "通过id查找学校移动端首页", notes = "返回学校移动端首页对象")
    public SchoolAppIndex findSchoolAppIndexById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolAppIndexService.findSchoolAppIndexById(id);
    }

    @PostMapping("/saveSchoolAppIndex")
    @ApiOperation(value = "保存学校移动端首页", notes = "返回学校移动端首页对象")
    public SchoolAppIndex saveSchoolAppIndex(
            @ApiParam(value = "学校移动端首页对象", required = true)
            @RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.saveSchoolAppIndex(schoolAppIndex);
        return schoolAppIndex;
    }

    @PostMapping("/findSchoolAppIndexListByCondition")
    @ApiOperation(value = "根据条件查找学校移动端首页列表", notes = "返回学校移动端首页列表")
    public List<SchoolAppIndex> findSchoolAppIndexListByCondition(
            @ApiParam(value = "学校移动端首页对象")
            @RequestBody SchoolAppIndex schoolAppIndex){
        return schoolAppIndexService.findSchoolAppIndexListByCondition(schoolAppIndex);
    }
    @PostMapping("/findSchoolAppIndexCountByCondition")
    @ApiOperation(value = "根据条件查找学校移动端首页列表个数", notes = "返回学校移动端首页总个数")
    public long findSchoolAppIndexCountByCondition(
            @ApiParam(value = "学校移动端首页对象")
            @RequestBody SchoolAppIndex schoolAppIndex){
        return schoolAppIndexService.findSchoolAppIndexCountByCondition(schoolAppIndex);
    }

    @PostMapping("/updateSchoolAppIndex")
    @ApiOperation(value = "修改学校移动端首页", notes = "学校移动端首页对象必传")
    public void updateSchoolAppIndex(
            @ApiParam(value = "学校移动端首页对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.updateSchoolAppIndex(schoolAppIndex);
    }

    @GetMapping("/deleteSchoolAppIndex/{id}")
    @ApiOperation(value = "通过id删除学校移动端首页")
    public void deleteSchoolAppIndex(
            @ApiParam(value = "学校移动端首页对象", required = true)
            @PathVariable String id){
        schoolAppIndexService.deleteSchoolAppIndex(id);
    }
    @PostMapping("/deleteSchoolAppIndexByCondition")
    @ApiOperation(value = "根据条件删除学校移动端首页")
    public void deleteSchoolAppIndexByCondition(
            @ApiParam(value = "学校移动端首页对象")
            @RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.deleteSchoolAppIndexByCondition(schoolAppIndex);
    }
    @PostMapping("/findOneSchoolAppIndexByCondition")
    @ApiOperation(value = "根据条件查找单个学校移动端首页,结果必须为单条数据", notes = "返回单个学校移动端首页,没有时为空")
    public SchoolAppIndex findOneSchoolAppIndexByCondition(
            @ApiParam(value = "学校移动端首页对象")
            @RequestBody SchoolAppIndex schoolAppIndex){
        return schoolAppIndexService.findOneSchoolAppIndexByCondition(schoolAppIndex);
    }

    @PostMapping("/moveAppIndexes")
    public void moveAppIndexes(@RequestBody List<SchoolAppIndex> schoolAppIndices){
        schoolAppIndexService.moveAppIndexes(schoolAppIndices);
    }

    @PostMapping("/upsertSchoolAppIndex")
    public void upsertSchoolAppIndex(@RequestBody SchoolAppIndex schoolAppIndex){
        schoolAppIndexService.upsertSchoolAppIndex(schoolAppIndex);
    }
}
