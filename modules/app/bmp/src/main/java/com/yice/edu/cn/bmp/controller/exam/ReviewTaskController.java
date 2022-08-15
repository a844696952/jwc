package com.yice.edu.cn.bmp.controller.exam;

import com.yice.edu.cn.bmp.service.exam.ReviewTaskService;
import com.yice.edu.cn.bmp.service.exam.SchoolExamService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetItem;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviewTask")
@Api(value = "/reviewTask",description = "阅卷模块")
public class ReviewTaskController {
    @Autowired
    private ReviewTaskService reviewTaskService;
    @Autowired
    private SchoolExamService schoolExamService;

    @PostMapping("/look/findStudentSchoolExamList")
    @ApiOperation(value = "查询考试列表，传递学生对象,id:'',pager:{分页}",notes = "返回考试列表")
    public ResponseJson findStudentSchoolExamList(
            @ApiParam(value = "例子：id:'***',pager:{分页}")
            @RequestBody Student student
            ){
        List<SchoolExam> list = reviewTaskService.findStudentSchoolExamList(student);
        long count = reviewTaskService.findStudentSchoolExamCount(student);
        return new ResponseJson(list,count);
    }

    @PostMapping("/look/findOneStuScoreByScoreCond")
    @ApiOperation(value = "传递科目id,考试id，学生id查询对应的试卷,courseId,schoolExamId,studentId",notes = "返回对应成绩")
    public ResponseJson findOneStuScoreByScoreCond(
            @ApiParam(value = "例子:courseId:'****',schoolExamId:'****',studentId:'***'")
            @RequestBody ScoreCond scoreCond
    ){
        StuScore stuScore = reviewTaskService.findOneStuScoreByScoreCond(scoreCond);
        return new ResponseJson(stuScore);
    }

    @PostMapping("/look/findAnswerSheetItemOne")
    @ApiOperation(value = "传递学生考试id(stuScoreId)，题目序号(num,整型)",notes = "返回小题数据")
    public ResponseJson findAnswerSheetItemOne(
            @ApiParam(value = "例子:stuScoreId:'*****',num:'***'")
            @RequestBody AnswerSheetItem answerSheetItem
            ){
        return reviewTaskService.findAnswerSheetItemOne(answerSheetItem);
    }

}
