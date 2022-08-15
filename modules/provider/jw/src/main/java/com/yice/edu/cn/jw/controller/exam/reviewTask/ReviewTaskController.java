package com.yice.edu.cn.jw.controller.exam.reviewTask;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetItem;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.jw.service.exam.buildPaper.reviewTask.ReviewTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviewTask")
@Api(value = "/reviewTask",description="阅卷任务模块")
public class ReviewTaskController {
    @Autowired
    private ReviewTaskService reviewTaskService;

    @PostMapping("/findSchoolExamListByCondionKong/{teacherId}/{schoolId}")
    @ApiOperation(value = "通过对象查询",notes = "返回数据列表")
    public List<SchoolExam> findSchoolExamListByCondionKong(
            @ApiParam(value = "通过对象查询")
            @RequestBody SchoolExam schoolExam,
            @PathVariable String teacherId,
            @PathVariable String schoolId
    ){
        return reviewTaskService.findSchoolExamListByCondionKong(schoolExam,teacherId,schoolId);
    }


    @PostMapping("/findSchoolExamLongByCondionKong/{teacherId}/{schoolId}")
    @ApiOperation(value = "通过对象查询",notes = "返回总数量")
    public long findSchoolExamLongByCondionKong(
            @ApiParam(value = "通过对象查询")
            @RequestBody SchoolExam schoolExam,
            @PathVariable String teacherId,
            @PathVariable String schoolId
    ){
        return reviewTaskService.findSchoolExamLongByCondionKong(schoolExam,teacherId,schoolId);
    }


    @PostMapping("/findSchoolExamStuScoreKong/{courseId}/{schoolExamId}/{teacherId}")
    @ApiOperation(value = "通过大题对象查询",notes = "返回当前教师需批阅的学生成绩")
    public ResponseJson findSchoolExamStuScoreKong(
            @ApiParam(value = "通过大题对象查询")
            @RequestBody AnswerSheetData answerSheetData,
            @PathVariable("courseId")String courseId,@PathVariable("schoolExamId") String schoolExamId,@PathVariable("teacherId") String teacherId
            ){
        return reviewTaskService.findSchoolExamStuScoreNewInterface(answerSheetData,courseId,schoolExamId,teacherId);
    }

    @PostMapping("/updateStuScoreSchoolExamKong/{typeId}/{teacherId}/{num}")
    @ApiOperation(value = "批阅学生成绩题目",notes = "无返回")
    public  void updateStuScoreSchoolExamKong(
            @ApiParam(value = "批阅学生成绩题目")
            @RequestBody StuScore stuScore,
            @PathVariable Integer typeId,
            @PathVariable String teacherId,
            @PathVariable Integer num
    ){
           reviewTaskService.updateStuScoreSchoolExamKong(stuScore,typeId,teacherId,num);
    }


    @PostMapping("/findStudentSchoolExamList")
    @ApiOperation(value = "家长端查看考试试卷",notes = "返回学生考试列表")
    public List<SchoolExam> findStudentSchoolExamList(
            @RequestBody Student student
            ){
        return reviewTaskService.findStudentSchoolExamList(student);
    }

    @PostMapping("/findStudentSchoolExamCount")
    @ApiOperation(value = "考试试卷总数量",notes = "返回学生考生总数量")
    public long findStudentSchoolExamCount(
            @RequestBody Student student
    ){
        return reviewTaskService.findStudentSchoolExamCount(student);
    }

    @PostMapping("/findOneStuScoreByScoreCond")
    @ApiOperation(value = "返回对应考试对应科目的考试成绩",notes = "返回对应数据")
    public StuScore findOneStuScoreByScoreCond(
            @RequestBody ScoreCond scoreCond
            ){
        return reviewTaskService.findOneStuScoreByScoreCond(scoreCond);
    }

    @PostMapping("/findAnswerSheetItemOne")
    @ApiOperation(value = "传递学生考试id，和题目序号",notes = "返回对应数据")
    public ResponseJson findAnswerSheetItemOne(
            @RequestBody AnswerSheetItem answerSheetItem
            ){
        return reviewTaskService.findAnswerSheetItemOne(answerSheetItem);
    }
}
