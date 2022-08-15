package com.yice.edu.cn.jy.service.subjectSource;


import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jy.dao.subjectSource.IMaterialItemDao;
import com.yice.edu.cn.jy.source21.service.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialItemService {
    @Autowired
    private IMaterialItemDao materialItemDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private Source21Service source21Service;

    @Transactional(readOnly = true)
    public MaterialItem findMaterialItemById(String id) {
        return materialItemDao.findMaterialItemById(id);
    }
    @Transactional
    public void saveMaterialItem(MaterialItem materialItem) {
      	//修改父节点为非叶子节点
    	if(!"-1".equals(materialItem.getParentId())) {
    		MaterialItem parentModel = new MaterialItem();
    		parentModel.setId(materialItem.getParentId());
    		parentModel.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_NOT_LEAF));
    		materialItemDao.updateMaterialItem(parentModel);
    	}
        materialItem.setId(sequenceId.nextId()); 
        materialItem.setPath((materialItem.getPath()==null?"":materialItem.getPath())+materialItem.getId()+",");
        materialItemDao.saveMaterialItem(materialItem);
    }
    @Transactional(readOnly = true)
    public List<MaterialItem> findMaterialItemListByCondition(MaterialItem materialItem) {
        return materialItemDao.findMaterialItemListByCondition(materialItem);
    }
    @Transactional(readOnly = true)
    public MaterialItem findOneMaterialItemByCondition(MaterialItem materialItem) {
        return materialItemDao.findOneMaterialItemByCondition(materialItem);
    }
    @Transactional(readOnly = true)
    public long findMaterialItemCountByCondition(MaterialItem materialItem) {
        return materialItemDao.findMaterialItemCountByCondition(materialItem);
    }
    @Transactional
    public void updateMaterialItem(MaterialItem materialItem) {
        materialItemDao.updateMaterialItem(materialItem);
    }
    @Transactional
    public void deleteMaterialItem(String id) {
    	MaterialItem materialItem = materialItemDao.findMaterialItemById(id);
    	
    	MaterialItem queryMaterialItem = new MaterialItem();
    	queryMaterialItem.setParentId(materialItem.getParentId());
    	long count = materialItemDao.findMaterialItemCountByCondition(queryMaterialItem);
    	if(count<=1) {
    		 //修改父节点为叶子节点
    		MaterialItem updateMaterialItem = new MaterialItem();
    		updateMaterialItem.setId(materialItem.getParentId());
    		updateMaterialItem.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
    		materialItemDao.updateMaterialItem(updateMaterialItem);
    	}
    	
        materialItemDao.deleteMaterialItem(id);
    }
    @Transactional
    public void deleteMaterialItemByCondition(MaterialItem materialItem) {
        materialItemDao.deleteMaterialItemByCondition(materialItem);
    }
    @Transactional
    public void batchSaveMaterialItem(List<MaterialItem> materialItems){
        if(CollectionUtil.isNotEmpty(materialItems)){
            //先删除原先的节点
            MaterialItem del = new MaterialItem();
            del.setMaterialId(materialItems.get(0).getMaterialId());
            materialItemDao.deleteMaterialItemByCondition(del);
            //保存数据
            materialItems.forEach(materialItem -> materialItem.setId(Optional.ofNullable(materialItem.getId()).orElse(sequenceId.nextId())));
            materialItemDao.batchSaveMaterialItem(materialItems);
        }
    }
    
    @Transactional(readOnly = true)
    public List<KnowledgePoint> findKnowledgePointListByItem(MaterialItemKnowledge materialItemKnowledge){
    	return materialItemDao.findKnowledgePointListByItem(materialItemKnowledge);
    }

    @Transactional(readOnly = true)
    public long findKnowledgePointCountByItem(MaterialItemKnowledge materialItemKnowledge) {
    	return materialItemDao.findKnowledgePointCountByItem(materialItemKnowledge);
    }

    @Transactional(readOnly = true)
    public List<MaterialItem> findMaterialItemTree(MaterialItem materialItem){
        return materialItemDao.findMaterialItemTree(materialItem);
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
            List<MaterialItem> treeData = ObjectKit.buildTree(materialItemDao.findMaterialItemListByCondition(materialItem), "-1");
            return new ResponseJson(treeData);
        }
    }

    public Boolean checkMaterialItemIsNull(String materialId) {
        int count = materialItemDao.checkMaterialItemIsNull(materialId);
        if(count == 0){
            return false;
        }else {
            return true;
        }
    }
}
