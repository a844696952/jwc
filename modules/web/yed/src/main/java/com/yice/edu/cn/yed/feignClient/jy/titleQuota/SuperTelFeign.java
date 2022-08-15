package com.yice.edu.cn.yed.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "superTelFeign",path = "/superTel")
public interface SuperTelFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSuperTelById/{id}")
    SuperTel findSuperTelById(@PathVariable("id") String id);
    @PostMapping("/saveSuperTel")
    SuperTel saveSuperTel(SuperTel superTel);
    @PostMapping("/batchSaveSuperTel")
    void batchSaveSuperTel(List<SuperTel> superTels);
    @PostMapping("/findSuperTelListByCondition")
    List<SuperTel> findSuperTelListByCondition(SuperTel superTel);
    @PostMapping("/findOneSuperTelByCondition")
    SuperTel findOneSuperTelByCondition(SuperTel superTel);
    @PostMapping("/findSuperTelCountByCondition")
    long findSuperTelCountByCondition(SuperTel superTel);
    @PostMapping("/updateSuperTel")
    void updateSuperTel(SuperTel superTel);
    @PostMapping("/updateSuperTelForNotNull")
    void updateSuperTelForAll(SuperTel superTel);
    @GetMapping("/deleteSuperTel/{id}")
    void deleteSuperTel(@PathVariable("id") String id);
    @PostMapping("/deleteSuperTelByCondition")
    void deleteSuperTelByCondition(SuperTel superTel);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
