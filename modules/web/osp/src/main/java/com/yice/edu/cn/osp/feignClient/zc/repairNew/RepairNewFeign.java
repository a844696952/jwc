package com.yice.edu.cn.osp.feignClient.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "repairNew",path = "/repairNew")
public interface RepairNewFeign {
    @GetMapping("/findRepairNewById/{id}")
    RepairNew findRepairNewById(@PathVariable("id") String id);
    @PostMapping("/saveRepairNew")
    RepairNew saveRepairNew(RepairNew repairNew);
    @PostMapping("/findRepairNewListByCondition")
    List<RepairNew> findRepairNewListByCondition(RepairNew repairNew);
    @PostMapping("/findOneRepairNewByCondition")
    RepairNew findOneRepairNewByCondition(RepairNew repairNew);
    @PostMapping("/findRepairNewCountByCondition")
    long findRepairNewCountByCondition(RepairNew repairNew);
    @PostMapping("/updateRepairNew")
    void updateRepairNew(RepairNew repairNew);
    @GetMapping("/deleteRepairNew/{id}")
    void deleteRepairNew(@PathVariable("id") String id);
    @PostMapping("/deleteRepairNewByCondition")
    void deleteRepairNewByCondition(RepairNew repairNew);


    @PostMapping("/updateRepairNewPerson")
    void updateRepairNewPerson(RepairNew repairNew);


    @PostMapping("/scrapRepairNew")
    void scrapRepairNew(RepairNew repairNew);


    @GetMapping("/lookRepairNewByAssetNo/{assetNo}")
    RepairNew lookRepairNewByAssetNo(@PathVariable("assetNo") String assetNo);


    @GetMapping("/findRepairNewByIdNew/{id}")
    RepairNew findRepairNewByIdNew(@PathVariable("id") String id);


    @PostMapping("/findRepairNewUpkeepCostsBySchool")
    double findRepairNewUpkeepCostsBySchool(RepairNew repairNew);


    @PostMapping("/findRepairNewsCountByWeek")
    long findRepairNewsCountByWeek(RepairNew repairNew);


    @PostMapping("/findRepairNewsCountByWeekLose1")
    long findRepairNewsCountByWeekLose1(RepairNew repairNew);

    @PostMapping("/selectBuildingNameList")
    List<RepairNew> selectBuildingNameList(RepairNew repairNew);

    @PostMapping("/selectRoomName")
    List<RepairNew> selectRoomName(RepairNew repairNew);
}
