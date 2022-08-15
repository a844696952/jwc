package com.yice.edu.cn.osp.feignClient.jw.exam.reviewTask;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "jw",contextId = "reviewTaskFeign",path = "/reviewTask")
public interface ReviewTaskFeign {

    @PostMapping("/findSchoolExamListByCondionKong/{teacherId}/{schoolId}")
    List<SchoolExam> findSchoolExamListByCondionKong(@RequestBody SchoolExam schoolExam,@PathVariable("teacherId") String teacherId,@PathVariable("schoolId")String schoolId);

    @PostMapping("/findSchoolExamLongByCondionKong/{teacherId}/{schoolId}")
    long findSchoolExamLongByCondionKong(@RequestBody  SchoolExam schoolExam,@PathVariable("teacherId") String teacherId,@PathVariable("schoolId")String schoolId);

    @PostMapping("/findSchoolExamStuScoreKong/{courseId}/{schoolExamId}/{teacherId}")
    ResponseJson findSchoolExamStuScoreKong(@RequestBody AnswerSheetData answerSheetData, @PathVariable("courseId")String courseId, @PathVariable("schoolExamId") String schoolExamId, @PathVariable("teacherId") String teacherId);

    @PostMapping("/updateStuScoreSchoolExamKong/{typeId}/{teacherId}/{num}")
    void updateStuScoreSchoolExamKong(@RequestBody StuScore stuScore,@PathVariable("typeId") Integer typeId,@PathVariable("teacherId") String teacherId,@PathVariable("num") Integer num);
}
