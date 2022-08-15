package com.yice.edu.cn.yed.service.jy.subjectSource;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.yed.feignClient.jy.subjectSource.MaterialItemFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MaterialItemService {
    @Autowired
    private MaterialItemFeign materialItemFeign;

    @CreateCache(name = Constant.Redis.OSP_CHAPTER_MENU, expire = 2, timeUnit= TimeUnit.HOURS)
    private Cache<String, List<MaterialItem>> materialItemCache;

    public MaterialItem findMaterialItemById(String id) {
        return materialItemFeign.findMaterialItemById(id);
    }

    public MaterialItem saveMaterialItem(MaterialItem materialItem) {
        MaterialItem savedMaterialItem = materialItemFeign.saveMaterialItem(materialItem);
        removeMaterialCache(savedMaterialItem.getMaterialId());
        return savedMaterialItem;
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

        removeMaterialItemCache(materialItem.getId());
    }

    /**
     *osp查询教材使用了缓存，后端更新，需要清空这个缓存
     */
    private void removeMaterialItemCache(String materialItemId) {
        MaterialItem materialItemObj = findMaterialItemById(materialItemId);
        if (Objects.nonNull(materialItemObj) && StringUtils.isNotEmpty(materialItemObj.getMaterialId())) {
            removeMaterialCache(materialItemObj.getMaterialId());
        }
    }

    private void removeMaterialCache(String materialId) {
        materialItemCache.remove(materialId);
    }

    public void deleteMaterialItem(String id) {
        removeMaterialItemCache(id);
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
