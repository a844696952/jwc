package com.yice.edu.cn.osp.controller.jw.exam.reviewTask;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.reviewTask.ReviewTaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviewTask")
public class ReviewTaskController {
    @Autowired
    private ReviewTaskService reviewTaskService;

    @PostMapping("/findReviewTasksByCondition")
    @ApiOperation(value = "通过对象查询",notes = "返回列表和总数量")
    public ResponseJson findBySchoolExamListAndLong(
            @ApiParam(value = "通过对象查询数据")
            @RequestBody SchoolExam schoolExam
            ){
        String teacherId = LoginInterceptor.myId();
       List<SchoolExam> list =  reviewTaskService.findSchoolExamListByCondionKong(schoolExam,teacherId,LoginInterceptor.mySchoolId());
       long count = reviewTaskService.findSchoolExamLongByCondionKong(schoolExam,teacherId,LoginInterceptor.mySchoolId());
       return new ResponseJson(list,count);
    }

    @PostMapping("/look/findSchoolExamStuScoreKong/{courseId}/{schoolExamId}")
    @ApiOperation(value = "通过大题对象查询",notes = "返回教师需批阅的学生成绩")
    public ResponseJson findSchoolExamStuScoreKong(
            @ApiParam(value = "通过大题对象查询")
            @RequestBody AnswerSheetData answerSheetData,
            @PathVariable("courseId") String courseId,@PathVariable("schoolExamId") String schoolExamId
            ){

        ResponseJson responseJson= reviewTaskService.findSchoolExamStuScoreKong(answerSheetData,courseId,schoolExamId,LoginInterceptor.myId());
        return responseJson;
    }

    @PostMapping("/update/updateStuScoreSchoolExamKong/{typeId}/{num}")
    @ApiOperation(value = "批阅学生成绩,需传学生对象与批阅题型Id",notes = "无返回")
    public ResponseJson updateStuScoreSchoolExamKong(
            @ApiParam(value = "批阅学生成绩")
            @RequestBody StuScore stuScore,
            @PathVariable("typeId") Integer typeId,
            @PathVariable("num") Integer num
    ){
        for(int i=0;i<stuScore.getAnswerSheetDatas().size();i++){
            if(stuScore.getAnswerSheetDatas().get(i).getTypeId().equals(typeId)){
                stuScore.getAnswerSheetDatas().get(i).setTeacherName(LoginInterceptor.currentTeacher().getName());
                break;
            }
        }
        reviewTaskService.updateStuScoreSchoolExamKong(stuScore,typeId,LoginInterceptor.myId(),num);
        return new ResponseJson();
    }
}
