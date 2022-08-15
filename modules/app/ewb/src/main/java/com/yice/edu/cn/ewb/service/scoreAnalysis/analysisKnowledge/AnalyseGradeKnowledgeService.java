package com.yice.edu.cn.ewb.service.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.analysisKnowledge.AnalyseGradeKnowledgeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseGradeKnowledgeService {
    @Autowired
    private AnalyseGradeKnowledgeFeign analyseGradeKnowledgeFeign;

    public AnalyseGradeKnowledge findAnalyseGradeKnowledgeById(String id) {
        return analyseGradeKnowledgeFeign.findAnalyseGradeKnowledgeById(id);
    }

    public AnalyseGradeKnowledge saveAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge) {
        return analyseGradeKnowledgeFeign.saveAnalyseGradeKnowledge(analyseGradeKnowledge);
    }

    public List<AnalyseGradeKnowledge> findAnalyseGradeKnowledgeListByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        return analyseGradeKnowledgeFeign.findAnalyseGradeKnowledgeListByCondition(analyseGradeKnowledge);
    }

    public AnalyseGradeKnowledge findOneAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        return analyseGradeKnowledgeFeign.findOneAnalyseGradeKnowledgeByCondition(analyseGradeKnowledge);
    }

    public long findAnalyseGradeKnowledgeCountByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        return analyseGradeKnowledgeFeign.findAnalyseGradeKnowledgeCountByCondition(analyseGradeKnowledge);
    }

    public void updateAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge) {
        analyseGradeKnowledgeFeign.updateAnalyseGradeKnowledge(analyseGradeKnowledge);
    }

    public void deleteAnalyseGradeKnowledge(String id) {
        analyseGradeKnowledgeFeign.deleteAnalyseGradeKnowledge(id);
    }

    public void deleteAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        analyseGradeKnowledgeFeign.deleteAnalyseGradeKnowledgeByCondition(analyseGradeKnowledge);
    }
}
