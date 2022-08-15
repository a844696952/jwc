package com.yice.edu.cn.jw.controller.schoolDateCenter;

import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.jw.service.schoolDateCenter.SchoolDateCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolDateCenter")
@Api(value = "/schoolDateCenter",description = "模块")
public class SchoolDateCenterController {
    @Autowired
    private SchoolDateCenterService schoolDateCenterService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolDateCenterById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolDateCenter findSchoolDateCenterById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolDateCenterService.findSchoolDateCenterById(id);
    }

    @PostMapping("/saveSchoolDateCenter")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolDateCenter saveSchoolDateCenter(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.saveSchoolDateCenter(schoolDateCenter);
        return schoolDateCenter;
    }

    @PostMapping("/batchSaveSchoolDateCenter")
    @ApiOperation(value = "批量保存")
    public void batchSaveSchoolDateCenter(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<SchoolDateCenter> schoolDateCenters){
        schoolDateCenterService.batchSaveSchoolDateCenter(schoolDateCenters);
    }

    @PostMapping("/findSchoolDateCenterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolDateCenter> findSchoolDateCenterListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolDateCenter schoolDateCenter){
        return schoolDateCenterService.findSchoolDateCenterListByCondition(schoolDateCenter);
    }
    @PostMapping("/findSchoolDateCenterCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolDateCenterCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolDateCenter schoolDateCenter){
        return schoolDateCenterService.findSchoolDateCenterCountByCondition(schoolDateCenter);
    }

    @PostMapping("/updateSchoolDateCenter")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateSchoolDateCenter(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.updateSchoolDateCenter(schoolDateCenter);
    }
    @PostMapping("/updateSchoolDateCenterForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateSchoolDateCenterForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.updateSchoolDateCenterForAll(schoolDateCenter);
    }

    @GetMapping("/deleteSchoolDateCenter/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolDateCenter(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolDateCenterService.deleteSchoolDateCenter(id);
    }
    @PostMapping("/deleteSchoolDateCenterByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolDateCenterByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolDateCenter schoolDateCenter){
        schoolDateCenterService.deleteSchoolDateCenterByCondition(schoolDateCenter);
    }
    @PostMapping("/findOneSchoolDateCenterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolDateCenter findOneSchoolDateCenterByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolDateCenter schoolDateCenter){
        return schoolDateCenterService.findOneSchoolDateCenterByCondition(schoolDateCenter);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateSchoolDateCenterByType/{type}")
    public void updateSchoolDateCenterByType(@RequestBody SchoolDateCenter schoolDateCenter,@PathVariable("type") Integer type){
        schoolDateCenterService.updateSchoolDateCenterByType(schoolDateCenter,type);
    }

    @GetMapping("/findSchoolCompusCenterBySchoolId/{schoolId}")
    public SchoolDateCenter findSchoolCompusCenterBySchoolId(@PathVariable("schoolId")String schoolId){
       return  schoolDateCenterService.findSchoolCompusCenterBySchoolId(schoolId);
    }

    @GetMapping("/getTruesSchoolDateCenter/{schoolId}")
    public SchoolDateCenter getTruesSchoolDateCenter(@PathVariable("schoolId")String schoolId){
        return schoolDateCenterService.getTruesSchoolDateCenter(schoolId);
    }
}
