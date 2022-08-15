package com.yice.edu.cn.jy.service.resourceCenter;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterTypeConditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceCenterTypeConditionService {
    @Autowired
    private IResourceCenterTypeConditionDao resourceCenterTypeConditionDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ResourceCenterTypeCondition findResourceCenterTypeConditionById(String id) {
        return resourceCenterTypeConditionDao.findResourceCenterTypeConditionById(id);
    }
    @Transactional
    public void saveResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        resourceCenterTypeCondition.setId(sequenceId.nextId());
        resourceCenterTypeConditionDao.saveResourceCenterTypeCondition(resourceCenterTypeCondition);
    }
    @Transactional(readOnly = true)
    public List<ResourceCenterTypeCondition> findResourceCenterTypeConditionListByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionDao.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
    }
    @Transactional(readOnly = true)
    public ResourceCenterTypeCondition findOneResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionDao.findOneResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }
    @Transactional(readOnly = true)
    public long findResourceCenterTypeConditionCountByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionDao.findResourceCenterTypeConditionCountByCondition(resourceCenterTypeCondition);
    }
    @Transactional
    public void updateResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        resourceCenterTypeConditionDao.updateResourceCenterTypeCondition(resourceCenterTypeCondition);
    }
    @Transactional
    public void deleteResourceCenterTypeCondition(String id) {
        resourceCenterTypeConditionDao.deleteResourceCenterTypeCondition(id);
    }
    @Transactional
    public void deleteResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        resourceCenterTypeConditionDao.deleteResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }
    @Transactional
    public void batchSaveResourceCenterTypeCondition(List<ResourceCenterTypeCondition> resourceCenterTypeConditions){
        resourceCenterTypeConditions.forEach(resourceCenterTypeCondition -> resourceCenterTypeCondition.setId(sequenceId.nextId()));
        resourceCenterTypeConditionDao.batchSaveResourceCenterTypeCondition(resourceCenterTypeConditions);
    }

}
