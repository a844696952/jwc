package com.yice.edu.cn.osp.feignClient.dm.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm", contextId = "dmCodeResourceFeign", path = "/dmCodeResource")
public interface DmCodeResourceFeign {
    @GetMapping("/findDmCodeResourceById/{id}")
    DmCodeResource findDmCodeResourceById(@PathVariable("id") String id);

    @PostMapping("/saveDmCodeResource")
    DmCodeResource saveDmCodeResource(DmCodeResource dmCodeResource);

    @PostMapping("/batchSaveDmCodeResource")
    List<DmCodeResource> batchSaveDmCodeResource(List<DmCodeResource> dmCodeResources);

    @PostMapping("/findDmCodeResourceListByCondition")
    List<DmCodeResource> findDmCodeResourceListByCondition(DmCodeResource dmCodeResource);

    @PostMapping("/findOneDmCodeResourceByCondition")
    DmCodeResource findOneDmCodeResourceByCondition(DmCodeResource dmCodeResource);

    @PostMapping("/findDmCodeResourceCountByCondition")
    long findDmCodeResourceCountByCondition(DmCodeResource dmCodeResource);

    @PostMapping("/updateDmCodeResource")
    void updateDmCodeResource(DmCodeResource dmCodeResource);

    @GetMapping("/deleteDmCodeResource/{id}")
    void deleteDmCodeResource(@PathVariable("id") String id);

    @PostMapping("/deleteDmCodeResourceByCondition")
    void deleteDmCodeResourceByCondition(DmCodeResource dmCodeResource);
}
