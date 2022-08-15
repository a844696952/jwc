package com.yice.edu.cn.osp.feignClient.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmCountDownManageFeign",path = "/dmCountDownManage")
public interface DmCountDownManageFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmCountDownManageById/{id}")
    DmCountDownManage findDmCountDownManageById(@PathVariable("id") String id);
    @PostMapping("/saveDmCountDownManage")
    DmCountDownManage saveDmCountDownManage(DmCountDownManage dmCountDownManage);
    @PostMapping("/findDmCountDownManageListByCondition")
    List<DmCountDownManage> findDmCountDownManageListByCondition(DmCountDownManage dmCountDownManage);
    @PostMapping("/findOneDmCountDownManageByCondition")
    DmCountDownManage findOneDmCountDownManageByCondition(DmCountDownManage dmCountDownManage);
    @PostMapping("/findDmCountDownManageCountByCondition")
    long findDmCountDownManageCountByCondition(DmCountDownManage dmCountDownManage);
    @PostMapping("/updateDmCountDownManage")
    void updateDmCountDownManage(DmCountDownManage dmCountDownManage);
    @PostMapping("/updateDmCountDownManageForNotNull")
    void updateDmCountDownManageForAll(DmCountDownManage dmCountDownManage);
    @GetMapping("/deleteDmCountDownManage/{id}")
    void deleteDmCountDownManage(@PathVariable("id") String id);
    @PostMapping("/deleteDmCountDownManageByCondition")
    void deleteDmCountDownManageByCondition(DmCountDownManage dmCountDownManage);

    @PostMapping("/updateDmCountDownManageStatus")
    void updateDmCountDownManageStatus(DmCountDownManage dmCountDownManage);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
