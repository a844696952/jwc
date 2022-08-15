package com.yice.edu.cn.osp.service.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.osp.feignClient.jy.resourceCenter.ResourceCenterConditionFeign;
import com.yice.edu.cn.osp.feignClient.jy.resourceCenter.ResourceCenterTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceCenterTypeService {
    @Autowired
    private ResourceCenterTypeFeign resourceCenterTypeFeign;
    @Autowired
    private ResourceCenterConditionFeign resourceCenterConditionFeign;

    public ResourceCenterType findResourceCenterTypeById(String id) {
        return resourceCenterTypeFeign.findResourceCenterTypeById(id);
    }

    public ResourceCenterType saveResourceCenterType(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.saveResourceCenterType(resourceCenterType);
    }

    public List<ResourceCenterType> findResourceCenterTypeListByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.findResourceCenterTypeListByCondition(resourceCenterType);
    }

    public ResourceCenterType findOneResourceCenterTypeByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.findOneResourceCenterTypeByCondition(resourceCenterType);
    }

    public long findResourceCenterTypeCountByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.findResourceCenterTypeCountByCondition(resourceCenterType);
    }

    public void updateResourceCenterType(ResourceCenterType resourceCenterType) {
        resourceCenterTypeFeign.updateResourceCenterType(resourceCenterType);
    }

    public ResourceCenterType deleteResourceCenterType(String id) {
        return resourceCenterTypeFeign.deleteResourceCenterType(id);
    }

    public void deleteResourceCenterTypeByCondition(ResourceCenterType resourceCenterType) {
        resourceCenterTypeFeign.deleteResourceCenterTypeByCondition(resourceCenterType);
    }

    public ResourceCenterType saveResourceCenterType4Like(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.saveResourceCenterType4Like(resourceCenterType);
    }

    public List<ResourceCenterType> findResourceCenterTypeListByCondition4Like(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeFeign.findResourceCenterTypeListByCondition4Like(resourceCenterType);
    }

    public void updateResourceCenterType4Like(ResourceCenterType resourceCenterType) {
        resourceCenterTypeFeign.updateResourceCenterType4Like(resourceCenterType);
    }

    public ResourceCenterType deleteResourceCenterType4Like(String id) {
        return resourceCenterTypeFeign.deleteResourceCenterType4Like(id);
    }

    public  List<ResourceCenterType> findResourceCenterTypeListByConditionMap(ResourceCenterType resourceCenterType) {
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortField("sort");
        pager.setSortOrder("desc");
        resourceCenterType.setPager(pager);
        List<ResourceCenterType> list = resourceCenterTypeFeign.findResourceCenterTypeListByCondition(resourceCenterType);
        if(list.isEmpty()){
            Collections.emptyList();
        }
        ResourceCenterCondition rc = new ResourceCenterCondition();
        rc.setSchoolId(resourceCenterType.getSchoolId());
        List<ResourceCenterCondition> rcs = resourceCenterConditionFeign.findResourceCenterConditionListByCondition(rc);
        list.stream().forEach(s->{
            List<ResourceCenterCondition> collect = rcs.stream().filter(rs -> s.getId().equals(rs.getTypeId())).collect(Collectors.toList());
            if(!collect.isEmpty()){
                Collections.sort(collect,(s1,s2)->{
                    return -Integer.compare(s1.getSort(),s2.getSort());
                });
            }
            s.setChild(collect);
        });
        return list;
    }
}
