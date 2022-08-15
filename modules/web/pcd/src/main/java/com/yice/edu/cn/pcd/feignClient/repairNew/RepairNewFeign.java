package com.yice.edu.cn.pcd.feignClient.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "repairNew",path = "/repairNew")
public interface RepairNewFeign {
    @GetMapping("/findRepairNewById/{id}")
    RepairNew findRepairNewById(@PathVariable("id") String id);
    @PostMapping("/findRepairNewListByCondition")
    List<RepairNew> findRepairNewListByCondition(RepairNew repairNew);
    @PostMapping("/findRepairNewCountByCondition")
    long findRepairNewCountByCondition(RepairNew repairNew);

    @PostMapping("/findRepairNewsBySchoolIds")
    List<RepairNew> findRepairNewsBySchoolIds(RepairNew repairNew);
    @PostMapping("/findRepairNewCountBySchoolIds")
    long findRepairNewCountBySchoolIds(RepairNew repairNew);
}
