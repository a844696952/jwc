package com.yice.edu.cn.jw.dao.building;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.building.SpaceOfType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBuildingDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<Building> findBuildingListByCondition(Building building);

    long findBuildingCountByCondition(Building building);

    Building findOneBuildingByCondition(Building building);

    Building findBuildingById(@Param("id") String id);

    void saveBuilding(Building building);

    void updateBuilding(Building building);

    void deleteBuilding(@Param("id") String id);

    void deleteBuildingByCondition(Building building);

    void batchSaveBuilding(List<Building> buildings);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<Building> findSchoolNumberRooms(@Param("schoolId") String schoolId);

    List<SpaceOfType> findSpaceListCountOfType(@Param("schoolId")String schoolId);

    List<Building> findBuildingRoomNameAll(Building building);

    List<String> findBuildingListByParentIds(List<String> list);
}
