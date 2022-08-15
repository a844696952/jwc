package com.yice.edu.cn.yed.controller.jw.thirdParty;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchoolTeacher;
import com.yice.edu.cn.yed.service.jw.thirdParty.ApplySchoolTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@RestController
@RequestMapping("/applySchoolTeacher")
@Api(value = "/applySchoolTeacher", description = "绑定到学校中老师的第三方应用")
public class ApplySchoolTeacherController {
    @Autowired
    private ApplySchoolTeacherService applySchoolTeacherService;

    @PostMapping("/saveApplySchoolTeacher")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = ApplySchoolTeacher.class)
    public ResponseJson saveApplySchoolTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        ApplySchoolTeacher s = applySchoolTeacherService.saveApplySchoolTeacher(applySchoolTeacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findApplySchoolTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = ApplySchoolTeacher.class)
    public ResponseJson findApplySchoolTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        ApplySchoolTeacher applySchoolTeacher = applySchoolTeacherService.findApplySchoolTeacherById(id);
        return new ResponseJson(applySchoolTeacher);
    }

    @PostMapping("/update/updateApplySchoolTeacher")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateApplySchoolTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacherService.updateApplySchoolTeacher(applySchoolTeacher);
        return new ResponseJson();
    }

    @PostMapping("/update/updateApplySchoolTeacherForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateApplySchoolTeacherForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacherService.updateApplySchoolTeacherForAll(applySchoolTeacher);
        return new ResponseJson();
    }

    @GetMapping("/look/lookApplySchoolTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = ApplySchoolTeacher.class)
    public ResponseJson lookApplySchoolTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        ApplySchoolTeacher applySchoolTeacher = applySchoolTeacherService.findApplySchoolTeacherById(id);
        return new ResponseJson(applySchoolTeacher);
    }

    @PostMapping("/findApplySchoolTeachersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = ApplySchoolTeacher.class)
    public ResponseJson findApplySchoolTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        List<ApplySchoolTeacher> data = applySchoolTeacherService.findApplySchoolTeacherListByCondition(applySchoolTeacher);
        long count = applySchoolTeacherService.findApplySchoolTeacherCountByCondition(applySchoolTeacher);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneApplySchoolTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = ApplySchoolTeacher.class)
    public ResponseJson findOneApplySchoolTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        ApplySchoolTeacher one = applySchoolTeacherService.findOneApplySchoolTeacherByCondition(applySchoolTeacher);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteApplySchoolTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteApplySchoolTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        applySchoolTeacherService.deleteApplySchoolTeacher(id);
        return new ResponseJson();
    }

    @PostMapping("/findApplySchoolTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = ApplySchoolTeacher.class)
    public ResponseJson findApplySchoolTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchoolTeacher applySchoolTeacher) {
        List<ApplySchoolTeacher> data = applySchoolTeacherService.findApplySchoolTeacherListByCondition(applySchoolTeacher);
        return new ResponseJson(data);
    }


}
