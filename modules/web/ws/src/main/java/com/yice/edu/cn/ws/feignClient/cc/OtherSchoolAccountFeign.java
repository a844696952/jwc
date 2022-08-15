package com.yice.edu.cn.ws.feignClient.cc;

import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value="jy",path = "/otherSchoolAccount")
public interface OtherSchoolAccountFeign {
    @GetMapping("/findOtherSchoolAccountById/{id}")
    OtherSchoolAccount findOtherSchoolAccountById(@PathVariable("id") String id);
}
