package com.yice.edu.cn.tap.controller.xw.djDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.tap.service.xw.dj.doc.XwDjPartyOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

/**
 *@ClassName XwDjPartyOrganization
 *@Description 党建活动组织结构树
 *@Author mayn
 *@Date 2019/5/30 9:52
 *@Version 1.0
 */
@RestController
@RequestMapping("/xwDjOrganization")
@Api(value = "/xwDjOrganization",description = "党建APP组织机构树")
public class XwDjPartyOrganizationController {

    @Autowired
    private XwDjPartyOrganizationService xwDjPartyOrganizationService;

    @GetMapping("/findPartyCommitteeTree")
    @ApiOperation(value = "根据学校Id查询党组织结构树", notes = "根据学校Id查询党组织结构树")
    public ResponseJson findPartyCommitteeTree(){
        List<XwPartyCommittee> partyCommitteeTree = xwDjPartyOrganizationService.findPartyCommitteeTree(mySchoolId());
        return new ResponseJson(partyCommitteeTree);
    }


    @GetMapping("/findPartyMemberTree")
    @ApiOperation(value = "根据党员类别查询党组织树结构", notes = "根据党员类别查询党组织树结构")
    public ResponseJson getPartyMemberTree() {
        List<XwPartyMember> partyMemberTree = xwDjPartyOrganizationService.getPartyMemberTree(mySchoolId());
        return new ResponseJson(partyMemberTree);
    }

}
