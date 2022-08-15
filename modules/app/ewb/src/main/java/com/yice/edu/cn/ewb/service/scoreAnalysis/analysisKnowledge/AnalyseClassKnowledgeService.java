package com.yice.edu.cn.ewb.service.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.analysisKnowledge.AnalyseClassKnowledgeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseClassKnowledgeService {
    @Autowired
    private AnalyseClassKnowledgeFeign analyseClassKnowledgeFeign;

    public AnalyseClassKnowledge findAnalyseClassKnowledgeById(String id) {
        return analyseClassKnowledgeFeign.findAnalyseClassKnowledgeById(id);
    }

    public AnalyseClassKnowledge saveAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge) {
        return analyseClassKnowledgeFeign.saveAnalyseClassKnowledge(analyseClassKnowledge);
    }

    public List<AnalyseClassKnowledge> findAnalyseClassKnowledgeListByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        return analyseClassKnowledgeFeign.findAnalyseClassKnowledgeListByCondition(analyseClassKnowledge);
    }

    public AnalyseClassKnowledge findOneAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        return analyseClassKnowledgeFeign.findOneAnalyseClassKnowledgeByCondition(analyseClassKnowledge);
    }

    public long findAnalyseClassKnowledgeCountByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        return analyseClassKnowledgeFeign.findAnalyseClassKnowledgeCountByCondition(analyseClassKnowledge);
    }

    public void updateAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge) {
        analyseClassKnowledgeFeign.updateAnalyseClassKnowledge(analyseClassKnowledge);
    }

    public void deleteAnalyseClassKnowledge(String id) {
        analyseClassKnowledgeFeign.deleteAnalyseClassKnowledge(id);
    }

    public void deleteAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        analyseClassKnowledgeFeign.deleteAnalyseClassKnowledgeByCondition(analyseClassKnowledge);
    }
}
