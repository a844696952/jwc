package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.jy.dao.titleQuota.IResourcePlatformDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourcePlatformService {
    @Autowired
    private IResourcePlatformDao resourcePlatformDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ResourcePlatform findResourcePlatformById(String id) {
        return resourcePlatformDao.findResourcePlatformById(id);
    }
    @Transactional
    public void saveResourcePlatform(ResourcePlatform resourcePlatform) {
        resourcePlatform.setId(sequenceId.nextId());
        resourcePlatformDao.saveResourcePlatform(resourcePlatform);
    }
    @Transactional(readOnly = true)
    public List<ResourcePlatform> findResourcePlatformListByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformDao.findResourcePlatformListByCondition(resourcePlatform);
    }
    @Transactional(readOnly = true)
    public ResourcePlatform findOneResourcePlatformByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformDao.findOneResourcePlatformByCondition(resourcePlatform);
    }
    @Transactional(readOnly = true)
    public long findResourcePlatformCountByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformDao.findResourcePlatformCountByCondition(resourcePlatform);
    }
    @Transactional
    public void updateResourcePlatform(ResourcePlatform resourcePlatform) {
        resourcePlatformDao.updateResourcePlatform(resourcePlatform);
    }
    @Transactional
    public void updateResourcePlatformForAll(ResourcePlatform resourcePlatform) {
        if(resourcePlatform.getResourceName()!=null){
            String name = resourcePlatform.getResourceName().trim();
            resourcePlatform.setResourceName(name);
            ResourcePlatform temp = new ResourcePlatform();
            temp.setId(resourcePlatform.getId());
            temp.setResourceName(name);
            long one = resourcePlatformDao.findCount(temp);
            if(one>0){
                resourcePlatform.setCode("204");
                resourcePlatform.setMsg("题库名称不可以重复");
                return;
            }
        }


        resourcePlatformDao.updateResourcePlatformForAll(resourcePlatform);
    }
    @Transactional
    public void deleteResourcePlatform(String id) {
        resourcePlatformDao.deleteResourcePlatform(id);
    }
    @Transactional
    public void deleteResourcePlatformByCondition(ResourcePlatform resourcePlatform) {
        resourcePlatformDao.deleteResourcePlatformByCondition(resourcePlatform);
    }
    @Transactional
    public void batchSaveResourcePlatform(List<ResourcePlatform> resourcePlatforms){
        //resourcePlatforms.forEach(resourcePlatform -> resourcePlatform.setId(sequenceId.nextId()));
        resourcePlatformDao.batchSaveResourcePlatform(resourcePlatforms);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
