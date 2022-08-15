package com.yice.edu.cn.jw.controller.stuEvaluate;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.jw.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuEvaluateContent")
@Api(value = "/stuEvaluateContent",description = "模块")
public class StuEvaluateContentController {
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolYearService schoolYearService;

    @GetMapping("/findStuEvaluateContentById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuEvaluateContent findStuEvaluateContentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuEvaluateContentService.findStuEvaluateContentById(id);
    }

    @PostMapping("/saveStuEvaluateContent")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuEvaluateContent saveStuEvaluateContent(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContentService.saveStuEvaluateContent(stuEvaluateContent);
        return stuEvaluateContent;
    }

    @PostMapping("/findStuEvaluateContentListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuEvaluateContent> findStuEvaluateContentListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateContent stuEvaluateContent){
        return stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
    }
    @PostMapping("/findStuEvaluateContentCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuEvaluateContentCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateContent stuEvaluateContent){
        return stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
    }

    @PostMapping("/updateStuEvaluateContent")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateStuEvaluateContent(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContentService.updateStuEvaluateContent(stuEvaluateContent);
    }

    @GetMapping("/deleteStuEvaluateContent/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuEvaluateContent(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuEvaluateContentService.deleteStuEvaluateContent(id);
    }
    @PostMapping("/deleteStuEvaluateContentByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuEvaluateContentByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContentService.deleteStuEvaluateContentByCondition(stuEvaluateContent);
    }
    @PostMapping("/findOneStuEvaluateContentByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuEvaluateContent findOneStuEvaluateContentByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateContent stuEvaluateContent){
        return stuEvaluateContentService.findOneStuEvaluateContentByCondition(stuEvaluateContent);
    }


    @PostMapping("/findStuEvaluateContentListByCondition1")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuEvaluateContent> findStuEvaluateContentListByCondition1(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateContent stuEvaluateContent){
       String state= stuEvaluateContent.getEvaluateState();
       stuEvaluateContent.setEvaluateState(null);
        long num=stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
        if(num==0){
                Student student= new Student();
                student.setClassesId(stuEvaluateContent.getClassId());
                List<Student> studentList= studentService.findStudentListByCondition(student);
                CurSchoolYear schoolYear=schoolYearService.findCurSchoolYear(stuEvaluateContent.getSchoolId());
                studentList.forEach(student1 ->{
                    StuEvaluateContent stuEvaluateContent1  =new StuEvaluateContent();
                    stuEvaluateContent1.setClassId(student1.getClassesId());
                    stuEvaluateContent1.setImg(student1.getImgUrl());
                    stuEvaluateContent1.setStudentId(student1.getId());
                    stuEvaluateContent1.setStudentName(student1.getName());
                    stuEvaluateContent1.setEvaluateId(stuEvaluateContent.getEvaluateId());
                    stuEvaluateContent1.setEvaluateTitle(stuEvaluateContent.getEvaluateTitle());
                    stuEvaluateContent1.setEndTime(stuEvaluateContent.getEndTime());
                    stuEvaluateContent1.setEvaluateState("1");
                    stuEvaluateContent1.setPushState("1");
                    stuEvaluateContent1.setSchoolId(stuEvaluateContent.getSchoolId());
                    stuEvaluateContent1.setCreateTime(DateUtil.now());

                    //之后保存历史数据的时候需要使用
                    stuEvaluateContent1.setSchoolYearId(schoolYear.getSchoolYearId());
                    stuEvaluateContent1.setFromTo(schoolYear.getFromTo());
                    stuEvaluateContent1.setTerm(schoolYear.getTerm());
                    stuEvaluateContentService.saveStuEvaluateContent(stuEvaluateContent1);
                });
        }
        stuEvaluateContent.setEvaluateState(state);
        return stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
    }
}
