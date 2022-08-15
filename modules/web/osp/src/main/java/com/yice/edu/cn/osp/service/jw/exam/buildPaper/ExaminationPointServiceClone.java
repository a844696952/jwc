package com.yice.edu.cn.osp.service.jw.exam.buildPaper;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.ExaminationPointFeignClone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationPointServiceClone {
    @Autowired
    private ExaminationPointFeignClone examinationPointFeignClone;

    public List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint) {
        return examinationPointFeignClone.findExaminationPointListByCondition(examinationPoint);
    }

    public List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge){
        return examinationPointFeignClone.findKnowledgePointListByExamPoint(examPointKnowledge);

    }
    public long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge) {
        return examinationPointFeignClone.findKnowledgePointCountByExamPoint(examPointKnowledge);
    }

}
