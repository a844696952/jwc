package com.yice.edu.cn.osp.service.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.osp.feignClient.zc.repairNew.RepairNewFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairNewService {
    @Autowired
    private RepairNewFeign repairNewFeign;

    public RepairNew findRepairNewById(String id) {
        return repairNewFeign.findRepairNewById(id);
    }

    public RepairNew saveRepairNew(RepairNew repairNew) {
        return repairNewFeign.saveRepairNew(repairNew);
    }

    public List<RepairNew> findRepairNewListByCondition(RepairNew repairNew) {
        return repairNewFeign.findRepairNewListByCondition(repairNew);
    }

    public RepairNew findOneRepairNewByCondition(RepairNew repairNew) {
        return repairNewFeign.findOneRepairNewByCondition(repairNew);
    }

    public long findRepairNewCountByCondition(RepairNew repairNew) {
        return repairNewFeign.findRepairNewCountByCondition(repairNew);
    }

    public void updateRepairNew(RepairNew repairNew) {
        repairNewFeign.updateRepairNew(repairNew);
    }

    public void deleteRepairNew(String id) {
        repairNewFeign.deleteRepairNew(id);
    }

    public void deleteRepairNewByCondition(RepairNew repairNew) {
        repairNewFeign.deleteRepairNewByCondition(repairNew);
    }


    public void updateRepairNewPerson(RepairNew repairNew) {
        repairNewFeign.updateRepairNewPerson(repairNew);
    }

    public void scrapRepairNew(RepairNew repairNew) {
        repairNewFeign.scrapRepairNew(repairNew);
    }

    public RepairNew lookRepairNewByAssetNo(String assetNo) {
        return repairNewFeign.lookRepairNewByAssetNo(assetNo);
    }




    public RepairNew findRepairNewByIdNew(String id) {
        return repairNewFeign.findRepairNewByIdNew(id);
    }


    public double findRepairNewUpkeepCostsBySchool(RepairNew repairNew) {
        return repairNewFeign.findRepairNewUpkeepCostsBySchool(repairNew);
    }

    public long findRepairNewsCountByWeek(RepairNew repairNew) {
        return repairNewFeign.findRepairNewsCountByWeek(repairNew);
    }

    public long findRepairNewsCountByWeekLose1(RepairNew repairNew) {
        return repairNewFeign.findRepairNewsCountByWeekLose1(repairNew);
    }

    public List<RepairNew> selectBuildingNameList(RepairNew repairNew) {
        return repairNewFeign.selectBuildingNameList(repairNew);
    }

    public List<RepairNew> selectRoomName(RepairNew repairNew) {
        return repairNewFeign.selectRoomName(repairNew);
    }
}
