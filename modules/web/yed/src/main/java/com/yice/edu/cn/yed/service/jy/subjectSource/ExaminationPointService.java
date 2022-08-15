package com.yice.edu.cn.yed.service.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.yed.feignClient.jy.subjectSource.ExaminationPointFeign;

@Service
public class ExaminationPointService {
    @Autowired
    private ExaminationPointFeign examinationPointFeign;

    public ExaminationPoint findExaminationPointById(String id) {
        return examinationPointFeign.findExaminationPointById(id);
    }

    public ExaminationPoint saveExaminationPoint(ExaminationPoint examinationPoint) {
        return examinationPointFeign.saveExaminationPoint(examinationPoint);
    }

    public List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint) {
        return examinationPointFeign.findExaminationPointListByCondition(examinationPoint);
    }

    public ExaminationPoint findOneExaminationPointByCondition(ExaminationPoint examinationPoint) {
        return examinationPointFeign.findOneExaminationPointByCondition(examinationPoint);
    }

    public long findExaminationPointCountByCondition(ExaminationPoint examinationPoint) {
        return examinationPointFeign.findExaminationPointCountByCondition(examinationPoint);
    }

    public void updateExaminationPoint(ExaminationPoint examinationPoint) {
        examinationPointFeign.updateExaminationPoint(examinationPoint);
    }

    public void deleteExaminationPoint(String id) {
        examinationPointFeign.deleteExaminationPoint(id);
    }

    public void deleteExaminationPointByCondition(ExaminationPoint examinationPoint) {
        examinationPointFeign.deleteExaminationPointByCondition(examinationPoint);
    }
    
    public List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge){
       return examinationPointFeign.findKnowledgePointListByExamPoint(examPointKnowledge);

    }
    public long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge) {
    	return examinationPointFeign.findKnowledgePointCountByExamPoint(examPointKnowledge);
    }

}
