package com.yice.edu.cn.osp.feignClient.xw.dj.partyMember;

import com.yice.edu.cn.common.pojo.xw.dj.partyMember.IdAndName;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberExcel;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberVaild;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/xwPartyMember")
public interface XwPartyMemberFeign {
    @GetMapping("/findXwPartyMemberById/{id}")
    XwPartyMember findXwPartyMemberById(@PathVariable("id") String id);
    @PostMapping("/saveXwPartyMember")
    XwPartyMember saveXwPartyMember(XwPartyMember xwPartyMember);
    @PostMapping("/findXwPartyMemberListByCondition")
    List<XwPartyMember> findXwPartyMemberListByCondition(XwPartyMember xwPartyMember);
    @PostMapping("/findOneXwPartyMemberByCondition")
    XwPartyMember findOneXwPartyMemberByCondition(XwPartyMember xwPartyMember);
    @PostMapping("/findXwPartyMemberCountByCondition")
    long findXwPartyMemberCountByCondition(XwPartyMember xwPartyMember);
    @PostMapping("/updateXwPartyMember")
    void updateXwPartyMember(XwPartyMember xwPartyMember);
    @GetMapping("/deleteXwPartyMember/{id}")
    void deleteXwPartyMember(@PathVariable("id") String id);
    @PostMapping("/deleteXwPartyMemberByCondition")
    void deleteXwPartyMemberByCondition(XwPartyMember xwPartyMember);

    @PostMapping("/findXwPartyMemberListByConditions")
    List<XwPartyMember> findXwPartyMemberListByConditions(XwPartyMember xwPartyMember);
    @PostMapping("/findXwPartyMemberCountByConditions")
    long findXwPartyMemberCountByConditions(XwPartyMember xwPartyMember);

    @PostMapping("/uploadExcel")
    void uploadExcel(List<XwPartyMemberExcel> list);

    @PostMapping("/getPartyMemberTree/{schoolId}")
    List<XwPartyMember> getPartyMemberTree(@PathVariable("schoolId")String schoolId);

    @GetMapping("/getTeacherList/{schoolId}")
    List<IdAndName> getTeacherList(@PathVariable("schoolId") String schoolId);
    @PostMapping("/batchSaveXwPartyMember")
    void batchSaveXwPartyMember(List<XwPartyMember> xwPartyMemberList);

    @PostMapping("/findXwPartyMemberListByArray")
    List<XwPartyMember> findXwPartyMemberListByArray(XwPartyMember xwPartyMember);

    @PostMapping("/getTeacherNameListByString")
    String getTeacherNameListByString(XwPartyMember xwPartyMember);

    @PostMapping("/bachUpdateByArray")
    void bachUpdateByArray(XwPartyMember xwPartyMember);

    @PostMapping("/batchUpdateParty")
    void batchUpdateParty(List<XwPartyMember> list);
    @PostMapping("/batchSaveXwPartyMemberByRowData")
    void batchSaveXwPartyMemberByRowData(List<XwPartyMember> xwPartyMembers);

    @GetMapping("/lookXwPartyMemberById/{id}")
    XwPartyMemberVaild lookXwPartyMemberById(@PathVariable("id") String id);
}
