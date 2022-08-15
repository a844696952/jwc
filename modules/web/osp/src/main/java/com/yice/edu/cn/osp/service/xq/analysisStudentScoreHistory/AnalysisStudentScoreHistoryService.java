package com.yice.edu.cn.osp.service.xq.analysisStudentScoreHistory;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.StudentScoreListVo;
import com.yice.edu.cn.osp.feignClient.xq.analysisStudentScoreHistory.AnalysisStudentScoreHistoryFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class AnalysisStudentScoreHistoryService {
    @Autowired
    private AnalysisStudentScoreHistoryFeign analysisStudentScoreHistoryFeign;

    public AnalysisStudentScoreHistory findAnalysisStudentScoreHistoryById(String id) {
        return analysisStudentScoreHistoryFeign.findAnalysisStudentScoreHistoryById(id);
    }

    public AnalysisStudentScoreHistory saveAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        return analysisStudentScoreHistoryFeign.saveAnalysisStudentScoreHistory(analysisStudentScoreHistory);
    }

    public void batchSaveAnalysisStudentScoreHistory(List<AnalysisStudentScoreHistory> analysisStudentScoreHistorys){
        analysisStudentScoreHistoryFeign.batchSaveAnalysisStudentScoreHistory(analysisStudentScoreHistorys);
    }

    public List<AnalysisStudentScoreHistory> findAnalysisStudentScoreHistoryListByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        return analysisStudentScoreHistoryFeign.findAnalysisStudentScoreHistoryListByCondition(analysisStudentScoreHistory);
    }

    public AnalysisStudentScoreHistory findOneAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        return analysisStudentScoreHistoryFeign.findOneAnalysisStudentScoreHistoryByCondition(analysisStudentScoreHistory);
    }

    public long findAnalysisStudentScoreHistoryCountByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        return analysisStudentScoreHistoryFeign.findAnalysisStudentScoreHistoryCountByCondition(analysisStudentScoreHistory);
    }

    public void updateAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        analysisStudentScoreHistoryFeign.updateAnalysisStudentScoreHistory(analysisStudentScoreHistory);
    }

    public void deleteAnalysisStudentScoreHistory(String id) {
        analysisStudentScoreHistoryFeign.deleteAnalysisStudentScoreHistory(id);
    }

    public void deleteAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        analysisStudentScoreHistoryFeign.deleteAnalysisStudentScoreHistoryByCondition(analysisStudentScoreHistory);
    }


    private AnalysisStudentScoreHistory adaptExamScoreList(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScoreHistory analysisStudentScoreHistory = new AnalysisStudentScoreHistory();
        analysisStudentScoreHistory.setSchoolId(mySchoolId());
        if(StringUtils.isNotEmpty(studentScoreListVo.getStudentId())){
            Student student = new Student();
            student.setId(studentScoreListVo.getStudentId());
            analysisStudentScoreHistory.setStudent(student);
        }
        if(StringUtils.isNotEmpty(studentScoreListVo.getSubjectId())){
            JwCourse course = new JwCourse();
            course.setId(studentScoreListVo.getSubjectId());
            analysisStudentScoreHistory.setSubject(course);
        }
        if(StringUtils.isNotEmpty(studentScoreListVo.getGradeId())){
            JwClasses jwClasses = new JwClasses();
            jwClasses.setGradeId(studentScoreListVo.getGradeId());
            analysisStudentScoreHistory.setClassObj(jwClasses);
        }
        if(studentScoreListVo.getPager()!=null) {
            Pager pager = studentScoreListVo.getPager();
            if(StringUtils.isEmpty(pager.getSortField())){
                pager.setSortField("examination.examTime");
                pager.setSortOrder(Pager.ASC);
            }
            analysisStudentScoreHistory.setPager(pager);
        }
        return analysisStudentScoreHistory;
    }

    public long findStudentScoresCountByCondition(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScoreHistory analysisStudentScoreHistory = this.adaptExamScoreList(studentScoreListVo);
        return analysisStudentScoreHistoryFeign.findStudentScoresCountByCondition(analysisStudentScoreHistory);
    }

    public List<AnalysisStudentScoreHistory> findStudentScoresByCondition(StudentScoreListVo studentScoreListVo) {
        AnalysisStudentScoreHistory analysisStudentScoreHistory = this.adaptExamScoreList(studentScoreListVo);
        return analysisStudentScoreHistoryFeign.findAnalysisStudentScoreHistoryListByCondition(analysisStudentScoreHistory);
    }
}
