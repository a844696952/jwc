package com.yice.edu.cn.jw.controller.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.jw.service.schoolNotify.SchoolNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolNotify")
@Api(value = "/schoolNotify",description = "模块")
public class SchoolNotifyController {
    @Autowired
    private SchoolNotifyService schoolNotifyService;

    @GetMapping("/findSchoolNotifyById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolNotify findSchoolNotifyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolNotifyService.findSchoolNotifyById(id);
    }

    @PostMapping("/saveSchoolNotify")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolNotify saveSchoolNotify(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotify schoolNotify){
        schoolNotifyService.saveSchoolNotify(schoolNotify);
        return schoolNotify;
    }

    @PostMapping("/findSchoolNotifyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolNotify> findSchoolNotifyListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotify schoolNotify){
        return schoolNotifyService.findSchoolNotifyListByCondition(schoolNotify);
    }
    @PostMapping("/findSchoolNotifyCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolNotifyCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotify schoolNotify){
        return schoolNotifyService.findSchoolNotifyCountByCondition(schoolNotify);
    }

    @PostMapping("/updateSchoolNotify")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolNotify(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotify schoolNotify){
        schoolNotifyService.updateSchoolNotify(schoolNotify);
    }

    @GetMapping("/deleteSchoolNotify/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolNotify(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolNotifyService.deleteSchoolNotify(id);
    }
    @PostMapping("/deleteSchoolNotifyByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolNotifyByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotify schoolNotify){
        schoolNotifyService.deleteSchoolNotifyByCondition(schoolNotify);
    }
    @PostMapping("/findOneSchoolNotifyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolNotify findOneSchoolNotifyByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotify schoolNotify){
        return schoolNotifyService.findOneSchoolNotifyByCondition(schoolNotify);
    }
}
