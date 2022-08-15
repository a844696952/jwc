package com.yice.edu.cn.osp.service.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseSubjectGradeScoreFeign;

@Service
public class AnalyseSubjectGradeScoreService {
    @Autowired
    private AnalyseSubjectGradeScoreFeign analyseSubjectGradeScoreFeign;

    public AnalyseSubjectGradeScore findAnalyseSubjectGradeScoreById(String id) {
        return analyseSubjectGradeScoreFeign.findAnalyseSubjectGradeScoreById(id);
    }

    public AnalyseSubjectGradeScore saveAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        return analyseSubjectGradeScoreFeign.saveAnalyseSubjectGradeScore(analyseSubjectGradeScore);
    }

    public List<AnalyseSubjectGradeScore> findAnalyseSubjectGradeScoreListByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        return analyseSubjectGradeScoreFeign.findAnalyseSubjectGradeScoreListByCondition(analyseSubjectGradeScore);
    }

    public AnalyseSubjectGradeScore findOneAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        return analyseSubjectGradeScoreFeign.findOneAnalyseSubjectGradeScoreByCondition(analyseSubjectGradeScore);
    }

    public long findAnalyseSubjectGradeScoreCountByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        return analyseSubjectGradeScoreFeign.findAnalyseSubjectGradeScoreCountByCondition(analyseSubjectGradeScore);
    }

    public void updateAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        analyseSubjectGradeScoreFeign.updateAnalyseSubjectGradeScore(analyseSubjectGradeScore);
    }

    public void deleteAnalyseSubjectGradeScore(String id) {
        analyseSubjectGradeScoreFeign.deleteAnalyseSubjectGradeScore(id);
    }

    public void deleteAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        analyseSubjectGradeScoreFeign.deleteAnalyseSubjectGradeScoreByCondition(analyseSubjectGradeScore);
    }
}
