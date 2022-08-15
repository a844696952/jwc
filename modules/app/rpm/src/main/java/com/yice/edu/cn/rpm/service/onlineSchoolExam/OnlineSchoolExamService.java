package com.yice.edu.cn.rpm.service.onlineSchoolExam;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.rpm.feignClient.onlineSchoolExam.OnlineSchoolExamFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineSchoolExamService {
    @Autowired
    private OnlineSchoolExamFeign onlineSchoolExamFeign;

    public List<SchoolExam> findOnlineSchoolExamNow(String mySchoolId) {
        return onlineSchoolExamFeign.findOnlineSchoolExamNow(mySchoolId);
    }

    public String commitStuScore(List<StuScore> stuScores) {

        return onlineSchoolExamFeign.commitStuScore(stuScores);
    }

    public SchoolExam findSchoolExamById(String schoolExamId) {
        return onlineSchoolExamFeign.findSchoolExamById(schoolExamId);
    }

    public List<ExamStudentInfo> findExamStudentsBySchoolExamId(String schoolExamId) {
        return onlineSchoolExamFeign.findExamStudentsBySchoolExamId(schoolExamId);
    }

    public void updateSchoolExam(SchoolExam schoolExam) {
        onlineSchoolExamFeign.updateSchoolExam(schoolExam);
    }
}
