package com.yice.edu.cn.osp.controller.jw.schoolPush;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.osp.service.jw.schoolPush.SchoolPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolPush")
@Api(value = "/schoolPush", description = "学校推送模块")
public class SchoolPushController {
    @Autowired
    private SchoolPushService schoolPushService;

    @PostMapping("/saveSchoolPush")
    @ApiOperation(value = "保存学校推送对象", notes = "返回响应对象")
    public ResponseJson saveSchoolPush(
            @ApiParam(value = "学校推送对象", required = true)
            @RequestBody SchoolPush schoolPush) {
        schoolPush.setSchoolId(mySchoolId());
        SchoolPush s = schoolPushService.saveSchoolPush(schoolPush);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolPushById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校推送", notes = "返回响应对象")
    @EccJpush(type = 0,content = "修改公告")
    public ResponseJson findSchoolPushById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        SchoolPush schoolPush = schoolPushService.findSchoolPushById(id);
        return new ResponseJson(schoolPush);
    }

    @PostMapping("/update/updateSchoolPush")
    @ApiOperation(value = "修改学校推送对象", notes = "返回响应对象")
    @EccJpush(type = 0,content = "修改公告")
    public ResponseJson updateSchoolPush(
            @ApiParam(value = "被修改的学校推送对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolPush schoolPush) {
        schoolPushService.updateSchoolPush(schoolPush);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolPushById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校推送", notes = "返回响应对象")
    public ResponseJson lookSchoolPushById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        SchoolPush schoolPush = schoolPushService.findSchoolPushById(id);
        return new ResponseJson(schoolPush);
    }

    @PostMapping("/findSchoolPushsByCondition")
    @ApiOperation(value = "根据条件查找学校推送", notes = "返回响应对象")
    public ResponseJson findSchoolPushsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolPush schoolPush) {
        schoolPush.setSchoolId(mySchoolId());
        List<SchoolPush> data = schoolPushService.findSchoolPushListByCondition(schoolPush);
        long count = schoolPushService.findSchoolPushCountByCondition(schoolPush);

        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneSchoolPushByCondition")
    @ApiOperation(value = "根据条件查找单个学校推送,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSchoolPushByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolPush schoolPush) {
        SchoolPush one = schoolPushService.findOneSchoolPushByCondition(schoolPush);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteSchoolPush/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = 0,content = "删除公告")
    public ResponseJson deleteSchoolPush(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        schoolPushService.deleteSchoolPush(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolPushListByCondition")
    @ApiOperation(value = "根据条件查找学校推送列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolPushListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolPush schoolPush) {
        schoolPush.setSchoolId(mySchoolId());
        List<SchoolPush> data = schoolPushService.findSchoolPushListByCondition(schoolPush);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/getSchoolPushListToWebIndex")
    public ResponseJson getSchoolPushListToWebIndex(){

        return  new ResponseJson(schoolPushService.getSchoolPushListToWebIndex());
    }
}
