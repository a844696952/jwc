package com.yice.edu.cn.osp.service.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseAllSubjectGradeScore;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseAllSubjectGradeScoreFeign;

@Service
public class AnalyseAllSubjectGradeScoreService {
    @Autowired
    private AnalyseAllSubjectGradeScoreFeign analyseAllSubjectGradeScoreFeign;

    public AnalyseAllSubjectGradeScore findAnalyseAllSubjectGradeScoreById(String id) {
        return analyseAllSubjectGradeScoreFeign.findAnalyseAllSubjectGradeScoreById(id);
    }

    public AnalyseAllSubjectGradeScore saveAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        return analyseAllSubjectGradeScoreFeign.saveAnalyseAllSubjectGradeScore(analyseAllSubjectGradeScore);
    }

    public List<AnalyseAllSubjectGradeScore> findAnalyseAllSubjectGradeScoreListByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        return analyseAllSubjectGradeScoreFeign.findAnalyseAllSubjectGradeScoreListByCondition(analyseAllSubjectGradeScore);
    }

    public AnalyseAllSubjectGradeScore findOneAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        return analyseAllSubjectGradeScoreFeign.findOneAnalyseAllSubjectGradeScoreByCondition(analyseAllSubjectGradeScore);
    }

    public long findAnalyseAllSubjectGradeScoreCountByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        return analyseAllSubjectGradeScoreFeign.findAnalyseAllSubjectGradeScoreCountByCondition(analyseAllSubjectGradeScore);
    }

    public void updateAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        analyseAllSubjectGradeScoreFeign.updateAnalyseAllSubjectGradeScore(analyseAllSubjectGradeScore);
    }

    public void deleteAnalyseAllSubjectGradeScore(String id) {
        analyseAllSubjectGradeScoreFeign.deleteAnalyseAllSubjectGradeScore(id);
    }

    public void deleteAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        analyseAllSubjectGradeScoreFeign.deleteAnalyseAllSubjectGradeScoreByCondition(analyseAllSubjectGradeScore);
    }
}
