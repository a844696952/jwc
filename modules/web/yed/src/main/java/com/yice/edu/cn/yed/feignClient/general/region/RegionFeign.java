package com.yice.edu.cn.yed.feignClient.general.region;

import com.yice.edu.cn.common.pojo.general.region.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "regionFeign2",path = "/region")
public interface RegionFeign {
    @GetMapping("/findRegionById/{id}")
    Region findRegionById(@PathVariable("id") String id);
    @PostMapping("/saveRegion")
    Region saveRegion(Region region);
    @PostMapping("/findRegionListByCondition")
    List<Region> findRegionListByCondition(Region region);
    @PostMapping("/findRegionCountByCondition")
    long findRegionCountByCondition(Region region);
    @PostMapping("/updateRegion")
    void updateRegion(Region region);
    @GetMapping("/deleteRegion/{id}")
    void deleteRegion(@PathVariable("id") String id);
    @GetMapping("/findRegionForCascade/{ids}")
    List<Region> findRegionForCascade(@PathVariable("ids") String ids);
}
