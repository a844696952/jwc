package com.yice.edu.cn.ewb.service.scoreAnalysis;


import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.AnalyseClassScoreAllFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
