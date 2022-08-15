package com.yice.edu.cn.tap.service.xw.dj.doc;

import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.tap.feignClient.xw.dj.doc.XwDjPartyMemberFeign;
import com.yice.edu.cn.tap.feignClient.xw.dj.doc.XwDjPartyOrganizationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@ClassName XwDjPartyOrganizationService
 *@Description
 *@Author mayn
 *@Date 2019/5/30 9:56
 *@Version 1.0
 */
@Service
public class XwDjPartyOrganizationService {

    @Autowired
    private XwDjPartyMemberFeign xwDjPartyMemberFeign;

    @Autowired
    private XwDjPartyOrganizationFeign xwDjPartyOrganizationFeign;



    /**
     * 获取党组织机构树
     * @param schoolId
     * @return
     */
    public List<XwPartyCommittee> findPartyCommitteeTree(String schoolId){
        return xwDjPartyOrganizationFeign.findPartyCommitteeTree(schoolId);
    }

    /**
     * 获取类别树
     * @param schoolId
     * @return
     */
    public List<XwPartyMember> getPartyMemberTree(String schoolId){
        return xwDjPartyMemberFeign.getPartyMemberTree(schoolId);
    }

}
