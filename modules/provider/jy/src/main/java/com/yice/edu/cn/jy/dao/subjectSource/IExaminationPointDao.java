package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;

@Mapper
public interface IExaminationPointDao {
    List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint);

    ExaminationPoint findOneExaminationPointByCondition(ExaminationPoint examinationPoint);

    long findExaminationPointCountByCondition(ExaminationPoint examinationPoint);

    ExaminationPoint findExaminationPointById(@Param("id") String id);

    void saveExaminationPoint(ExaminationPoint examinationPoint);

    void updateExaminationPoint(ExaminationPoint examinationPoint);

    void deleteExaminationPoint(@Param("id") String id);

    void deleteExaminationPointByCondition(ExaminationPoint examinationPoint);

    void batchSaveExaminationPoint(List<ExaminationPoint> examinationPoints);
    
    List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge);

    long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge);
}
