package com.yice.edu.cn.jy.service.subjectSource;


import java.util.List;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.jy.service.topics.TopicsService;
import com.yice.edu.cn.jy.source21.service.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.jy.dao.subjectSource.ISubjectMaterialDao;

@Service
public class SubjectMaterialService {
    @Autowired
    private ISubjectMaterialDao subjectMaterialDao;
    @Autowired
    private Source21Service source21Service;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public SubjectMaterial findSubjectMaterialById(String id) {
        return subjectMaterialDao.findSubjectMaterialById(id);
    }
    @Transactional
    public void saveSubjectMaterial(SubjectMaterial subjectMaterial) {
      	//修改父节点为非叶子节点
    	if(!"-1".equals(subjectMaterial.getParentId())) {
    		SubjectMaterial parentModel = new SubjectMaterial();
    		parentModel.setId(subjectMaterial.getParentId());
    		parentModel.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_NOT_LEAF));
    		subjectMaterialDao.updateSubjectMaterial(parentModel);
    	}
        subjectMaterial.setId(sequenceId.nextId());
    	subjectMaterial.setPath((subjectMaterial.getPath()==null?"":subjectMaterial.getPath())+subjectMaterial.getId()+";");
        subjectMaterialDao.saveSubjectMaterial(subjectMaterial);
    }
    @Transactional(readOnly = true)
    public List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialDao.findSubjectMaterialListByCondition(subjectMaterial);
    }
    @Transactional(readOnly = true)
    public SubjectMaterial findOneSubjectMaterialByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialDao.findOneSubjectMaterialByCondition(subjectMaterial);
    }
    @Transactional(readOnly = true)
    public long findSubjectMaterialCountByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialDao.findSubjectMaterialCountByCondition(subjectMaterial);
    }
    @Transactional
    public void updateSubjectMaterial(SubjectMaterial subjectMaterial) {
        subjectMaterialDao.updateSubjectMaterial(subjectMaterial);
    }
    @Transactional
    public void deleteSubjectMaterial(String id) {
    	SubjectMaterial subjectMaterial = subjectMaterialDao.findSubjectMaterialById(id);
    	
    	SubjectMaterial querySubjectMaterial = new SubjectMaterial();
    	querySubjectMaterial.setParentId(subjectMaterial.getParentId());
    	long count = subjectMaterialDao.findSubjectMaterialCountByCondition(querySubjectMaterial);
    	if(count<=1) {
    		 //修改父节点为叶子节点
    		SubjectMaterial updateSubjectMaterial = new SubjectMaterial();
    		updateSubjectMaterial.setId(subjectMaterial.getParentId());
    		updateSubjectMaterial.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
    		subjectMaterialDao.updateSubjectMaterial(updateSubjectMaterial);
    		
    	}
        subjectMaterialDao.deleteSubjectMaterial(id);
    }
    @Transactional
    public void deleteSubjectMaterialByCondition(SubjectMaterial subjectMaterial) {
        subjectMaterialDao.deleteSubjectMaterialByCondition(subjectMaterial);
    }
    @Transactional
    public void batchSaveSubjectMaterial(List<SubjectMaterial> subjectMaterials){
        subjectMaterials.forEach(subjectMaterial -> subjectMaterial.setId(sequenceId.nextId()));
        subjectMaterialDao.batchSaveSubjectMaterial(subjectMaterials);
    }

    /**
     * 平台通过科目查询第一级节点
     * 1、我们平台是教材对应的年级
     * 2、21世纪是教材对应的版本
     * @param resourceVo
     * @return
     */
    public ResponseJson findSubjectMaterialBySubject(ResourceVo resourceVo) {
        if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(resourceVo.getPlatform())){
            List<Version> versions = source21Service.getVersionsBySubject(resourceVo.getStage(), TopicsService.getSubjectToMap(resourceVo.getTempId()));
            return new ResponseJson(versions.stream().map(version -> {
                SubjectMaterial sm = new SubjectMaterial();
                sm.setId(String.valueOf(version.getVersionId()));
                sm.setName(version.getVersionName());
                return sm;
            }).collect(Collectors.toList()));
        }else{
            SubjectMaterial subjectMaterial = new SubjectMaterial();
            subjectMaterial.setDdId(resourceVo.getTempId());
            subjectMaterial = subjectMaterialDao.findOneSubjectMaterialByCondition(subjectMaterial);
            if(subjectMaterial!=null){
                SubjectMaterial temp = new SubjectMaterial();
                temp.setParentId(subjectMaterial.getId());
                temp.setPager(new Pager().setPaging(false).setIncludes("id","name").setSortField("sort").setSortOrder(Pager.ASC));
                return new ResponseJson(subjectMaterialDao.findSubjectMaterialListByCondition(temp));
            }else{
                return new ResponseJson();
            }
        }
    }
}
