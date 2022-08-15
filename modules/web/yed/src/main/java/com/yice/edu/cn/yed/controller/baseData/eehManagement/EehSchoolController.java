package com.yice.edu.cn.yed.controller.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.yed.service.baseData.eehManagement.EehSchoolService;
import com.yice.edu.cn.yed.service.baseData.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eehSchool")
@Api(value = "/eehSchool",description = "模块")
public class EehSchoolController {
    @Autowired
    private EehSchoolService eehSchoolService;
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/saveEehSchool")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=EehSchool.class)
    public ResponseJson saveEehSchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehSchool eehSchool){
        EehSchool s=eehSchoolService.saveEehSchool(eehSchool);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEehSchoolById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=EehSchool.class)
    public ResponseJson findEehSchoolById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EehSchool eehSchool=eehSchoolService.findEehSchoolById(id);
        return new ResponseJson(eehSchool);
    }

    @PostMapping("/update/updateEehSchool")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEehSchool(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EehSchool eehSchool){
        eehSchoolService.updateEehSchool(eehSchool);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEehSchoolById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=EehSchool.class)
    public ResponseJson lookEehSchoolById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EehSchool eehSchool=eehSchoolService.findEehSchoolById(id);
        return new ResponseJson(eehSchool);
    }

    @PostMapping("/findEehSchoolsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=EehSchool.class)
    public ResponseJson findEehSchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehSchool eehSchool){
        List<EehSchool> data=eehSchoolService.findEehSchoolListByCondition(eehSchool);
        long count=eehSchoolService.findEehSchoolCountByCondition(eehSchool);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEehSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=EehSchool.class)
    public ResponseJson findOneEehSchoolByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EehSchool eehSchool){
        EehSchool one=eehSchoolService.findOneEehSchoolByCondition(eehSchool);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEehSchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEehSchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        eehSchoolService.deleteEehSchool(id);
        return new ResponseJson();
    }


    @PostMapping("/findEehSchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=EehSchool.class)
    public ResponseJson findEehSchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehSchool eehSchool){
        List<EehSchool> data=eehSchoolService.findEehSchoolListByCondition(eehSchool);
        return new ResponseJson(data);
    }

    @PostMapping("/saveEehSchoolNew")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=EehSchool.class)
    public ResponseJson saveEehSchoolNew(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehSchool eehSchool){
        eehSchoolService.saveEehSchoolNew(eehSchool);
        return new ResponseJson();
    }

    @GetMapping("/findCheckEehSchoolListById/{id}")
    public ResponseJson findCheckEehSchoolListById(@PathVariable String id){
       return new ResponseJson(eehSchoolService.findCheckEehSchoolListById(id));
    }

    @GetMapping("/findNoCheckEehSchoolListById/{type}")
    public ResponseJson findNoCheckEehSchoolListById(@PathVariable String type) {
        EehSchool eehSchool = new EehSchool();
        eehSchool.setType(type);
        List<EehSchool> eehSchoolList = eehSchoolService.findEehSchoolListByCondition(eehSchool);
        School school = new School();
        List<School> schoolList = schoolService.findSchoolListByCondition(school);

        schoolList.removeIf(school1 -> eehSchoolList.stream().anyMatch(eehSchool1 -> {
            return eehSchool1.getSchoolId().equals(school1.getId());
        }));

        return new ResponseJson(schoolList);
    }
}
