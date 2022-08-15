package com.yice.edu.cn.jw.controller.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.jw.service.appIndex.AppIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appIndex")
@Api(value = "/appIndex",description = "移动端首页模块")
public class AppIndexController {
    @Autowired
    private AppIndexService appIndexService;

    @GetMapping("/findAppIndexById/{id}")
    @ApiOperation(value = "通过id查找移动端首页", notes = "返回移动端首页对象")
    public AppIndex findAppIndexById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return appIndexService.findAppIndexById(id);
    }

    @PostMapping("/saveAppIndex")
    @ApiOperation(value = "保存移动端首页", notes = "返回移动端首页对象")
    public AppIndex saveAppIndex(
            @ApiParam(value = "移动端首页对象", required = true)
            @RequestBody AppIndex appIndex){
        appIndexService.saveAppIndex(appIndex);
        return appIndex;
    }

    @PostMapping("/findAppIndexListByCondition")
    @ApiOperation(value = "根据条件查找移动端首页列表", notes = "返回移动端首页列表")
    public List<AppIndex> findAppIndexListByCondition(
            @ApiParam(value = "移动端首页对象")
            @RequestBody AppIndex appIndex){
        return appIndexService.findAppIndexListByCondition(appIndex);
    }
    @PostMapping("/findAppIndexCountByCondition")
    @ApiOperation(value = "根据条件查找移动端首页列表个数", notes = "返回移动端首页总个数")
    public long findAppIndexCountByCondition(
            @ApiParam(value = "移动端首页对象")
            @RequestBody AppIndex appIndex){
        return appIndexService.findAppIndexCountByCondition(appIndex);
    }

    @PostMapping("/updateAppIndex")
    @ApiOperation(value = "修改移动端首页", notes = "移动端首页对象必传")
    public void updateAppIndex(
            @ApiParam(value = "移动端首页对象,对象属性不为空则修改", required = true)
            @RequestBody AppIndex appIndex){
        appIndexService.updateAppIndex(appIndex);
    }

    @GetMapping("/deleteAppIndex/{id}")
    @ApiOperation(value = "通过id删除移动端首页")
    public void deleteAppIndex(
            @ApiParam(value = "移动端首页对象", required = true)
            @PathVariable String id){
        appIndexService.deleteAppIndex(id);
    }
    @PostMapping("/deleteAppIndexByCondition")
    @ApiOperation(value = "根据条件删除移动端首页")
    public void deleteAppIndexByCondition(
            @ApiParam(value = "移动端首页对象")
            @RequestBody AppIndex appIndex){
        appIndexService.deleteAppIndexByCondition(appIndex);
    }
    @PostMapping("/findOneAppIndexByCondition")
    @ApiOperation(value = "根据条件查找单个移动端首页,结果必须为单条数据", notes = "返回单个移动端首页,没有时为空")
    public AppIndex findOneAppIndexByCondition(
            @ApiParam(value = "移动端首页对象")
            @RequestBody AppIndex appIndex){
        return appIndexService.findOneAppIndexByCondition(appIndex);
    }

    @GetMapping("/moveAppIndexItem/{fromIndex}/{toIndex}/{type}")
    public void moveAppIndexItem(@PathVariable("fromIndex")int fromIndex,@PathVariable("toIndex") int toIndex,@PathVariable("type") int type){
        appIndexService.moveAppIndexItem(fromIndex, toIndex, type);
    }

    @GetMapping("/findAppIndexListForSchool/{schoolId}")
    public List<AppIndex> findAppIndexListForSchool(@PathVariable String schoolId){
        return appIndexService.findAppIndexListForSchool(schoolId);
    }

    @GetMapping("/getAppIndexesForTeacher/{schoolId}/{id}")
    public List<AppIndex> getAppIndexesForTeacher(@PathVariable("schoolId") String schoolId,@PathVariable("id") String id){
        return appIndexService.getAppIndexesForTeacher(schoolId, id);
    }

    @GetMapping("/getAppIndexesForParents/{schoolId}/{id}")
    public List<AppIndex> getAppIndexesForParents(@PathVariable("schoolId") String schoolId,@PathVariable("id") String id){
        return appIndexService.getAppIndexesForParents(schoolId, id);
    }

    @GetMapping("/getCountLeaveSchool/{schoolId}/{id}")
    public Long getCountLeaveSchool(@PathVariable("schoolId") String schoolId,@PathVariable("id") String id){
        return appIndexService.getCountLeaveSchool(schoolId, id);
    }
}
