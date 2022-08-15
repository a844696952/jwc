package com.yice.edu.cn.jw.service.building;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.building.SpaceOfType;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.building.IBuildingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private IBuildingDao buildingDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public Building findBuildingById(String id) {
        return buildingDao.findBuildingById(id);
    }
    @Transactional
    public void saveBuilding(Building building) {
        building.setId(sequenceId.nextId());
        buildingDao.saveBuilding(building);

        if(building.getLevel().equals("1")){
            for (int i = 0; i <building.getFloors() ; i++) {
                Building b=new Building();
                b.setSchoolId(building.getSchoolId());
                b.setId(sequenceId.nextId());
                b.setParentId(building.getId());
                b.setLevel("2");
                b.setName("楼层"+(i+1));
                buildingDao.saveBuilding(b);
            }

        }

    }
    @Transactional(readOnly = true)
    public List<Building> findBuildingListByCondition(Building building) {
        return buildingDao.findBuildingListByCondition(building);
    }
    @Transactional(readOnly = true)
    public Building findOneBuildingByCondition(Building building) {
        return buildingDao.findOneBuildingByCondition(building);
    }
    @Transactional(readOnly = true)
    public long findBuildingCountByCondition(Building building) {
        return buildingDao.findBuildingCountByCondition(building);
    }
    @Transactional
    public void updateBuilding(Building building) {
        if(building.getLevel().equals("1")){
            if(building.getFloors()>building.getOldFloors()){
                for (int i = building.getOldFloors(); i <building.getFloors() ; i++) {
                    Building b=new Building();
                    b.setSchoolId(building.getSchoolId());
                    b.setId(sequenceId.nextId());
                    b.setParentId(building.getId());
                    b.setLevel("2");
                    b.setName("楼层"+(i+1));
                    buildingDao.saveBuilding(b);
                }

            }else if(building.getFloors()<building.getOldFloors()){
                for (int i = building.getFloors(); i <building.getOldFloors() ; i++) {
                    Building b1=new Building();
                    b1.setName("楼层"+(i+1));
                    b1.setParentId(building.getId());
                    Building building1=buildingDao.findOneBuildingByCondition(b1);
                    buildingDao.deleteBuilding(building1.getId());

                    Building b2=new Building();
                    b2.setParentId(building1.getId());
                    List<Building> list= buildingDao.findBuildingListByCondition(b2);

                    list.forEach(building2 -> {
                        buildingDao.deleteBuilding(building2.getId());
                    });

                }
            }
        }
        buildingDao.updateBuilding(building);
    }
    @Transactional
    public void deleteBuilding(String id) {
        buildingDao.deleteBuilding(id);
    }
    @Transactional
    public void deleteBuildingByCondition(Building building) {
        buildingDao.deleteBuildingByCondition(building);
    }
    @Transactional
    public void batchSaveBuilding(List<Building> buildings){
        buildings.forEach(building -> building.setId(sequenceId.nextId()));
        buildingDao.batchSaveBuilding(buildings);
    }

    public List<Building> getBuildingSpaceTree(String schoolId) {
        Building building=new Building();
        building.setSchoolId(schoolId);
        List<Building> buildingList=findBuildingListByCondition(building);
        return ObjectKit.buildTree(buildingList,"-1");
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void deleteWholeBuilding(String id) {
        buildingDao.deleteBuilding(id);
        //删除楼层
        Building b1=new Building();
        b1.setParentId(id);
        List<Building> buildingList=buildingDao.findBuildingListByCondition(b1);
        buildingList.forEach(building -> {

            buildingDao.deleteBuilding(building.getId());
            //删除楼层下的场地
            Building b2=new Building();
            b2.setParentId(building.getId());
            List<Building> list= buildingDao.findBuildingListByCondition(b2);

            list.forEach(building2 -> {
                buildingDao.deleteBuilding(building2.getId());
            });
        });




    }

    public List<Building> findSchoolNumberRooms(String schoolId) {
        return buildingDao.findSchoolNumberRooms(schoolId);
    }


    public List<SpaceOfType> findSpaceListCountOfType(String schoolId) {
        return buildingDao.findSpaceListCountOfType(schoolId);
    }

    public List<Building> findBuildingRoomNameAll(Building building) {
        return buildingDao.findBuildingRoomNameAll(building);
    }

    public List<String> findBuildingListByParentIds(List<String> list) {
        return buildingDao.findBuildingListByParentIds(list);
    }
}
