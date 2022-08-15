package com.yice.edu.cn.bmp.feignClient.exam;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetItem;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw",contextId = "reviewTask",path = "/reviewTask")
public interface ReviewTaskFeign {
    @PostMapping("/findStudentSchoolExamList")
    List<SchoolExam> findStudentSchoolExamList(Student student);
    @PostMapping("/findStudentSchoolExamCount")
    long findStudentSchoolExamCount(Student student);

    @PostMapping("/findOneStuScoreByScoreCond")
    StuScore findOneStuScoreByScoreCond(ScoreCond scoreCond);
    @PostMapping("/findAnswerSheetItemOne")
    ResponseJson findAnswerSheetItemOne(AnswerSheetItem answerSheetItem);
}
