package com.yice.edu.cn.osp.service.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.osp.feignClient.jy.resourceCenter.ResourceCenterTypeConditionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceCenterTypeConditionService {
    @Autowired
    private ResourceCenterTypeConditionFeign resourceCenterTypeConditionFeign;

    public ResourceCenterTypeCondition findResourceCenterTypeConditionById(String id) {
        return resourceCenterTypeConditionFeign.findResourceCenterTypeConditionById(id);
    }

    public ResourceCenterTypeCondition saveResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionFeign.saveResourceCenterTypeCondition(resourceCenterTypeCondition);
    }

    public List<ResourceCenterTypeCondition> findResourceCenterTypeConditionListByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionFeign.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
    }

    public ResourceCenterTypeCondition findOneResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionFeign.findOneResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }

    public long findResourceCenterTypeConditionCountByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        return resourceCenterTypeConditionFeign.findResourceCenterTypeConditionCountByCondition(resourceCenterTypeCondition);
    }

    public void updateResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        resourceCenterTypeConditionFeign.updateResourceCenterTypeCondition(resourceCenterTypeCondition);
    }

    public void deleteResourceCenterTypeCondition(String id) {
        resourceCenterTypeConditionFeign.deleteResourceCenterTypeCondition(id);
    }

    public void deleteResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition) {
        resourceCenterTypeConditionFeign.deleteResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }
}
