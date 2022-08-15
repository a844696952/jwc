package com.yice.edu.cn.yed.controller.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.yed.service.baseData.educationBureau.EducationBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educationBureau")
@Api(value = "/educationBureau",description = "教育局模块")
public class EducationBureauController {
    @Autowired
    private EducationBureauService educationBureauService;

    @PostMapping("/saveEducationBureau")
    @ApiOperation(value = "保存教育局对象", notes = "返回响应对象")
    public ResponseJson saveEducationBureau(
            @ApiParam(value = "教育局对象", required = true)
            @RequestBody EducationBureau educationBureau){
        EducationBureau s=educationBureauService.saveEducationBureau(educationBureau);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEducationBureauById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教育局", notes = "返回响应对象")
    public ResponseJson findEducationBureauById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EducationBureau educationBureau=educationBureauService.findEducationBureauById(id);
        return new ResponseJson(educationBureau);
    }

    @PostMapping("/update/updateEducationBureau")
    @ApiOperation(value = "修改教育局对象", notes = "返回响应对象")
    public ResponseJson updateEducationBureau(
            @ApiParam(value = "被修改的教育局对象,对象属性不为空则修改", required = true)
            @RequestBody EducationBureau educationBureau){
        educationBureauService.updateEducationBureau(educationBureau);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEducationBureauById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教育局", notes = "返回响应对象")
    public ResponseJson lookEducationBureauById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EducationBureau educationBureau=educationBureauService.findEducationBureauById(id);
        return new ResponseJson(educationBureau);
    }

    @PostMapping("/findEducationBureausByCondition")
    @ApiOperation(value = "根据条件查找教育局", notes = "返回响应对象")
    public ResponseJson findEducationBureausByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EducationBureau educationBureau){
        List<EducationBureau> data=educationBureauService.findEducationBureauListByCondition(educationBureau);
        long count=educationBureauService.findEducationBureauCountByCondition(educationBureau);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEducationBureauByCondition")
    @ApiOperation(value = "根据条件查找单个教育局,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEducationBureauByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EducationBureau educationBureau){
        EducationBureau one=educationBureauService.findOneEducationBureauByCondition(educationBureau);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEducationBureau/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEducationBureau(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        educationBureauService.deleteEducationBureau(id);
        return new ResponseJson();
    }


    @PostMapping("/findEducationBureauListByCondition")
    @ApiOperation(value = "根据条件查找教育局列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEducationBureauListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EducationBureau educationBureau){
        List<EducationBureau> data=educationBureauService.findEducationBureauListByCondition(educationBureau);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/findSchoolsForTransfer/{educationBureauId}")
    public ResponseJson findSchoolsForTransfer(@PathVariable String educationBureauId){
        List<School> unSelected=educationBureauService.findUnSelectedSchoolsById(educationBureauId);
        List<String> selectedIds = educationBureauService.findSelectedSchoolsById(educationBureauId);
        return new ResponseJson(unSelected,selectedIds);
    }



}
