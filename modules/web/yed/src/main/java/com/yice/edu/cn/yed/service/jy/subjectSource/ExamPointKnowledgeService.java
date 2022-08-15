package com.yice.edu.cn.yed.service.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.yed.feignClient.jy.subjectSource.ExamPointKnowledgeFeign;

@Service
public class ExamPointKnowledgeService {
    @Autowired
    private ExamPointKnowledgeFeign examPointKnowledgeFeign;

    public ExamPointKnowledge findExamPointKnowledgeById(String id) {
        return examPointKnowledgeFeign.findExamPointKnowledgeById(id);
    }

    public ExamPointKnowledge saveExamPointKnowledge(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeFeign.saveExamPointKnowledge(examPointKnowledge);
    }

    public List<ExamPointKnowledge> findExamPointKnowledgeListByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeFeign.findExamPointKnowledgeListByCondition(examPointKnowledge);
    }

    public ExamPointKnowledge findOneExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeFeign.findOneExamPointKnowledgeByCondition(examPointKnowledge);
    }

    public long findExamPointKnowledgeCountByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeFeign.findExamPointKnowledgeCountByCondition(examPointKnowledge);
    }

    public void updateExamPointKnowledge(ExamPointKnowledge examPointKnowledge) {
        examPointKnowledgeFeign.updateExamPointKnowledge(examPointKnowledge);
    }

    public void deleteExamPointKnowledge(String id) {
        examPointKnowledgeFeign.deleteExamPointKnowledge(id);
    }

    public void deleteExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge) {
        examPointKnowledgeFeign.deleteExamPointKnowledgeByCondition(examPointKnowledge);
    }
}
