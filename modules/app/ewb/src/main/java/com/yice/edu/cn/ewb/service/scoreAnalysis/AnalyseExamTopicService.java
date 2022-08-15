package com.yice.edu.cn.ewb.service.scoreAnalysis;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.AnalyseExamTopicFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseExamTopicService {
    @Autowired
    private AnalyseExamTopicFeign analyseExamTopicFeign;

    public AnalyseExamTopic findAnalyseExamTopicById(String id) {
        return analyseExamTopicFeign.findAnalyseExamTopicById(id);
    }

    public AnalyseExamTopic saveAnalyseExamTopic(AnalyseExamTopic analyseExamTopic) {
        return analyseExamTopicFeign.saveAnalyseExamTopic(analyseExamTopic);
    }

    public List<AnalyseExamTopic> findAnalyseExamTopicListByCondition(AnalyseExamTopic analyseExamTopic) {
        return analyseExamTopicFeign.findAnalyseExamTopicListByCondition(analyseExamTopic);
    }

    public AnalyseExamTopic findOneAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic) {
        return analyseExamTopicFeign.findOneAnalyseExamTopicByCondition(analyseExamTopic);
    }

    public long findAnalyseExamTopicCountByCondition(AnalyseExamTopic analyseExamTopic) {
        return analyseExamTopicFeign.findAnalyseExamTopicCountByCondition(analyseExamTopic);
    }

    public void updateAnalyseExamTopic(AnalyseExamTopic analyseExamTopic) {
        analyseExamTopicFeign.updateAnalyseExamTopic(analyseExamTopic);
    }

    public void deleteAnalyseExamTopic(String id) {
        analyseExamTopicFeign.deleteAnalyseExamTopic(id);
    }

    public void deleteAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic) {
        analyseExamTopicFeign.deleteAnalyseExamTopicByCondition(analyseExamTopic);
    }
}
