package com.yice.edu.cn.bmp.controller.dd;

import com.yice.edu.cn.bmp.service.dd.DdService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DdController")
@Api(value = "/dd",description = "数据字典系统维护字段模块")
public class DdController {
    @Autowired
    private DdService ddService;
    @GetMapping("/findDdById/{id}")
    @ApiOperation(value = "通过id查找数据字典系统维护字段", notes = "返回响应对象")
    public ResponseJson findDdById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Dd dd=ddService.findDdById(id);
        return new ResponseJson(dd);
    }

    @PostMapping("/saveDd")
    @ApiOperation(value = "保存数据字典系统维护字段对象", notes = "返回响应对象")
    public ResponseJson saveDd(
            @ApiParam(value = "数据字典系统维护字段对象", required = true)
            @RequestBody Dd dd){
        Dd s=ddService.saveDd(dd);
        return new ResponseJson(s);
    }
    @PostMapping("/updateDd")
    @ApiOperation(value = "修改数据字典系统维护字段对象", notes = "返回响应对象")
    public ResponseJson updateDd(
            @ApiParam(value = "被修改的数据字典系统维护字段对象,对象属性不为空则修改", required = true)
            @RequestBody Dd dd){
        ddService.updateDd(dd);
        return new ResponseJson();
    }

    @PostMapping("/findDdsByCondition")
    @ApiOperation(value = "根据条件查找数据字典系统维护字段", notes = "返回响应对象")
    public ResponseJson findDdsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Dd dd){
        List<Dd> data=ddService.findDdListByCondition(dd);
        long count=ddService.findDdCountByCondition(dd);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteDd/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDd(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ddService.deleteDd(id);
        return new ResponseJson();
    }
    @GetMapping("/findNationality")
    @ApiOperation(value = "查找国籍", notes = "返回响应对象")
    public ResponseJson findNationality(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.NATIONALITY);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findSex")
    @ApiOperation(value = "查找性别", notes = "返回响应对象")
    public ResponseJson findSex(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SEX);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findPoliticalFace")
    @ApiOperation(value = "查找政治面貌", notes = "返回响应对象")
    public ResponseJson findPoliticalFace(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.POLITICAL_FACE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findGradeJunior")
    @ApiOperation(value = "查找小学年级", notes = "返回响应对象")
    public ResponseJson findGradeJunior(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(Constant.DD_LEVEL.JUNIOR);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findGradeMiddle")
    @ApiOperation(value = "查找初中年级", notes = "返回响应对象")
    public ResponseJson findGradeMiddle(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(Constant.DD_LEVEL.MIDDLE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findGradeHign")
    @ApiOperation(value = "查找高中年级", notes = "返回响应对象")
    public ResponseJson findGradeHign(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(Constant.DD_LEVEL.HIGH);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findSubjectByLevelId/{id}")
    @ApiOperation(value = "查找年段科目", notes = "返回响应对象")
    public ResponseJson findSubjectByLevelId(@PathVariable("id") String id){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SUBJECT);
        dd.setLevelType(id);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findHighestEdu")
    @ApiOperation(value = "查找最高学历", notes = "返回响应对象")
    public ResponseJson findHighestEdu(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.HIGHEST_EDU);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findTeacherStatus")
    @ApiOperation(value = "查找教师状态", notes = "返回响应对象")
    public ResponseJson findTeacherStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.TEACHER_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findStudentStatus")
    @ApiOperation(value = "查找学生状态", notes = "返回响应对象")
    public ResponseJson findStudentStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.STUDENT_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findDocumentType")
    @ApiOperation(value = "查找证件类型", notes = "返回响应对象")
    public ResponseJson findDocumentType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.DOCUMENT_TYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findTeacherPost")
    @ApiOperation(value = "查找教师职务", notes = "返回响应对象")
    public ResponseJson findTeacherPost(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.TEACHER_POST);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @PostMapping("/findDdListByCondition")
    @ApiOperation(value = "根据条件查找字典数据")
    public ResponseJson findDdListByCondition(@RequestBody Dd dd){
        return new ResponseJson(ddService.findDdListByCondition(dd));
    }
    @GetMapping("/findSchoolStatus")
    @ApiOperation(value = "查找学校状态", notes = "返回响应对象")
    public ResponseJson findSchoolStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SCHOOL_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findMaritalStatus")
    @ApiOperation(value = "查询婚姻状况数据")
    public ResponseJson findMaritalStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.MARITALSTATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @PostMapping("/findDdAnnualPeriod")
    @ApiOperation(value = "查找年段(小学、初中、高中...)")
    public ResponseJson findDdAnnualPeriod(){
    	Dd dd = new Dd();
    	dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
        return new ResponseJson(ddService.findDdListByCondition(dd));
    }

    @GetMapping("/findGradeByAnnualPeriodId/{id}")
    @ApiOperation(value = "根据年段id查找拥有的年级", notes = "返回响应对象")
    public ResponseJson findGradeByAnnualPeriodId(@PathVariable String id){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(id);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

}
