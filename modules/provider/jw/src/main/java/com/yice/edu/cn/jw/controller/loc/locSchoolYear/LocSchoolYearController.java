package com.yice.edu.cn.jw.controller.loc.locSchoolYear;

import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import com.yice.edu.cn.jw.service.loc.locSchoolYear.LocSchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locSchoolYear")
@Api(value = "/locSchoolYear",description = "学年模块")
public class LocSchoolYearController {
    @Autowired
    private LocSchoolYearService locSchoolYearService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocSchoolYearById/{id}")
    @ApiOperation(value = "通过id查找学年", notes = "返回学年对象")
    public LocSchoolYear findLocSchoolYearById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return locSchoolYearService.findLocSchoolYearById(id);
    }

    @PostMapping("/saveLocSchoolYear")
    @ApiOperation(value = "保存学年", notes = "返回学年对象")
    public LocSchoolYear saveLocSchoolYear(
            @ApiParam(value = "学年对象", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.saveLocSchoolYear(locSchoolYear);
        return locSchoolYear;
    }

    @PostMapping("/batchSaveLocSchoolYear")
    @ApiOperation(value = "批量保存学年")
    public void batchSaveLocSchoolYear(
            @ApiParam(value = "学年对象集合", required = true)
            @RequestBody List<LocSchoolYear> locSchoolYears){
        locSchoolYearService.batchSaveLocSchoolYear(locSchoolYears);
    }

    @PostMapping("/findLocSchoolYearListByCondition")
    @ApiOperation(value = "根据条件查找学年列表", notes = "返回学年列表")
    public List<LocSchoolYear> findLocSchoolYearListByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody LocSchoolYear locSchoolYear){
        return locSchoolYearService.findLocSchoolYearListByCondition(locSchoolYear);
    }
    @PostMapping("/findLocSchoolYearCountByCondition")
    @ApiOperation(value = "根据条件查找学年列表个数", notes = "返回学年总个数")
    public long findLocSchoolYearCountByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody LocSchoolYear locSchoolYear){
        return locSchoolYearService.findLocSchoolYearCountByCondition(locSchoolYear);
    }

    @PostMapping("/updateLocSchoolYear")
    @ApiOperation(value = "修改学年有值的字段", notes = "学年对象必传")
    public void updateLocSchoolYear(
            @ApiParam(value = "学年对象,对象属性不为空则修改", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.updateLocSchoolYear(locSchoolYear);
    }
    @PostMapping("/updateLocSchoolYearForAll")
    @ApiOperation(value = "修改学年所有字段", notes = "学年对象必传")
    public void updateLocSchoolYearForAll(
            @ApiParam(value = "学年对象", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.updateLocSchoolYearForAll(locSchoolYear);
    }

    @GetMapping("/deleteLocSchoolYear/{id}")
    @ApiOperation(value = "通过id删除学年")
    public void deleteLocSchoolYear(
            @ApiParam(value = "学年对象", required = true)
            @PathVariable String id){
        locSchoolYearService.deleteLocSchoolYear(id);
    }
    @PostMapping("/deleteLocSchoolYearByCondition")
    @ApiOperation(value = "根据条件删除学年")
    public void deleteLocSchoolYearByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.deleteLocSchoolYearByCondition(locSchoolYear);
    }
    @PostMapping("/findOneLocSchoolYearByCondition")
    @ApiOperation(value = "根据条件查找单个学年,结果必须为单条数据", notes = "返回单个学年,没有时为空")
    public LocSchoolYear findOneLocSchoolYearByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody LocSchoolYear locSchoolYear){
        return locSchoolYearService.findOneLocSchoolYearByCondition(locSchoolYear);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
