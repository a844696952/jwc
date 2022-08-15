package com.yice.edu.cn.jy.dao.knowledge;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.ibatis.annotations.Mapper;

import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyKnowledgeDao {
    List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge);

    JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge);

    long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge);

    JyKnowledge findJyKnowledgeById(String id);

    List<JwClasses> findJwClassesById(List<String> classIds);

    void saveJyKnowledge(JyKnowledge jyKnowledge);

    void updateJyKnowledge(JyKnowledge jyKnowledge);

    void deleteJyKnowledge(String id);

    void deleteJyKnowledgeByCondition(JyKnowledge jyKnowledge);

    void batchSaveJyKnowledge(List<JyKnowledge> jyKnowledges);
    
    void updateJyKnowledgeByParentId(JyKnowledge jyKnowledge);
}
