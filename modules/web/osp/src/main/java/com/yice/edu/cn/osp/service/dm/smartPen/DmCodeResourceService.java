package com.yice.edu.cn.osp.service.dm.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import com.yice.edu.cn.osp.feignClient.dm.smartPen.DmCodeResourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmCodeResourceService {
    @Autowired
    private DmCodeResourceFeign dmCodeResourceFeign;

    public DmCodeResource findDmCodeResourceById(String id) {
        return dmCodeResourceFeign.findDmCodeResourceById(id);
    }

    public DmCodeResource saveDmCodeResource(DmCodeResource dmCodeResource) {
        return dmCodeResourceFeign.saveDmCodeResource(dmCodeResource);
    }

    public List<DmCodeResource> batchSaveDmCodeResource(List<DmCodeResource> dmCodeResources) {
        return dmCodeResourceFeign.batchSaveDmCodeResource(dmCodeResources);
    }

    public List<DmCodeResource> findDmCodeResourceListByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceFeign.findDmCodeResourceListByCondition(dmCodeResource);
    }

    public DmCodeResource findOneDmCodeResourceByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceFeign.findOneDmCodeResourceByCondition(dmCodeResource);
    }

    public long findDmCodeResourceCountByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceFeign.findDmCodeResourceCountByCondition(dmCodeResource);
    }

    public void updateDmCodeResource(DmCodeResource dmCodeResource) {
        dmCodeResourceFeign.updateDmCodeResource(dmCodeResource);
    }

    public void deleteDmCodeResource(String id) {
        dmCodeResourceFeign.deleteDmCodeResource(id);
    }

    public void deleteDmCodeResourceByCondition(DmCodeResource dmCodeResource) {
        dmCodeResourceFeign.deleteDmCodeResourceByCondition(dmCodeResource);
    }
}
