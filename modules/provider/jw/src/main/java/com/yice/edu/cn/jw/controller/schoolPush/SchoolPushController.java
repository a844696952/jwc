package com.yice.edu.cn.jw.controller.schoolPush;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.jw.service.schoolPush.SchoolPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolPush")
@Api(value = "/schoolPush",description = "学校推送模块")
public class SchoolPushController {
    @Autowired
    private SchoolPushService schoolPushService;

    @GetMapping("/findSchoolPushById/{id}")
    @ApiOperation(value = "通过id查找学校推送", notes = "返回学校推送对象")
    public SchoolPush findSchoolPushById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolPushService.findSchoolPushById(id);
    }

    @PostMapping("/saveSchoolPush")
    @ApiOperation(value = "保存学校推送", notes = "返回学校推送对象")
    public SchoolPush saveSchoolPush(
            @ApiParam(value = "学校推送对象", required = true)
            @RequestBody SchoolPush schoolPush){
        schoolPushService.saveSchoolPush(schoolPush);
        return schoolPush;
    }

    @PostMapping("/findSchoolPushListByCondition")
    @ApiOperation(value = "根据条件查找学校推送列表", notes = "返回学校推送列表")
    public List<SchoolPush> findSchoolPushListByCondition(
            @ApiParam(value = "学校推送对象")
            @RequestBody SchoolPush schoolPush){
        return schoolPushService.findSchoolPushListByCondition(schoolPush);
    }
    @PostMapping("/findSchoolPushCountByCondition")
    @ApiOperation(value = "根据条件查找学校推送列表个数", notes = "返回学校推送总个数")
    public long findSchoolPushCountByCondition(
            @ApiParam(value = "学校推送对象")
            @RequestBody SchoolPush schoolPush){
        return schoolPushService.findSchoolPushCountByCondition(schoolPush);
    }

    @PostMapping("/updateSchoolPush")
    @ApiOperation(value = "修改学校推送", notes = "学校推送对象必传")
    public void updateSchoolPush(
            @ApiParam(value = "学校推送对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolPush schoolPush){
        schoolPushService.updateSchoolPush(schoolPush);
    }

    @GetMapping("/deleteSchoolPush/{id}")
    @ApiOperation(value = "通过id删除学校推送")
    public void deleteSchoolPush(
            @ApiParam(value = "学校推送对象", required = true)
            @PathVariable String id){
        schoolPushService.deleteSchoolPush(id);
    }
    @PostMapping("/deleteSchoolPushByCondition")
    @ApiOperation(value = "根据条件删除学校推送")
    public void deleteSchoolPushByCondition(
            @ApiParam(value = "学校推送对象")
            @RequestBody SchoolPush schoolPush){
        schoolPushService.deleteSchoolPushByCondition(schoolPush);
    }
    @PostMapping("/findOneSchoolPushByCondition")
    @ApiOperation(value = "根据条件查找单个学校推送,结果必须为单条数据", notes = "返回单个学校推送,没有时为空")
    public SchoolPush findOneSchoolPushByCondition(
            @ApiParam(value = "学校推送对象")
            @RequestBody SchoolPush schoolPush){
        return schoolPushService.findOneSchoolPushByCondition(schoolPush);
    }

    @PostMapping("/findPageSchoolPushesByAppIdAndSchoolId")
    @ApiOperation("通过appId（JpushApp枚举类中的id）和schoolId以及pager,获取学校广播列表")
    public List<SchoolPush> findPageSchoolPushesByAppIdAndSchoolId(@RequestBody SchoolPush schoolPush){
        return schoolPushService.findPageSchoolPushesByAppIdAndSchoolId(schoolPush);
    }

}
