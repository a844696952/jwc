package com.yice.edu.cn.osp.service.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseClassScoreFeign;

import io.swagger.annotations.ApiOperation;

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
    public void saveTopClazzScoreAnalyse(
    		List<String> clazzIdList) {
    	analyseClassScoreFeign.saveTopClazzScoreAnalyse(clazzIdList);
    }
}
