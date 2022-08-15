package com.yice.edu.cn.ewb.feignClient.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.HelpCenter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "helpCenterFeign",path = "/helpCenter")
public interface HelpCenterFeign {
    @GetMapping("/findHelpCenterById/{id}")
    HelpCenter findHelpCenterById(@PathVariable("id") String id);
    @PostMapping("/saveHelpCenter")
    HelpCenter saveHelpCenter(HelpCenter helpCenter);
    @PostMapping("/findHelpCenterListByCondition")
    List<HelpCenter> findHelpCenterListByCondition(HelpCenter helpCenter);
    @PostMapping("/findOneHelpCenterByCondition")
    HelpCenter findOneHelpCenterByCondition(HelpCenter helpCenter);
    @PostMapping("/findHelpCenterCountByCondition")
    long findHelpCenterCountByCondition(HelpCenter helpCenter);
    @PostMapping("/updateHelpCenter")
    void updateHelpCenter(HelpCenter helpCenter);
    @GetMapping("/deleteHelpCenter/{id}")
    void deleteHelpCenter(@PathVariable("id") String id);
    @PostMapping("/deleteHelpCenterByCondition")
    void deleteHelpCenterByCondition(HelpCenter helpCenter);
}
