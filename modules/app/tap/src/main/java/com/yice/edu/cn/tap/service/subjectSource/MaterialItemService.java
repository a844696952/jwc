package com.yice.edu.cn.tap.service.subjectSource;


import java.util.List;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.tap.service.source21.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.tap.feignClient.subjectSource.MaterialItemFeign;

@Service
public class MaterialItemService {
    @Autowired
    private MaterialItemFeign materialItemFeign;
    @Autowired
    private Source21Service source21Service;

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
    /**
     * 查询教材对应的章节树
     * @param resourceVo
     * @return
     */
    public ResponseJson findChapterTree(ResourceVo resourceVo) {
        if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(resourceVo.getPlatform())){
            List<Chapter> chapters = source21Service.getChaptersByBook(resourceVo.getTempId());
            return new ResponseJson(chapters);
        }else{
            MaterialItem materialItem = new MaterialItem();
            materialItem.setMaterialId(resourceVo.getTempId());
            materialItem.setPager(new Pager().setPaging(false).setSortField("sort").setSortOrder(Pager.DESC));
            List<MaterialItem> treeData = ObjectKit.buildTree(materialItemFeign.findMaterialItemListByCondition(materialItem), "-1");
            return new ResponseJson(treeData);
        }
    }

}
