package com.yice.edu.cn.bmp.service.subjectSource;


import com.yice.edu.cn.bmp.feignClient.subjectSource.MaterialItemFeign;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialItemService {
    @Autowired
    private MaterialItemFeign materialItemFeign;

    public MaterialItem findMaterialItemById(String id) {
        return materialItemFeign.findMaterialItemById(id);
    }

    public MaterialItem saveMaterialItem(MaterialItem materialItem) {
        return materialItemFeign.saveMaterialItem(materialItem);
    }

    public List<MaterialItem> findMaterialItemListByCondition(MaterialItem materialItem) {
        return materialItemFeign.findMaterialItemListByCondition(materialItem);
    }

    public MaterialItem findOneMaterialItemByCondition(MaterialItem materialItem) {
        return materialItemFeign.findOneMaterialItemByCondition(materialItem);
    }

    public long findMaterialItemCountByCondition(MaterialItem materialItem) {
        return materialItemFeign.findMaterialItemCountByCondition(materialItem);
    }

    public void updateMaterialItem(MaterialItem materialItem) {
        materialItemFeign.updateMaterialItem(materialItem);
    }

    public void deleteMaterialItem(String id) {
        materialItemFeign.deleteMaterialItem(id);
    }

    public void deleteMaterialItemByCondition(MaterialItem materialItem) {
        materialItemFeign.deleteMaterialItemByCondition(materialItem);
    }
    public List<KnowledgePoint> findKnowledgePointListByItem(MaterialItemKnowledge materialItemKnowledge){
    	return materialItemFeign.findKnowledgePointListByItem(materialItemKnowledge);
    }
    public long findKnowledgePointCountByItem(MaterialItemKnowledge materialItemKnowledge) {
    	return materialItemFeign.findKnowledgePointCountByItem(materialItemKnowledge);
    }

}
