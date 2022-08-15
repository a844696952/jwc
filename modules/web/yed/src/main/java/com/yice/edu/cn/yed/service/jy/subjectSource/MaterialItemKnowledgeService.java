package com.yice.edu.cn.yed.service.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.yed.feignClient.jy.subjectSource.MaterialItemKnowledgeFeign;

@Service
public class MaterialItemKnowledgeService {
    @Autowired
    private MaterialItemKnowledgeFeign materialItemKnowledgeFeign;

    public MaterialItemKnowledge findMaterialItemKnowledgeById(String id) {
        return materialItemKnowledgeFeign.findMaterialItemKnowledgeById(id);
    }

    public MaterialItemKnowledge saveMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeFeign.saveMaterialItemKnowledge(materialItemKnowledge);
    }

    public List<MaterialItemKnowledge> findMaterialItemKnowledgeListByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeFeign.findMaterialItemKnowledgeListByCondition(materialItemKnowledge);
    }

    public MaterialItemKnowledge findOneMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeFeign.findOneMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }

    public long findMaterialItemKnowledgeCountByCondition(MaterialItemKnowledge materialItemKnowledge) {
        return materialItemKnowledgeFeign.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
    }

    public void updateMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge) {
        materialItemKnowledgeFeign.updateMaterialItemKnowledge(materialItemKnowledge);
    }

    public void deleteMaterialItemKnowledge(String id) {
        materialItemKnowledgeFeign.deleteMaterialItemKnowledge(id);
    }

    public void deleteMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge) {
        materialItemKnowledgeFeign.deleteMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }
}
