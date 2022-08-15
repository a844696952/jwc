package com.yice.edu.cn.pcd.service.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.pcd.feignClient.repairNew.RepairNewFeign;
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

    public List<RepairNew> findRepairNewListByCondition(RepairNew repairNew) {
        return repairNewFeign.findRepairNewListByCondition(repairNew);
    }

    public long findRepairNewCountByCondition(RepairNew repairNew) {
        return repairNewFeign.findRepairNewCountByCondition(repairNew);
    }

    public List<RepairNew> findRepairNewsBySchoolIds(RepairNew repairNew) {
        return repairNewFeign.findRepairNewsBySchoolIds(repairNew);
    }

    public long findRepairNewCountBySchoolIds(RepairNew repairNew) {
        return repairNewFeign.findRepairNewCountBySchoolIds(repairNew);
    }

}
