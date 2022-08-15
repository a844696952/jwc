package com.yice.edu.cn.osp.service.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseClassScoreAllFeign;

@Service
public class AnalyseClassScoreAllService {
    @Autowired
    private AnalyseClassScoreAllFeign analyseClassScoreAllFeign;

    public AnalyseClassScoreAll findAnalyseClassScoreAllById(String id) {
        return analyseClassScoreAllFeign.findAnalyseClassScoreAllById(id);
    }

    public AnalyseClassScoreAll saveAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll) {
        return analyseClassScoreAllFeign.saveAnalyseClassScoreAll(analyseClassScoreAll);
    }

    public List<AnalyseClassScoreAll> findAnalyseClassScoreAllListByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        return analyseClassScoreAllFeign.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
    }

    public AnalyseClassScoreAll findOneAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        return analyseClassScoreAllFeign.findOneAnalyseClassScoreAllByCondition(analyseClassScoreAll);
    }

    public long findAnalyseClassScoreAllCountByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        return analyseClassScoreAllFeign.findAnalyseClassScoreAllCountByCondition(analyseClassScoreAll);
    }

    public void updateAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll) {
        analyseClassScoreAllFeign.updateAnalyseClassScoreAll(analyseClassScoreAll);
    }

    public void deleteAnalyseClassScoreAll(String id) {
        analyseClassScoreAllFeign.deleteAnalyseClassScoreAll(id);
    }

    public void deleteAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        analyseClassScoreAllFeign.deleteAnalyseClassScoreAllByCondition(analyseClassScoreAll);
    }

    public void analyseClassScore(String examId) {
        analyseClassScoreAllFeign.analyseClassScore(examId);
    }
}
