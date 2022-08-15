package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",contextId = "examinationPointFeignClone",path = "/examinationPoint")
public interface ExaminationPointFeignClone {
    @PostMapping("/findExaminationPointListByCondition")
    List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint);
    @PostMapping("/findKnowledgePointListByExamPoint")
    public List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/findKnowledgePointCountByExamPoint")
    public long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge);

}
