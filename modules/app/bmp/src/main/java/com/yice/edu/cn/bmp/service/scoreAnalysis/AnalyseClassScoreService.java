package com.yice.edu.cn.bmp.service.scoreAnalysis;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseClassScoreFeign;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;

@Service
public class AnalyseClassScoreService {
    @Autowired
    private AnalyseClassScoreFeign analyseClassScoreFeign;

    public AnalyseClassScore findAnalyseClassScoreById(String id) {
        return analyseClassScoreFeign.findAnalyseClassScoreById(id);
    }

    public AnalyseClassScore saveAnalyseClassScore(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.saveAnalyseClassScore(analyseClassScore);
    }

    public List<AnalyseClassScore> findAnalyseClassScoreListByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findAnalyseClassScoreListByCondition(analyseClassScore);
    }

    public AnalyseClassScore findOneAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findOneAnalyseClassScoreByCondition(analyseClassScore);
    }

    public long findAnalyseClassScoreCountByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findAnalyseClassScoreCountByCondition(analyseClassScore);
    }

    public void updateAnalyseClassScore(AnalyseClassScore analyseClassScore) {
        analyseClassScoreFeign.updateAnalyseClassScore(analyseClassScore);
    }

    public void deleteAnalyseClassScore(String id) {
        analyseClassScoreFeign.deleteAnalyseClassScore(id);
    }

    public void deleteAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
        analyseClassScoreFeign.deleteAnalyseClassScoreByCondition(analyseClassScore);
    }
}
