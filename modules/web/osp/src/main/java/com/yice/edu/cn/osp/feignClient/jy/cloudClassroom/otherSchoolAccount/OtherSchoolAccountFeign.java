package com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.otherSchoolAccount;

import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "otherSchoolAccountFeign",path = "/otherSchoolAccount")
public interface OtherSchoolAccountFeign {
    @GetMapping("/findOtherSchoolAccountById/{id}")
    OtherSchoolAccount findOtherSchoolAccountById(@PathVariable("id") String id);
    @PostMapping("/saveOtherSchoolAccount")
    OtherSchoolAccount saveOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount);
    @PostMapping("/findOtherSchoolAccountListByCondition")
    List<OtherSchoolAccount> findOtherSchoolAccountListByCondition(OtherSchoolAccount otherSchoolAccount);
    @PostMapping("/findOneOtherSchoolAccountByCondition")
    OtherSchoolAccount findOneOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount);
    @PostMapping("/findOtherSchoolAccountCountByCondition")
    long findOtherSchoolAccountCountByCondition(OtherSchoolAccount otherSchoolAccount);
    @PostMapping("/updateOtherSchoolAccount")
    void updateOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount);
    @GetMapping("/deleteOtherSchoolAccount/{id}")
    void deleteOtherSchoolAccount(@PathVariable("id") String id);
    @PostMapping("/deleteOtherSchoolAccountByCondition")
    void deleteOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount);
}
