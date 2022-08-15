package com.yice.edu.cn.osp.service.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.osp.feignClient.zc.repairNew.RepairFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairFileService {
    @Autowired
    private RepairFileFeign repairFileFeign;

    public RepairFile findRepairFileById(String id) {
        return repairFileFeign.findRepairFileById(id);
    }

    public RepairFile saveRepairFile(RepairFile repairFile) {
        return repairFileFeign.saveRepairFile(repairFile);
    }

    public List<RepairFile> findRepairFileListByCondition(RepairFile repairFile) {
        return repairFileFeign.findRepairFileListByCondition(repairFile);
    }

    public RepairFile findOneRepairFileByCondition(RepairFile repairFile) {
        return repairFileFeign.findOneRepairFileByCondition(repairFile);
    }

    public long findRepairFileCountByCondition(RepairFile repairFile) {
        return repairFileFeign.findRepairFileCountByCondition(repairFile);
    }

    public void updateRepairFile(RepairFile repairFile) {
        repairFileFeign.updateRepairFile(repairFile);
    }

    public void deleteRepairFile(String id) {
        repairFileFeign.deleteRepairFile(id);
    }

    public void deleteRepairFileByCondition(RepairFile repairFile) {
        repairFileFeign.deleteRepairFileByCondition(repairFile);
    }
}
