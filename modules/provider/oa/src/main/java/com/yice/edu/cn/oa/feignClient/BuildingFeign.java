package com.yice.edu.cn.oa.feignClient;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "buildingFeign",path = "/building")
public interface BuildingFeign {

    @GetMapping("/findSchoolNumberRooms/{schoolId}")
    List<Building> findSchoolNumberRooms(@PathVariable("schoolId") String schoolId);

    @PostMapping("/findBuildingListByCondition")
    List<Building> findBuildingListByCondition(Building building);
}
