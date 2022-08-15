package com.yice.edu.cn.tap.service.jw.building;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.tap.feignClient.jw.building.BuildingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private BuildingFeign buildingFeign;

    public Building findBuildingById(String id) {
        return buildingFeign.findBuildingById(id);
    }

    public Building saveBuilding(Building building) {
        return buildingFeign.saveBuilding(building);
    }

    public List<Building> findBuildingListByCondition(Building building) {
        return buildingFeign.findBuildingListByCondition(building);
    }

    public Building findOneBuildingByCondition(Building building) {
        return buildingFeign.findOneBuildingByCondition(building);
    }

    public long findBuildingCountByCondition(Building building) {
        return buildingFeign.findBuildingCountByCondition(building);
    }

    public void updateBuilding(Building building) {
        buildingFeign.updateBuilding(building);
    }

    public void deleteBuilding(String id) {
        buildingFeign.deleteBuilding(id);
    }

    public void deleteBuildingByCondition(Building building) {
        buildingFeign.deleteBuildingByCondition(building);
    }

    public List<Building> getBuildingSpaceTree(String schoolId) {
       return buildingFeign.getBuildingSpaceTree( schoolId);
    }

    public void deleteWholeBuilding(String id) {
        buildingFeign.deleteWholeBuilding(id);
    }

    public List<Building> findSchoolNumberRooms(String schoolId) {
        return buildingFeign.findSchoolNumberRooms(schoolId);
    }

    public List<Building> findBuildingRoomNameAll(Building building) {
        return buildingFeign.findBuildingRoomNameAll(building);
    }
}
