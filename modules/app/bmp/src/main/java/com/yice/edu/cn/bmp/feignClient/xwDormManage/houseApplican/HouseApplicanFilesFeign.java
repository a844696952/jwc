package com.yice.edu.cn.bmp.feignClient.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "HouseApplicanFilesFeign",path = "/houseApplicanFiles")
public interface HouseApplicanFilesFeign {
    @GetMapping("/findHouseApplicanFilesById/{id}")
    HouseApplicanFiles findHouseApplicanFilesById(@PathVariable("id") String id);
    @PostMapping("/saveHouseApplicanFiles")
    HouseApplicanFiles saveHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles);
    @PostMapping("/findHouseApplicanFilesListByCondition")
    List<HouseApplicanFiles> findHouseApplicanFilesListByCondition(HouseApplicanFiles houseApplicanFiles);
    @PostMapping("/findOneHouseApplicanFilesByCondition")
    HouseApplicanFiles findOneHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles);
    @PostMapping("/findHouseApplicanFilesCountByCondition")
    long findHouseApplicanFilesCountByCondition(HouseApplicanFiles houseApplicanFiles);
    @PostMapping("/updateHouseApplicanFiles")
    void updateHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles);
    @GetMapping("/deleteHouseApplicanFiles/{id}")
    void deleteHouseApplicanFiles(@PathVariable("id") String id);
    @PostMapping("/deleteHouseApplicanFilesByCondition")
    void deleteHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles);
}
