package com.yice.edu.cn.jw.controller.loc;

import com.yice.edu.cn.common.pojo.loc.SchoolConfig;
import com.yice.edu.cn.jw.service.loc.SchoolConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolConfig")
@Api(value = "/schoolConfig",description = "模块")
public class SchoolConfigController {
    @Autowired
    private SchoolConfigService schoolConfigService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolConfigById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolConfig findSchoolConfigById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolConfigService.findSchoolConfigById(id);
    }

    @PostMapping("/saveSchoolConfig")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolConfig saveSchoolConfig(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.saveSchoolConfig(schoolConfig);
        return schoolConfig;
    }

    @PostMapping("/batchSaveSchoolConfig")
    @ApiOperation(value = "批量保存")
    public void batchSaveSchoolConfig(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<SchoolConfig> schoolConfigs){
        schoolConfigService.batchSaveSchoolConfig(schoolConfigs);
    }

    @PostMapping("/findSchoolConfigListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolConfig> findSchoolConfigListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolConfig schoolConfig){
        return schoolConfigService.findSchoolConfigListByCondition(schoolConfig);
    }
    @PostMapping("/findSchoolConfigCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolConfigCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolConfig schoolConfig){
        return schoolConfigService.findSchoolConfigCountByCondition(schoolConfig);
    }

    @PostMapping("/updateSchoolConfig")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateSchoolConfig(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.updateSchoolConfig(schoolConfig);
    }
    @PostMapping("/updateSchoolConfigForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateSchoolConfigForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.updateSchoolConfigForAll(schoolConfig);
    }

    @GetMapping("/deleteSchoolConfig/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolConfig(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolConfigService.deleteSchoolConfig(id);
    }
    @PostMapping("/deleteSchoolConfigByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolConfigByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.deleteSchoolConfigByCondition(schoolConfig);
    }
    @PostMapping("/findOneSchoolConfigByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolConfig findOneSchoolConfigByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolConfig schoolConfig){
        return schoolConfigService.findOneSchoolConfigByCondition(schoolConfig);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/saveSchoolConfigKong")
    public void  saveSchoolConfigKong(@RequestBody SchoolConfig schoolConfig){
        schoolConfigService.saveSchoolConfigKong(schoolConfig);
    }
}
