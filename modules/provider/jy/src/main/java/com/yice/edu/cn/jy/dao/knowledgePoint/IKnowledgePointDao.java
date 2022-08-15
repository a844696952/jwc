package com.yice.edu.cn.jy.dao.knowledgePoint;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IKnowledgePointDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<KnowledgePoint> findKnowledgePointListByCondition(KnowledgePoint knowledgePoint);

    long findKnowledgePointCountByCondition(KnowledgePoint knowledgePoint);

    KnowledgePoint findOneKnowledgePointByCondition(KnowledgePoint knowledgePoint);

    KnowledgePoint findKnowledgePointById(@Param("id") String id);

    void saveKnowledgePoint(KnowledgePoint knowledgePoint);

    void updateKnowledgePoint(KnowledgePoint knowledgePoint);

    void updateKnowledgePointForAll(KnowledgePoint knowledgePoint);

    void deleteKnowledgePoint(@Param("id") String id);

    void deleteKnowledgePointByCondition(KnowledgePoint knowledgePoint);

    void batchSaveKnowledgePoint(List<KnowledgePoint> knowledgePoints);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void batchSetKnowledgeTopicsCount(@Param("ids")String[] ids,@Param("count")int count);

    void batchDeleteKnowledgePoint(@Param("ids")String[] ids);

    List<String> findKnowledgePointIdByChapterId(@Param("chapterId") String chapterId);

    void updateKnowledgePointChildNum(@Param("id") String id,@Param("num") int i);
}
