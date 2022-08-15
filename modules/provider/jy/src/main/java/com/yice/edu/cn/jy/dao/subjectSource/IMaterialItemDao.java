package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;

@Mapper
public interface IMaterialItemDao {
    List<MaterialItem> findMaterialItemListByCondition(MaterialItem materialItem);

    MaterialItem findOneMaterialItemByCondition(MaterialItem materialItem);

    long findMaterialItemCountByCondition(MaterialItem materialItem);

    MaterialItem findMaterialItemById(@Param("id") String id);

    void saveMaterialItem(MaterialItem materialItem);

    void updateMaterialItem(MaterialItem materialItem);

    void deleteMaterialItem(@Param("id") String id);

    void deleteMaterialItemByCondition(MaterialItem materialItem);

    void batchSaveMaterialItem(List<MaterialItem> materialItems);
    
    List<KnowledgePoint> findKnowledgePointListByItem(MaterialItemKnowledge materialItem);

    long findKnowledgePointCountByItem(MaterialItemKnowledge materialItem);

    List<MaterialItem> findMaterialItemTree(MaterialItem materialItem);

    int checkMaterialItemIsNull(String materialId);
}
