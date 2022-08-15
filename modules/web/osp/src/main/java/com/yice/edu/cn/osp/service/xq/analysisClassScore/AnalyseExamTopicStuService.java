package com.yice.edu.cn.osp.service.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseExamTopicStuFeign;

@Service
public class AnalyseExamTopicStuService {
    @Autowired
    private AnalyseExamTopicStuFeign analyseExamTopicStuFeign;

    public AnalyseExamTopicStu findAnalyseExamTopicStuById(String id) {
        return analyseExamTopicStuFeign.findAnalyseExamTopicStuById(id);
    }

    public AnalyseExamTopicStu saveAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu) {
        return analyseExamTopicStuFeign.saveAnalyseExamTopicStu(analyseExamTopicStu);
    }

    public List<AnalyseExamTopicStu> findAnalyseExamTopicStuListByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        return analyseExamTopicStuFeign.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
    }

    public AnalyseExamTopicStu findOneAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        return analyseExamTopicStuFeign.findOneAnalyseExamTopicStuByCondition(analyseExamTopicStu);
    }

    public long findAnalyseExamTopicStuCountByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        return analyseExamTopicStuFeign.findAnalyseExamTopicStuCountByCondition(analyseExamTopicStu);
    }

    public void updateAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu) {
        analyseExamTopicStuFeign.updateAnalyseExamTopicStu(analyseExamTopicStu);
    }

    public void deleteAnalyseExamTopicStu(String id) {
        analyseExamTopicStuFeign.deleteAnalyseExamTopicStu(id);
    }

    public void deleteAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        analyseExamTopicStuFeign.deleteAnalyseExamTopicStuByCondition(analyseExamTopicStu);
    }
}
