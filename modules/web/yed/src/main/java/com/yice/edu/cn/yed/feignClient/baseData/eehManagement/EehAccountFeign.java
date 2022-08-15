package com.yice.edu.cn.yed.feignClient.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "eehAccountFeign",path = "/eehAccount")
public interface EehAccountFeign {
    @GetMapping("/findEehAccountById/{id}")
    EehAccount findEehAccountById(@PathVariable("id") String id);
    @PostMapping("/saveEehAccount")
    EehAccount saveEehAccount(EehAccount eehAccount);
    @PostMapping("/findEehAccountListByCondition")
    List<EehAccount> findEehAccountListByCondition(EehAccount eehAccount);
    @PostMapping("/findOneEehAccountByCondition")
    EehAccount findOneEehAccountByCondition(EehAccount eehAccount);
    @PostMapping("/findEehAccountCountByCondition")
    long findEehAccountCountByCondition(EehAccount eehAccount);
    @PostMapping("/updateEehAccount")
    void updateEehAccount(EehAccount eehAccount);
    @GetMapping("/deleteEehAccount/{id}")
    void deleteEehAccount(@PathVariable("id") String id);
    @PostMapping("/deleteEehAccountByCondition")
    void deleteEehAccountByCondition(EehAccount eehAccount);
    @GetMapping("/updatePassword/{id}")
    void updatePassword(@PathVariable("id") String id);

    @PostMapping("/updateEehAccountStatus")
    void updateEehAccountStatus(EehAccount eehAccount);

    @PostMapping("/findOneEehAccountByAccountType")
    EehAccount findOneEehAccountByAccountType(EehAccount eehAccount);
}
