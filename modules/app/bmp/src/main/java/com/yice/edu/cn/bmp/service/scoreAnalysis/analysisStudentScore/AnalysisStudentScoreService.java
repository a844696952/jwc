package com.yice.edu.cn.bmp.service.scoreAnalysis.analysisStudentScore;

import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreFeign;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;


@Service
public class AnalysisStudentScoreService {
    @Autowired
    private AnalysisStudentScoreFeign analysisStudentScoreFeign;

    public AnalysisStudentScore findAnalysisStudentScoreById(String id) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreById(id);
    }
    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisStudentScore analysisStudentScore) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(analysisStudentScore);
    }
    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(this.init(analysisVo));
    }
    public AnalysisStudentScore findOneAnalysisStudentScoreByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreFeign.findOneAnalysisStudentScoreByCondition(this.init(analysisVo));
    }
    public long findAnalysisStudentScoreCountByCondition(AnalysisStudentScore analysisStudentScore) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreCountByCondition(analysisStudentScore);
    }
    public long findAnalysisStudentScoreCountByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreCountByCondition(this.init(analysisVo));
    }
    public void analysisStudentScore(String examinationId){
        analysisStudentScoreFeign.analysisStudentScore(examinationId);
    }

    /**
     * 初始化请求参数构造
     * @return
     */
    private AnalysisStudentScore init(AnalysisVo analysisVo){
        AnalysisStudentScore analysisStudentScore = new AnalysisStudentScore();
        analysisStudentScore.setSchoolId(mySchoolId());
        if(StringUtils.isNotEmpty(analysisVo.getClassId())){
            JwClasses classObj = new JwClasses();
            classObj.setId(analysisVo.getClassId());
            analysisStudentScore.setClassObj(classObj);
        }
        if(StringUtils.isNotEmpty(analysisVo.getSubjectId())){
            JwCourse course = new JwCourse();
            course.setId(analysisVo.getSubjectId());
            analysisStudentScore.setSubject(course);
        }
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            SchoolExam schoolExam = new SchoolExam();
            schoolExam.setId(analysisVo.getExaminationId());
            analysisStudentScore.setExamination(schoolExam);
        }
        if(StringUtils.isNotEmpty(analysisVo.getStudentId())){
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analysisStudentScore.setStudent(student);
        }
        if(analysisVo.getPager()!=null)
            analysisStudentScore.setPager(analysisVo.getPager());
        return analysisStudentScore;
    }
}
