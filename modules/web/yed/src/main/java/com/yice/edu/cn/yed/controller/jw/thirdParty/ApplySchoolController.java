package com.yice.edu.cn.yed.controller.jw.thirdParty;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import com.yice.edu.cn.yed.service.jw.thirdParty.ApplySchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@RestController
@RequestMapping("/applySchool")
@Api(value = "/applySchool", description = "绑定到学校的第三方应用")
public class ApplySchoolController {
    @Autowired
    private ApplySchoolService applySchoolService;

    @PostMapping("/saveApplySchool")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = ApplySchool.class)
    public ResponseJson saveApplySchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<ApplySchool> applySchoolList) {
        List<ApplySchool> s = applySchoolService.saveApplySchool(applySchoolList);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findApplySchoolById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = ApplySchool.class)
    public ResponseJson findApplySchoolById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        ApplySchool applySchool = applySchoolService.findApplySchoolById(id);
        return new ResponseJson(applySchool);
    }

    @PostMapping("/update/updateApplySchool")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateApplySchool(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ApplySchool applySchool) {
        applySchoolService.updateApplySchool(applySchool);
        return new ResponseJson();
    }

    @PostMapping("/update/updateApplySchoolForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateApplySchoolForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody ApplySchool applySchool) {
        applySchoolService.updateApplySchoolForAll(applySchool);
        return new ResponseJson();
    }

    @GetMapping("/look/lookApplySchoolById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = ApplySchool.class)
    public ResponseJson lookApplySchoolById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        ApplySchool applySchool = applySchoolService.findApplySchoolById(id);
        return new ResponseJson(applySchool);
    }

    @PostMapping("/findApplySchoolsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = ApplySchool.class)
    public ResponseJson findApplySchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchool applySchool) {
        List<ApplySchool> data = applySchoolService.findApplySchoolListByCondition(applySchool);
        long count = applySchoolService.findApplySchoolCountByCondition(applySchool);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneApplySchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = ApplySchool.class)
    public ResponseJson findOneApplySchoolByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ApplySchool applySchool) {
        ApplySchool one = applySchoolService.findOneApplySchoolByCondition(applySchool);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteApplySchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteApplySchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        applySchoolService.deleteApplySchool(id);
        return new ResponseJson();
    }


    @PostMapping("/findApplySchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = ApplySchool.class)
    public ResponseJson findApplySchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchool applySchool) {
        List<ApplySchool> data = applySchoolService.findApplySchoolListByCondition(applySchool);
        return new ResponseJson(data);
    }


}
