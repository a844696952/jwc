package com.yice.edu.cn.yed.feignClient.jw.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "thirdPartyFeign",path = "/thirdParty")
public interface ThirdPartyFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findThirdPartyById/{id}")
    ThirdParty findThirdPartyById(@PathVariable("id") String id);
    @PostMapping("/saveThirdParty")
    ThirdParty saveThirdParty(ThirdParty thirdParty);
    @PostMapping("/batchSaveThirdParty")
    void batchSaveThirdParty(List<ThirdParty> thirdPartys);
    @PostMapping("/findThirdPartyListByCondition")
    List<ThirdParty> findThirdPartyListByCondition(ThirdParty thirdParty);
    @PostMapping("/findOneThirdPartyByCondition")
    ThirdParty findOneThirdPartyByCondition(ThirdParty thirdParty);
    @PostMapping("/findThirdPartyCountByCondition")
    long findThirdPartyCountByCondition(ThirdParty thirdParty);
    @PostMapping("/updateThirdParty")
    void updateThirdParty(ThirdParty thirdParty);
    @PostMapping("/updateThirdPartyForAll")
    void updateThirdPartyForAll(ThirdParty thirdParty);
    @GetMapping("/deleteThirdParty/{id}")
    void deleteThirdParty(@PathVariable("id") String id);
    @PostMapping("/deleteThirdPartyByCondition")
    void deleteThirdPartyByCondition(ThirdParty thirdParty);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
