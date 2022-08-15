package com.yice.edu.cn.jw.controller.schoolYear;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolYear")
@Api(value = "/schoolYear",description = "学年模块")
public class SchoolYearController {
    @Autowired
    private SchoolYearService schoolYearService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolYearById/{id}")
    @ApiOperation(value = "通过id查找学年", notes = "返回学年对象")
    public SchoolYear findSchoolYearById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolYearService.findSchoolYearById(id);
    }

    @PostMapping("/saveSchoolYear")
    @ApiOperation(value = "保存学年", notes = "返回学年对象")
    public SchoolYear saveSchoolYear(
            @ApiParam(value = "学年对象", required = true)
            @RequestBody SchoolYear schoolYear){
        schoolYearService.saveSchoolYear(schoolYear);
        return schoolYear;
    }

    @PostMapping("/batchSaveSchoolYear")
    @ApiOperation(value = "批量保存学年")
    public void batchSaveSchoolYear(
            @ApiParam(value = "学年对象集合", required = true)
            @RequestBody List<SchoolYear> schoolYears){
        schoolYearService.batchSaveSchoolYear(schoolYears);
    }

    @GetMapping("/findMaxSchoolYear/{schoolId}")
    @ApiOperation(value = "查询当前学年的时间段")
    public String[] findMaxSchoolYear(@PathVariable("schoolId")String schoolId){
        return schoolYearService.findMaxSchoolYear(schoolId);
    }

    @GetMapping("/findCurrentSchoolRange/{schoolId}")
    @ApiOperation(value = "查询当前学年的时间段")
    public String [] findCurrentSchoolRange(@PathVariable("schoolId")String schoolId){
        return schoolYearService.findCurrentSchoolRange(schoolId);
    }


    @PostMapping("/findSchoolYearListByCondition")
    @ApiOperation(value = "根据条件查找学年列表", notes = "返回学年列表")
    public List<SchoolYear> findSchoolYearListByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody SchoolYear schoolYear){
        return schoolYearService.findSchoolYearListByCondition(schoolYear);
    }
    @PostMapping("/findSchoolYearCountByCondition")
    @ApiOperation(value = "根据条件查找学年列表个数", notes = "返回学年总个数")
    public long findSchoolYearCountByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody SchoolYear schoolYear){
        return schoolYearService.findSchoolYearCountByCondition(schoolYear);
    }

    @PostMapping("/updateSchoolYear")
    @ApiOperation(value = "修改学年有值的字段", notes = "学年对象必传")
    public void updateSchoolYear(
            @ApiParam(value = "学年对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolYear schoolYear){
        schoolYearService.updateSchoolYear(schoolYear);
    }
    @PostMapping("/updateSchoolYearForAll")
    @ApiOperation(value = "修改学年所有字段", notes = "学年对象必传")
    public void updateSchoolYearForAll(
            @ApiParam(value = "学年对象", required = true)
            @RequestBody SchoolYear schoolYear){
        schoolYearService.updateSchoolYearForAll(schoolYear);
    }

    @GetMapping("/deleteSchoolYear/{id}")
    @ApiOperation(value = "通过id删除学年")
    public void deleteSchoolYear(
            @ApiParam(value = "学年对象", required = true)
            @PathVariable String id){
        schoolYearService.deleteSchoolYear(id);
    }
    @PostMapping("/deleteSchoolYearByCondition")
    @ApiOperation(value = "根据条件删除学年")
    public void deleteSchoolYearByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody SchoolYear schoolYear){
        schoolYearService.deleteSchoolYearByCondition(schoolYear);
    }
    @PostMapping("/findOneSchoolYearByCondition")
    @ApiOperation(value = "根据条件查找单个学年,结果必须为单条数据", notes = "返回单个学年,没有时为空")
    public SchoolYear findOneSchoolYearByCondition(
            @ApiParam(value = "学年对象")
            @RequestBody SchoolYear schoolYear){
        return schoolYearService.findOneSchoolYearByCondition(schoolYear);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/findCurSchoolYear/{schoolId}")
    @ApiOperation(value = "根据学校id获取当前的学年学期", notes = "返回一个对象，两个字段，schoolYearId和term")
    public CurSchoolYear findCurSchoolYear(@PathVariable String schoolId){
        return schoolYearService.findCurSchoolYear(schoolId);
    }
    @GetMapping("/findSchoolYears/{schoolId}")
    @ApiOperation(value = "根据学校id获取该学校的学年列表", notes = "返回一个对象，两个字段，id和fromTo")
    public List<SchoolYear> findSchoolYears(@PathVariable String schoolId){
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setSchoolId(schoolId);
        schoolYear.setPager(new Pager().setSortField("fromTo").setSortOrder(Pager.DESC).setIncludes("id","fromTo"));
        return schoolYearService.findSchoolYearListByCondition(schoolYear);
    }
}
