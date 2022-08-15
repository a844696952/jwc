package com.yice.edu.cn.osp.service.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.osp.feignClient.jy.resourceCenter.ResourceCenterConditionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceCenterConditionService {
    @Autowired
    private ResourceCenterConditionFeign resourceCenterConditionFeign;

    public ResourceCenterCondition findResourceCenterConditionById(String id) {
        return resourceCenterConditionFeign.findResourceCenterConditionById(id);
    }

    public ResourceCenterCondition saveResourceCenterCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionFeign.saveResourceCenterCondition(resourceCenterCondition);
    }

    public List<ResourceCenterCondition> findResourceCenterConditionListByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionFeign.findResourceCenterConditionListByCondition(resourceCenterCondition);
    }

    public ResourceCenterCondition findOneResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionFeign.findOneResourceCenterConditionByCondition(resourceCenterCondition);
    }

    public long findResourceCenterConditionCountByCondition(ResourceCenterCondition resourceCenterCondition) {
        return resourceCenterConditionFeign.findResourceCenterConditionCountByCondition(resourceCenterCondition);
    }

    public void updateResourceCenterCondition(ResourceCenterCondition resourceCenterCondition) {
        resourceCenterConditionFeign.updateResourceCenterCondition(resourceCenterCondition);
    }

    public void deleteResourceCenterCondition(String id) {
        resourceCenterConditionFeign.deleteResourceCenterCondition(id);
    }

    public ResourceCenterType deleteResourceCenterCondition4Like(ResourceCenterType resourceCenterType) {
        return resourceCenterConditionFeign.deleteResourceCenterCondition4Like(resourceCenterType);
    }
}
