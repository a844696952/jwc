package com.yice.edu.cn.osp.service.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.osp.feignClient.xq.analysisStudentScore.AnalysisStudentScoreAllFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class AnalysisStudentScoreAllService {
    @Autowired
    private AnalysisStudentScoreAllFeign analysisStudentScoreAllFeign;

    public AnalysisStudentScoreAll findAnalysisStudentScoreAllById(String id) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllById(id);
    }

    public AnalysisStudentScoreAll saveAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.saveAnalysisStudentScoreAll(analysisStudentScoreAll);
    }

    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
    }

    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisVo analysisVo) {
        AnalysisStudentScoreAll analysisStudentScoreAll = new AnalysisStudentScoreAll();
        analysisStudentScoreAll.setSchoolId(mySchoolId());
        JwClasses classObj = new JwClasses();
        classObj.setId(analysisVo.getClassId());
        analysisStudentScoreAll.setClassObj(classObj);
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analysisStudentScoreAll.setExamination(schoolExam);
        if(analysisVo.getPager()!=null)
            analysisStudentScoreAll.setPager(analysisVo.getPager());
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
    }

    public AnalysisStudentScoreAll findOneAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findOneAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }

    public long findAnalysisStudentScoreAllCountByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllCountByCondition(analysisStudentScoreAll);
    }

    public long findAnalysisStudentScoreAllCountByCondition(AnalysisVo analysisVo) {
        AnalysisStudentScoreAll analysisStudentScoreAll = new AnalysisStudentScoreAll();
        analysisStudentScoreAll.setSchoolId(mySchoolId());
        JwClasses classObj = new JwClasses();
        classObj.setId(analysisVo.getClassId());
        analysisStudentScoreAll.setClassObj(classObj);
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analysisStudentScoreAll.setExamination(schoolExam);
        if(analysisVo.getPager()!=null)
            analysisStudentScoreAll.setPager(analysisVo.getPager());
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllCountByCondition(analysisStudentScoreAll);
    }
    public void updateAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        analysisStudentScoreAllFeign.updateAnalysisStudentScoreAll(analysisStudentScoreAll);
    }

    public void deleteAnalysisStudentScoreAll(String id) {
        analysisStudentScoreAllFeign.deleteAnalysisStudentScoreAll(id);
    }

    public void deleteAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        analysisStudentScoreAllFeign.deleteAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }
    public List<Map<String,Object>> findAnalysisStuScoreAllListByCondition(AnalysisVo analysisVo){
        return analysisStudentScoreAllFeign.findAnalysisStuScoreAllListByCondition(analysisVo);
    }
    public long findAnalysisStuScoreAllCountByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreAllFeign.findAnalysisStuScoreAllCountByCondition(analysisVo);
    }
}
