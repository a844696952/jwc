package com.yice.edu.cn.tap.service.scoreAnalysis.analysisStudentScore;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.tap.feignClient.schoolYear.SchoolYearFeign;
import com.yice.edu.cn.tap.feignClient.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class AnalysisStudentScoreService {
    @Autowired
    private AnalysisStudentScoreFeign analysisStudentScoreFeign;
    @Autowired
    private SchoolYearFeign schoolYearFeign;

    public AnalysisStudentScore findAnalysisStudentScoreById(String id) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreById(id);
    }
    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisVo analysisVo) {
        AnalysisStudentScore analysisStudentScore = this.init(analysisVo);
        CurSchoolYear curSchoolYear = schoolYearFeign.findCurSchoolYear(mySchoolId());
        SchoolExam schoolExam = analysisStudentScore.getExamination();
        if (schoolExam == null ){
            schoolExam = new SchoolExam();
        }
        schoolExam.setSchoolYearId(curSchoolYear.getSchoolYearId());
        analysisStudentScore.setExamination(schoolExam);
        return analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(analysisStudentScore);
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
