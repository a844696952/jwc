package com.yice.edu.cn.tap.feignClient.xw.dj.doc;

import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 组织结构树结构feign
 */
@FeignClient(value="xw",contextId = "xwPartyCommitteeFeign",path = "/xwPartyCommittee")
public interface XwDjPartyOrganizationFeign {

    @PostMapping("findPartyCommitteeTree/{schoolId}")
    List<XwPartyCommittee> findPartyCommitteeTree(@PathVariable("schoolId")String schoolId);

}
