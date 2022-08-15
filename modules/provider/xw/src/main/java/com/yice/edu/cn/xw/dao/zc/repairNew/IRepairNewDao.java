package com.yice.edu.cn.xw.dao.zc.repairNew;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRepairNewDao {
    List<RepairNew> findRepairNewListByCondition(RepairNew repairNew);

    long findRepairNewCountByCondition(RepairNew repairNew);

    RepairNew findOneRepairNewByCondition(RepairNew repairNew);

    RepairNew findRepairNewById(@Param("id") String id);

    void saveRepairNew(RepairNew repairNew);

    void updateRepairNew(RepairNew repairNew);

    void deleteRepairNew(@Param("id") String id);

    void deleteRepairNewByCondition(RepairNew repairNew);

    void batchSaveRepairNew(List<RepairNew> repairNews);


    void updateRepairNewPerson(RepairNew repairNew);

    void scrapRepairNew(RepairNew repairNew);


    RepairNew lookRepairNewByAssetNo(@Param("assetNo") String assetNo);


    RepairNew findRepairNewByIdNew(@Param("id") String id);

    List<RepairNew> findRepairNewListByConditionNew(RepairNew repairNew);


    double findRepairNewUpkeepCostsBySchool(RepairNew repairNew);

    long findRepairNewsCountByWeek(RepairNew repairNew);

    List<RepairNew> selectBuildingNameList(RepairNew repairNew);

    List<RepairNew> selectRoomName(RepairNew repairNew);

    List<RepairNew> findRepairNewsBySchoolIds(RepairNew repairNew);

    long findRepairNewCountBySchoolIds(RepairNew repairNew);

}
