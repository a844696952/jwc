package com.yice.edu.cn.ewb.service.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.analysisKnowledge.AnalyseStuKnowledgeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseStuKnowledgeService {
    @Autowired
    private AnalyseStuKnowledgeFeign analyseStuKnowledgeFeign;

    public AnalyseStuKnowledge findAnalyseStuKnowledgeById(String id) {
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeById(id);
    }

    public AnalyseStuKnowledge saveAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.saveAnalyseStuKnowledge(analyseStuKnowledge);
    }

    public List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
    }

    public AnalyseStuKnowledge findOneAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.findOneAnalyseStuKnowledgeByCondition(analyseStuKnowledge);
    }

    public long findAnalyseStuKnowledgeCountByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeCountByCondition(analyseStuKnowledge);
    }

    public void updateAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge) {
        analyseStuKnowledgeFeign.updateAnalyseStuKnowledge(analyseStuKnowledge);
    }

    public void deleteAnalyseStuKnowledge(String id) {
        analyseStuKnowledgeFeign.deleteAnalyseStuKnowledge(id);
    }

    public void deleteAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        analyseStuKnowledgeFeign.deleteAnalyseStuKnowledgeByCondition(analyseStuKnowledge);
    }
}
