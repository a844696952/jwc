package com.yice.edu.cn.jy.service.resourceCenter;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.common.util.ArrayUtil.ArrayUtil;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterConditionDao;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterTypeConditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResourceCenterConditionService {
    @Autowired
    private IResourceCenterTypeConditionDao resourceCenterTypeConditionDao;
    @Autowired
    private IResourceCenterConditionDao resourceCenterConditionDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ResourceCenterCondition findResourceCenterConditionById(String id) {
        return resourceCenterConditionDao.findResourceCenterConditionById(id);
    }
    @Transactional
    public void saveResourceCenterCondition(ResourceCenterCondition resourceCenterCondition) {
        resourceCenterCondition.setId(sequenceId.nextId());
        resourceCenterConditionDao.saveResourceCenterCondition(resourceCenterCondition);
    }
    @Transactional(readOnly = true)
    public List<ResourceCenterCondition> findResourceCenterConditionListByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionDao.findResourceCenterConditionListByCondition(resourceCenterCondition);
    }
    @Transactional(readOnly = true)
    public ResourceCenterCondition findOneResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionDao.findOneResourceCenterConditionByCondition(resourceCenterCondition);
    }
    @Transactional(readOnly = true)
    public long findResourceCenterConditionCountByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionDao.findResourceCenterConditionCountByCondition(resourceCenterCondition);
    }
    @Transactional
    public void updateResourceCenterCondition(ResourceCenterCondition resourceCenterCondition) {
        resourceCenterConditionDao.updateResourceCenterCondition(resourceCenterCondition);
    }
    @Transactional
    public void deleteResourceCenterCondition(String id) {
        resourceCenterConditionDao.deleteResourceCenterCondition(id);
    }
    @Transactional
    public void deleteResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition) {
        resourceCenterConditionDao.deleteResourceCenterConditionByCondition(resourceCenterCondition);
    }
    @Transactional
    public void batchSaveResourceCenterCondition(List<ResourceCenterCondition> resourceCenterConditions){
        resourceCenterConditions.forEach(resourceCenterCondition -> resourceCenterCondition.setId(sequenceId.nextId()));
        resourceCenterConditionDao.batchSaveResourceCenterCondition(resourceCenterConditions);
    }

    @Transactional
    public ResourceCenterType deleteResourceCenterCondition4Like(ResourceCenterType resourceCenterType) {
        String[] arrId = resourceCenterType.getIds();
        if(arrId.length==0){//没有删除id
            resourceCenterType.setCode("200");
            return resourceCenterType;
        }
        int count = 0;
        for(String id :arrId){
            ResourceCenterTypeCondition s1 = new ResourceCenterTypeCondition();
            s1.setConditionId(id);
            long num = resourceCenterTypeConditionDao.findResourceCenterTypeConditionCountByCondition(s1);
            if(num>0){
                count++;
            }
        }
        if(count>0){
            resourceCenterType.setCode("204");
            resourceCenterType.setMsg("当前条件下有视频资源，请取消关联后删除");
            return resourceCenterType;
        }
        for (String id:arrId){
            resourceCenterConditionDao.deleteResourceCenterCondition(id);
        }
        resourceCenterType.setCode("200");
        resourceCenterType.setMsg("删除成功");
        return  resourceCenterType;
    }
}
