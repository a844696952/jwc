package com.yice.edu.cn.bmp.service.scoreAnalysis;


import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseExamTopicStuFeign;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
