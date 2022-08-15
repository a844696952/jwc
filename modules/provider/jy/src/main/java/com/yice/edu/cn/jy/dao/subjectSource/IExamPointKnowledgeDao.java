package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;

@Mapper
public interface IExamPointKnowledgeDao {
    List<ExamPointKnowledge> findExamPointKnowledgeListByCondition(ExamPointKnowledge examPointKnowledge);

    ExamPointKnowledge findOneExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge);

    long findExamPointKnowledgeCountByCondition(ExamPointKnowledge examPointKnowledge);

    ExamPointKnowledge findExamPointKnowledgeById(@Param("id") String id);

    void saveExamPointKnowledge(ExamPointKnowledge examPointKnowledge);

    void updateExamPointKnowledge(ExamPointKnowledge examPointKnowledge);

    void deleteExamPointKnowledge(@Param("id") String id);

    void deleteExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge);

    void batchSaveExamPointKnowledge(List<ExamPointKnowledge> examPointKnowledges);
}
