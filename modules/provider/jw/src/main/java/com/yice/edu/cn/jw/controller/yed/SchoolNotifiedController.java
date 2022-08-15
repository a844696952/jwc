package com.yice.edu.cn.jw.controller.yed;

import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.jw.service.yed.SchoolNotifiedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolNotified")
@Api(value = "/schoolNotified",description = "模块")
public class SchoolNotifiedController {
    @Autowired
    private SchoolNotifiedService schoolNotifiedService;

    @GetMapping("/findSchoolNotifiedById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolNotified findSchoolNotifiedById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolNotifiedService.findSchoolNotifiedById(id);
    }

    @PostMapping("/saveSchoolNotified")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolNotified saveSchoolNotified(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotified schoolNotified){
        schoolNotifiedService.saveSchoolNotified(schoolNotified);
        return schoolNotified;
    }

    @PostMapping("/findSchoolNotifiedListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolNotified> findSchoolNotifiedListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotified schoolNotified){
        return schoolNotifiedService.findSchoolNotifiedListByCondition(schoolNotified);
    }
    @PostMapping("/findSchoolNotifiedCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolNotifiedCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotified schoolNotified){
        return schoolNotifiedService.findSchoolNotifiedCountByCondition(schoolNotified);
    }

    @PostMapping("/updateSchoolNotified")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolNotified(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotified schoolNotified){
        schoolNotifiedService.updateSchoolNotified(schoolNotified);
    }

    @GetMapping("/deleteSchoolNotified/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolNotified(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolNotifiedService.deleteSchoolNotified(id);
    }
    @PostMapping("/deleteSchoolNotifiedByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolNotifiedByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotified schoolNotified){
        schoolNotifiedService.deleteSchoolNotifiedByCondition(schoolNotified);
    }
    @PostMapping("/findOneSchoolNotifiedByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolNotified findOneSchoolNotifiedByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotified schoolNotified){
        return schoolNotifiedService.findOneSchoolNotifiedByCondition(schoolNotified);
    }
}
