package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;

@Mapper
public interface IMaterialItemKnowledgeDao {
    List<MaterialItemKnowledge> findMaterialItemKnowledgeListByCondition(MaterialItemKnowledge materialItemKnowledge);

    MaterialItemKnowledge findOneMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge);

    long findMaterialItemKnowledgeCountByCondition(MaterialItemKnowledge materialItemKnowledge);

    MaterialItemKnowledge findMaterialItemKnowledgeById(@Param("id") String id);

    void saveMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge);

    void updateMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge);

    void deleteMaterialItemKnowledge(@Param("id") String id);

    void deleteMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge);

    void batchSaveMaterialItemKnowledge(List<MaterialItemKnowledge> materialItemKnowledges);

    void batchDeleteMaterialItemKnowledge(@Param("ids")String[] ids);
}
