package com.yice.edu.cn.osp.feignClient.xw.dj.partyDuty;

import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwPartyDutyFeign",path = "/xwPartyDuty")
public interface XwPartyDutyFeign {
    @GetMapping("/findXwPartyDutyById/{id}")
    XwPartyDuty findXwPartyDutyById(@PathVariable("id") String id);
    @PostMapping("/saveXwPartyDuty")
    XwPartyDuty saveXwPartyDuty(XwPartyDuty xwPartyDuty);
    @PostMapping("/findXwPartyDutyListByCondition")
    List<XwPartyDuty> findXwPartyDutyListByCondition(XwPartyDuty xwPartyDuty);
    @PostMapping("/findOneXwPartyDutyByCondition")
    XwPartyDuty findOneXwPartyDutyByCondition(XwPartyDuty xwPartyDuty);
    @PostMapping("/findXwPartyDutyCountByCondition")
    long findXwPartyDutyCountByCondition(XwPartyDuty xwPartyDuty);
    @PostMapping("/updateXwPartyDuty")
    void updateXwPartyDuty(XwPartyDuty xwPartyDuty);
    @GetMapping("/deleteXwPartyDuty/{id}")
    void deleteXwPartyDuty(@PathVariable("id") String id);
    @PostMapping("/deleteXwPartyDutyByCondition")
    void deleteXwPartyDutyByCondition(XwPartyDuty xwPartyDuty);

    @GetMapping("/findXwPartyDutyByName/{name}")
    XwPartyDuty findXwPartyDutyByName(@PathVariable("name") String name);
}
