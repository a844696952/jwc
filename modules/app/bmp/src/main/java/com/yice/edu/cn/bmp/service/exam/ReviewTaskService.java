package com.yice.edu.cn.bmp.service.exam;

import com.yice.edu.cn.bmp.feignClient.exam.ReviewTaskFeign;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetItem;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewTaskService {
    @Autowired
    private ReviewTaskFeign reviewTaskFeign;

    public List<SchoolExam> findStudentSchoolExamList(Student student){
        return reviewTaskFeign.findStudentSchoolExamList(student);
    }

    public long findStudentSchoolExamCount(Student student){
        return reviewTaskFeign.findStudentSchoolExamCount(student);
    }

    public StuScore findOneStuScoreByScoreCond(ScoreCond scoreCond){
        return reviewTaskFeign.findOneStuScoreByScoreCond(scoreCond);
    }

    public ResponseJson findAnswerSheetItemOne(AnswerSheetItem answerSheetItem){
        return reviewTaskFeign.findAnswerSheetItemOne(answerSheetItem);
    }
}
