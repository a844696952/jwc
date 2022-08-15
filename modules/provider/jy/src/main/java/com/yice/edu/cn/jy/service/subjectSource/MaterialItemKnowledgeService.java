package com.yice.edu.cn.jy.service.subjectSource;


import java.util.List;
import java.util.Optional;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.jy.dao.subjectSource.IMaterialItemKnowledgeDao;

@Service
public class MaterialItemKnowledgeService {
    @Autowired
    private IMaterialItemKnowledgeDao materialItemKnowledgeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public MaterialItemKnowledge findMaterialItemKnowledgeById(String id) {
        return materialItemKnowledgeDao.findMaterialItemKnowledgeById(id);
    }
    @Transactional
    public void saveMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge) {
        materialItemKnowledge.setId(sequenceId.nextId());
        materialItemKnowledgeDao.saveMaterialItemKnowledge(materialItemKnowledge);
    }
    @Transactional(readOnly = true)
    public List<MaterialItemKnowledge> findMaterialItemKnowledgeListByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeDao.findMaterialItemKnowledgeListByCondition(materialItemKnowledge);
    }
    @Transactional(readOnly = true)
    public MaterialItemKnowledge findOneMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeDao.findOneMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }
    @Transactional(readOnly = true)
    public long findMaterialItemKnowledgeCountByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeDao.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
    }
    @Transactional
    public void updateMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge) {
        materialItemKnowledgeDao.updateMaterialItemKnowledge(materialItemKnowledge);
    }
    @Transactional
    public void deleteMaterialItemKnowledge(String id) {
        materialItemKnowledgeDao.deleteMaterialItemKnowledge(id);
    }
    @Transactional
    public void deleteMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge) {
        materialItemKnowledgeDao.deleteMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }
    @Transactional
    public void batchSaveMaterialItemKnowledge(List<MaterialItemKnowledge> materialItemKnowledges){
        if(CollectionUtil.isNotEmpty(materialItemKnowledges)){
            materialItemKnowledges.forEach(materialItemKnowledge -> materialItemKnowledge.setId(Optional.ofNullable(materialItemKnowledge.getId()).orElse(sequenceId.nextId())));
            //先删除
            materialItemKnowledgeDao.batchDeleteMaterialItemKnowledge(materialItemKnowledges.stream().map(MaterialItemKnowledge::getMaterialItemId).toArray(String[]::new));
            materialItemKnowledgeDao.batchSaveMaterialItemKnowledge(materialItemKnowledges);
        }
    }
    @Transactional
    public void batchDeleteMaterialItemKnowledge(String[] ids) {
        materialItemKnowledgeDao.batchDeleteMaterialItemKnowledge(ids);
    }
}
