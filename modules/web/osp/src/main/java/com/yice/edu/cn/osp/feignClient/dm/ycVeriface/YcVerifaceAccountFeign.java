package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceAccountFeign",path = "/ycVerifaceAccount")
public interface YcVerifaceAccountFeign {
    @GetMapping("/findYcVerifaceAccountById/{id}")
    YcVerifaceAccount findYcVerifaceAccountById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceAccount")
    YcVerifaceAccount saveYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount);
    @PostMapping("/findYcVerifaceAccountListByCondition")
    List<YcVerifaceAccount> findYcVerifaceAccountListByCondition(YcVerifaceAccount ycVerifaceAccount);
    @PostMapping("/findOneYcVerifaceAccountByCondition")
    YcVerifaceAccount findOneYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount);
    @PostMapping("/findYcVerifaceAccountCountByCondition")
    long findYcVerifaceAccountCountByCondition(YcVerifaceAccount ycVerifaceAccount);
    @PostMapping("/updateYcVerifaceAccount")
    void updateYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount);
    @GetMapping("/deleteYcVerifaceAccount/{id}")
    void deleteYcVerifaceAccount(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceAccountByCondition")
    void deleteYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount);
}
