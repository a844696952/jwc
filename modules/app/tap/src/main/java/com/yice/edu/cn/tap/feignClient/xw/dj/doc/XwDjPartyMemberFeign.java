package com.yice.edu.cn.tap.feignClient.xw.dj.doc;

import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 组织机构类别树
 */
@FeignClient(value="xw",path = "/xwPartyMember")
public interface XwDjPartyMemberFeign {
    /**
     * 获取党建组织机构成员树
     * @param schoolId
     * @return
     */
    @PostMapping("/getPartyMemberTree/{schoolId}")
    List<XwPartyMember> getPartyMemberTree(@PathVariable("schoolId")String schoolId);

}
