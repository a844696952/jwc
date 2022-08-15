package com.yice.edu.cn.tap.feignClient.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "repairFile",path = "/repairFile")
public interface RepairFileFeign {
    @GetMapping("/findRepairFileById/{id}")
    RepairFile findRepairFileById(@PathVariable("id") String id);
    @PostMapping("/saveRepairFile")
    RepairFile saveRepairFile(RepairFile repairFile);
    @PostMapping("/findRepairFileListByCondition")
    List<RepairFile> findRepairFileListByCondition(RepairFile repairFile);
    @PostMapping("/findOneRepairFileByCondition")
    RepairFile findOneRepairFileByCondition(RepairFile repairFile);
    @PostMapping("/findRepairFileCountByCondition")
    long findRepairFileCountByCondition(RepairFile repairFile);
    @PostMapping("/updateRepairFile")
    void updateRepairFile(RepairFile repairFile);
    @GetMapping("/deleteRepairFile/{id}")
    void deleteRepairFile(@PathVariable("id") String id);
    @PostMapping("/deleteRepairFileByCondition")
    void deleteRepairFileByCondition(RepairFile repairFile);
}
