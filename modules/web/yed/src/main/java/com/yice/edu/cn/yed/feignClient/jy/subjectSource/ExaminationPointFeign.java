package com.yice.edu.cn.yed.feignClient.jy.subjectSource;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;


@FeignClient(value="jy",contextId = "examinationPointFeign",path = "/examinationPoint")
public interface ExaminationPointFeign {
    @GetMapping("/findExaminationPointById/{id}")
    ExaminationPoint findExaminationPointById(@PathVariable("id") String id);
    @PostMapping("/saveExaminationPoint")
    ExaminationPoint saveExaminationPoint(ExaminationPoint examinationPoint);
    @PostMapping("/findExaminationPointListByCondition")
    List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint);
    @PostMapping("/findOneExaminationPointByCondition")
    ExaminationPoint findOneExaminationPointByCondition(ExaminationPoint examinationPoint);
    @PostMapping("/findExaminationPointCountByCondition")
    long findExaminationPointCountByCondition(ExaminationPoint examinationPoint);
    @PostMapping("/updateExaminationPoint")
    void updateExaminationPoint(ExaminationPoint examinationPoint);
    @GetMapping("/deleteExaminationPoint/{id}")
    void deleteExaminationPoint(@PathVariable("id") String id);
    @PostMapping("/deleteExaminationPointByCondition")
    void deleteExaminationPointByCondition(ExaminationPoint examinationPoint);
    @PostMapping("/findKnowledgePointListByExamPoint")
    public List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/findKnowledgePointCountByExamPoint")
    public long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge);
}
