package com.yice.edu.cn.yed.feignClient.jw.building;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "buildingFeign",path = "/building")
public interface BuildingFeign {
    @GetMapping("/findBuildingById/{id}")
    Building findBuildingById(@PathVariable("id") String id);
    @PostMapping("/saveBuilding")
    Building saveBuilding(Building building);
    @PostMapping("/findBuildingListByCondition")
    List<Building> findBuildingListByCondition(Building building);
    @PostMapping("/findOneBuildingByCondition")
    Building findOneBuildingByCondition(Building building);
    @PostMapping("/findBuildingCountByCondition")
    long findBuildingCountByCondition(Building building);
    @PostMapping("/updateBuilding")
    void updateBuilding(Building building);
    @GetMapping("/deleteBuilding/{id}")
    void deleteBuilding(@PathVariable("id") String id);
    @PostMapping("/deleteBuildingByCondition")
    void deleteBuildingByCondition(Building building);
    @GetMapping("/getBuildingSpaceTree/{schoolId}")
    List<Building> getBuildingSpaceTree(@PathVariable("schoolId") String schoolId);

    @GetMapping("/deleteWholeBuilding/{id}")
    void deleteWholeBuilding(@PathVariable("id") String id);
    @PostMapping("/findBuildingListByParentIds")
    List<String> findBuildingListByParentIds(List<String> list);
}
