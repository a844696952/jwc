package com.yice.edu.cn.tap.feignClient.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplican;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "HouseApplicanFeign",path = "/houseApplican")
public interface HouseApplicanFeign {
    @GetMapping("/findHouseApplicanById/{id}")
    HouseApplican findHouseApplicanById(@PathVariable("id") String id);
    @PostMapping("/saveHouseApplican")
    HouseApplican saveHouseApplican(HouseApplican houseApplican);
    @PostMapping("/findHouseApplicanListByCondition")
    List<HouseApplican> findHouseApplicanListByCondition(HouseApplican houseApplican);
    @PostMapping("/findOneHouseApplicanByCondition")
    HouseApplican findOneHouseApplicanByCondition(HouseApplican houseApplican);
    @PostMapping("/findHouseApplicanCountByCondition")
    long findHouseApplicanCountByCondition(HouseApplican houseApplican);
    @PostMapping("/updateHouseApplican")
    void updateHouseApplican(HouseApplican houseApplican);
    @GetMapping("/deleteHouseApplican/{id}")
    void deleteHouseApplican(@PathVariable("id") String id);
    @PostMapping("/deleteHouseApplicanByCondition")
    void deleteHouseApplicanByCondition(HouseApplican houseApplican);

    @PostMapping("/findMyApproval")
    List<HouseApplican> findMyApproval(HouseApplican houseApplican);
    @PostMapping("/findMyApprovalCount")
    long findMyApprovalCount(HouseApplican houseApplican);

    @PostMapping("/saveHouseApplicanFromParent")
    HouseApplican saveHouseApplicanFromParent(HouseApplican houseApplican);

    @PostMapping("/updateHouseApplicanAndTeacher")
    void updateHouseApplicanAndTeacher(HouseApplican houseApplican);
}
