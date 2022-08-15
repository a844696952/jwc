package com.yice.edu.cn.jw.controller.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudent;
import com.yice.edu.cn.jw.service.exam.examManage.ExamStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examStudent")
@Api(value = "/examStudent",description = "考试考生模块")
public class ExamStudentController {
    @Autowired
    private ExamStudentService examStudentService;

    @GetMapping("/findExamStudentById/{id}")
    @ApiOperation(value = "通过id查找考试考生", notes = "返回考试考生对象")
    public ExamStudent findExamStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return examStudentService.findExamStudentById(id);
    }

    @PostMapping("/saveExamStudent")
    @ApiOperation(value = "保存考试考生", notes = "返回考试考生对象")
    public ExamStudent saveExamStudent(
            @ApiParam(value = "考试考生对象", required = true)
            @RequestBody ExamStudent examStudent){
        examStudentService.saveExamStudent(examStudent);
        return examStudent;
    }

    @PostMapping("/findExamStudentListByCondition")
    @ApiOperation(value = "根据条件查找考试考生列表", notes = "返回考试考生列表")
    public List<ExamStudent> findExamStudentListByCondition(
            @ApiParam(value = "考试考生对象")
            @RequestBody ExamStudent examStudent){
        return examStudentService.findExamStudentListByCondition(examStudent);
    }
    @PostMapping("/findExamStudentCountByCondition")
    @ApiOperation(value = "根据条件查找考试考生列表个数", notes = "返回考试考生总个数")
    public long findExamStudentCountByCondition(
            @ApiParam(value = "考试考生对象")
            @RequestBody ExamStudent examStudent){
        return examStudentService.findExamStudentCountByCondition(examStudent);
    }

    @PostMapping("/updateExamStudent")
    @ApiOperation(value = "修改考试考生", notes = "考试考生对象必传")
    public void updateExamStudent(
            @ApiParam(value = "考试考生对象,对象属性不为空则修改", required = true)
            @RequestBody ExamStudent examStudent){
        examStudentService.updateExamStudent(examStudent);
    }

    @GetMapping("/deleteExamStudent/{id}")
    @ApiOperation(value = "通过id删除考试考生")
    public void deleteExamStudent(
            @ApiParam(value = "考试考生对象", required = true)
            @PathVariable String id){
        examStudentService.deleteExamStudent(id);
    }
    @PostMapping("/deleteExamStudentByCondition")
    @ApiOperation(value = "根据条件删除考试考生")
    public void deleteExamStudentByCondition(
            @ApiParam(value = "考试考生对象")
            @RequestBody ExamStudent examStudent){
        examStudentService.deleteExamStudentByCondition(examStudent);
    }
    @PostMapping("/findOneExamStudentByCondition")
    @ApiOperation(value = "根据条件查找单个考试考生,结果必须为单条数据", notes = "返回单个考试考生,没有时为空")
    public ExamStudent findOneExamStudentByCondition(
            @ApiParam(value = "考试考生对象")
            @RequestBody ExamStudent examStudent){
        return examStudentService.findOneExamStudentByCondition(examStudent);
    }

    @PostMapping("/batchSaveExamStudent")
    public void batchSaveExamStudent(@RequestBody List<ExamStudent> examStudents){
        examStudentService.batchSaveExamStudent(examStudents);
    }
}
