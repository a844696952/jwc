package com.yice.edu.cn.osp.service.xq.analysisClassScore;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseExamStuTopicTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseExamStuTopicTypeService {
    @Autowired
    private AnalyseExamStuTopicTypeFeign analyseExamStuTopicTypeFeign;

    public AnalyseExamStuTopicType findAnalyseExamStuTopicTypeById(String id) {
        return analyseExamStuTopicTypeFeign.findAnalyseExamStuTopicTypeById(id);
    }

    public AnalyseExamStuTopicType saveAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType) {
        return analyseExamStuTopicTypeFeign.saveAnalyseExamStuTopicType(analyseExamStuTopicType);
    }

    public List<AnalyseExamStuTopicType> findAnalyseExamStuTopicTypeListByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        return analyseExamStuTopicTypeFeign.findAnalyseExamStuTopicTypeListByCondition(analyseExamStuTopicType);
    }

    public AnalyseExamStuTopicType findOneAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        return analyseExamStuTopicTypeFeign.findOneAnalyseExamStuTopicTypeByCondition(analyseExamStuTopicType);
    }

    public long findAnalyseExamStuTopicTypeCountByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        return analyseExamStuTopicTypeFeign.findAnalyseExamStuTopicTypeCountByCondition(analyseExamStuTopicType);
    }

    public void updateAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType) {
        analyseExamStuTopicTypeFeign.updateAnalyseExamStuTopicType(analyseExamStuTopicType);
    }

    public void deleteAnalyseExamStuTopicType(String id) {
        analyseExamStuTopicTypeFeign.deleteAnalyseExamStuTopicType(id);
    }

    public void deleteAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        analyseExamStuTopicTypeFeign.deleteAnalyseExamStuTopicTypeByCondition(analyseExamStuTopicType);
    }
}
