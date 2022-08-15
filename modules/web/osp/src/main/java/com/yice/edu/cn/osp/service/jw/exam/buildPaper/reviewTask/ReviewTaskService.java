package com.yice.edu.cn.osp.service.jw.exam.buildPaper.reviewTask;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.osp.feignClient.jw.exam.reviewTask.ReviewTaskFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ReviewTaskService {
    @Autowired
    private ReviewTaskFeign reviewTaskFeign;

    public List<SchoolExam> findSchoolExamListByCondionKong(SchoolExam schoolExam,String teacherId,String schoolId){
        return reviewTaskFeign.findSchoolExamListByCondionKong(schoolExam,teacherId,schoolId);
    }

    public long findSchoolExamLongByCondionKong(SchoolExam schoolExam,String teacherId,String schoolId){
        return reviewTaskFeign.findSchoolExamLongByCondionKong(schoolExam,teacherId,schoolId);
    }

    public ResponseJson findSchoolExamStuScoreKong(AnswerSheetData answerSheetData, String courseId, String schoolExamId, String teacherId){
        return reviewTaskFeign.findSchoolExamStuScoreKong(answerSheetData,courseId,schoolExamId,teacherId);
    }

    public void  updateStuScoreSchoolExamKong(StuScore stuScore,Integer typeId,String teacherId,Integer num){
        reviewTaskFeign.updateStuScoreSchoolExamKong(stuScore,typeId,teacherId,num);
    }

}
