package com.yice.edu.cn.osp.service.region;

import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.osp.feignClient.region.RegionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionFeign regionFeign;

    public Region findRegionById(String id) {
        return regionFeign.findRegionById(id);
    }

    public Region saveRegion(Region region) {
        return regionFeign.saveRegion(region);
    }

    public List<Region> findRegionListByCondition(Region region) {
        return regionFeign.findRegionListByCondition(region);
    }

    public long findRegionCountByCondition(Region region) {
        return regionFeign.findRegionCountByCondition(region);
    }

    public void updateRegion(Region region) {
        regionFeign.updateRegion(region);
    }

    public void deleteRegion(String id) {
        regionFeign.deleteRegion(id);
    }

    public List<Region> findRegionForCascade(String ids) {
        return regionFeign.findRegionForCascade(ids);
    }
}
