package com.yice.edu.cn.osp.feignClient.xw.dj.partyCommittee;

import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwPartyCommitteeFeign",path = "/xwPartyCommittee")
public interface XwPartyCommitteeFeign {
    @GetMapping("/findXwPartyCommitteeById/{id}")
    XwPartyCommittee findXwPartyCommitteeById(@PathVariable("id") String id);
    @PostMapping("/saveXwPartyCommittee")
    XwPartyCommittee saveXwPartyCommittee(XwPartyCommittee xwPartyCommittee);
    @PostMapping("/findXwPartyCommitteeListByCondition")
    List<XwPartyCommittee> findXwPartyCommitteeListByCondition(XwPartyCommittee xwPartyCommittee);
    @PostMapping("/findOneXwPartyCommitteeByCondition")
    XwPartyCommittee findOneXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee);
    @PostMapping("/findXwPartyCommitteeCountByCondition")
    long findXwPartyCommitteeCountByCondition(XwPartyCommittee xwPartyCommittee);
    @PostMapping("/updateXwPartyCommittee")
    void updateXwPartyCommittee(XwPartyCommittee xwPartyCommittee);
    @GetMapping("/deleteXwPartyCommittee/{id}")
    void deleteXwPartyCommittee(@PathVariable("id") String id);
    @PostMapping("/deleteXwPartyCommitteeByCondition")
    void deleteXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee);
    @PostMapping("findPartyCommitteeTree/{schoolId}")
    List<XwPartyCommittee> findPartyCommitteeTree(@PathVariable("schoolId")String schoolId);

    @PostMapping("findCommitteeWithParentName")
    List<XwPartyCommittee> findCommitteeWithParentName(XwPartyCommittee committeeParam);
}
