package com.yice.edu.cn.osp.service.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.StudentScoreListVo;
import com.yice.edu.cn.osp.feignClient.xq.analysisStudentScore.AnalysisStudentScoreFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class AnalysisStudentScoreService {
    @Autowired
    private AnalysisStudentScoreFeign analysisStudentScoreFeign;

    public AnalysisStudentScore findAnalysisStudentScoreById(String id) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreById(id);
    }
    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(this.init(analysisVo));
    }
    public List<Student> findMissingStudentBySubject(AnalysisVo analysisVo){
        AnalysisStudentScore analysisStudentScore = this.init(analysisVo);
        analysisStudentScore.setMissing(true);
        List<AnalysisStudentScore> analysisStudentScoreList = analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(analysisStudentScore);
        if(analysisStudentScoreList!=null&&analysisStudentScoreList.size()>0)
            return analysisStudentScoreList.stream().map(AnalysisStudentScore::getStudent).collect(Collectors.toList());
        return null;
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
     * StudentScoreListVo->AnalysisStudentScore
     * @param studentScoreListVo
     * @return
     */
    private AnalysisStudentScore adaptExamScoreList(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScore analysisStudentScore = new AnalysisStudentScore();
        analysisStudentScore.setSchoolId(mySchoolId());
        if(StringUtils.isNotEmpty(studentScoreListVo.getStudentId())){
            Student student = new Student();
            student.setId(studentScoreListVo.getStudentId());
            analysisStudentScore.setStudent(student);
        }
        if(StringUtils.isNotEmpty(studentScoreListVo.getSubjectId())){
            JwCourse course = new JwCourse();
            course.setId(studentScoreListVo.getSubjectId());
            analysisStudentScore.setSubject(course);
        }
        if(StringUtils.isNotEmpty(studentScoreListVo.getGradeId())){
            JwClasses jwClasses = new JwClasses();
            jwClasses.setGradeId(studentScoreListVo.getGradeId());
            analysisStudentScore.setClassObj(jwClasses);
        }
        if(studentScoreListVo.getPager()!=null) {
            Pager pager = studentScoreListVo.getPager();
            if(StringUtils.isEmpty(pager.getSortField())){
                pager.setSortField("examination.examTime");
                pager.setSortOrder(Pager.ASC);
            }
            analysisStudentScore.setPager(pager);
        }
        return analysisStudentScore;
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
        if(StringUtils.isNotEmpty(analysisVo.getStudentId())){
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analysisStudentScore.setStudent(student);
        }
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            SchoolExam schoolExam = new SchoolExam();
            schoolExam.setId(analysisVo.getExaminationId());
            analysisStudentScore.setExamination(schoolExam);
        }
        if(analysisVo.getPager()!=null)
            analysisStudentScore.setPager(analysisVo.getPager());
        return analysisStudentScore;
    }


    public List<AnalysisStudentScore> findStudentScoresByCondition(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScore analysisStudentScore = this.adaptExamScoreList(studentScoreListVo);
        List<AnalysisStudentScore> analysisStudentScoreList = analysisStudentScoreFeign.findStudentExamScoreListByCondition(analysisStudentScore);
        return analysisStudentScoreList;
    }

    public long findStudentScoresCountByCondition(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScore analysisStudentScore = this.adaptExamScoreList(studentScoreListVo);
        return analysisStudentScoreFeign.findStudentScoresCountByCondition(analysisStudentScore);
    }
}
