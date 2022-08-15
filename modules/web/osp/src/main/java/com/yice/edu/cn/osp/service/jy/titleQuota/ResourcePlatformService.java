package com.yice.edu.cn.osp.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.osp.feignClient.jy.titleQuota.ResourcePlatformFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcePlatformService {
    @Autowired
    private ResourcePlatformFeign resourcePlatformFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ResourcePlatform findResourcePlatformById(String id) {
        return resourcePlatformFeign.findResourcePlatformById(id);
    }
    public void batchSaveResourcePlatform(List<ResourcePlatform> resourcePlatforms){
        resourcePlatformFeign.batchSaveResourcePlatform(resourcePlatforms);
    }

    public List<ResourcePlatform> findResourcePlatformListByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformFeign.findResourcePlatformListByCondition(resourcePlatform);
    }

    public ResourcePlatform findOneResourcePlatformByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformFeign.findOneResourcePlatformByCondition(resourcePlatform);
    }

    public long findResourcePlatformCountByCondition(ResourcePlatform resourcePlatform) {
        return resourcePlatformFeign.findResourcePlatformCountByCondition(resourcePlatform);
    }

    public void updateResourcePlatform(ResourcePlatform resourcePlatform) {
        resourcePlatformFeign.updateResourcePlatform(resourcePlatform);
    }

    public void updateResourcePlatformForAll(ResourcePlatform resourcePlatform) {
        resourcePlatformFeign.updateResourcePlatformForAll(resourcePlatform);
    }

    public void deleteResourcePlatform(String id) {
        resourcePlatformFeign.deleteResourcePlatform(id);
    }

    public void deleteResourcePlatformByCondition(ResourcePlatform resourcePlatform) {
        resourcePlatformFeign.deleteResourcePlatformByCondition(resourcePlatform);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public ResourcePlatform saveResourcePlatform(ResourcePlatform resourcePlatform) {
        ResourcePlatform one = findOneResourcePlatformByCondition(resourcePlatform);
        if(one!=null){
            one.setCode("204");
            one.setMsg("题库资源平台已存在,请勿重复添加");
            return one;
        }
        return resourcePlatformFeign.saveResourcePlatform(resourcePlatform);
    }
}
