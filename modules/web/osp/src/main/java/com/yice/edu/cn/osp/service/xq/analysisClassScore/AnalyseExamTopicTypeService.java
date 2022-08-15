package com.yice.edu.cn.osp.service.xq.analysisClassScore;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import com.yice.edu.cn.osp.feignClient.xq.analysisClassScore.AnalyseExamTopicTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseExamTopicTypeService {
    @Autowired
    private AnalyseExamTopicTypeFeign analyseExamTopicTypeFeign;

    public AnalyseExamTopicType findAnalyseExamTopicTypeById(String id) {
        return analyseExamTopicTypeFeign.findAnalyseExamTopicTypeById(id);
    }

    public AnalyseExamTopicType saveAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType) {
        return analyseExamTopicTypeFeign.saveAnalyseExamTopicType(analyseExamTopicType);
    }

    public List<AnalyseExamTopicType> findAnalyseExamTopicTypeListByCondition(AnalyseExamTopicType analyseExamTopicType) {
        return analyseExamTopicTypeFeign.findAnalyseExamTopicTypeListByCondition(analyseExamTopicType);
    }

    public AnalyseExamTopicType findOneAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType) {
        return analyseExamTopicTypeFeign.findOneAnalyseExamTopicTypeByCondition(analyseExamTopicType);
    }

    public long findAnalyseExamTopicTypeCountByCondition(AnalyseExamTopicType analyseExamTopicType) {
        return analyseExamTopicTypeFeign.findAnalyseExamTopicTypeCountByCondition(analyseExamTopicType);
    }

    public void updateAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType) {
        analyseExamTopicTypeFeign.updateAnalyseExamTopicType(analyseExamTopicType);
    }

    public void deleteAnalyseExamTopicType(String id) {
        analyseExamTopicTypeFeign.deleteAnalyseExamTopicType(id);
    }

    public void deleteAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType) {
        analyseExamTopicTypeFeign.deleteAnalyseExamTopicTypeByCondition(analyseExamTopicType);
    }

    public List<AnalyseExamTopicType> analyseExamTopicTypes4Exam(SchoolExam schoolExam){
        return analyseExamTopicTypeFeign.analyseExamTopicTypes4Exam(schoolExam);
    }
}
