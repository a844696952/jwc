package com.yice.edu.cn.osp.service.xw.dj.partyCommittee;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyCommittee.XwPartyCommitteeFeign;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMember.XwPartyMemberFeign;
import com.yice.edu.cn.osp.service.xw.dj.partyMember.XwPartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class XwPartyCommitteeService {
    @Autowired
    private XwPartyCommitteeFeign xwPartyCommitteeFeign;
    @Autowired
    private XwPartyMemberFeign xwPartyMemberFeign;

    public XwPartyCommittee findXwPartyCommitteeById(String id) {
        return xwPartyCommitteeFeign.findXwPartyCommitteeById(id);
    }

    public XwPartyCommittee saveXwPartyCommittee(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeFeign.saveXwPartyCommittee(xwPartyCommittee);
    }

    public List<XwPartyCommittee> findXwPartyCommitteeListByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeFeign.findXwPartyCommitteeListByCondition(xwPartyCommittee);
    }

    public XwPartyCommittee findOneXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeFeign.findOneXwPartyCommitteeByCondition(xwPartyCommittee);
    }

    public long findXwPartyCommitteeCountByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeFeign.findXwPartyCommitteeCountByCondition(xwPartyCommittee);
    }

    public void updateXwPartyCommittee(XwPartyCommittee xwPartyCommittee) {
        xwPartyCommitteeFeign.updateXwPartyCommittee(xwPartyCommittee);
    }

    public ResponseJson deleteXwPartyCommittee(String id) {
        ResponseJson responseJson = new ResponseJson();
        XwPartyMember xwPartyMember = new XwPartyMember();
        xwPartyMember.setPartyCommitteeId(id);
        Long cnt = xwPartyMemberFeign.findXwPartyMemberCountByCondition(xwPartyMember);
        //先判断党支部委员会下有没有党员
        if(cnt > 0){
            responseJson.getResult().setSuccess(false);
            responseJson.getResult().setMsg("该分类下有人员信息,不可以删除!请先移除人员！");
        }else{
            //再判断党支部下有没有党员
            xwPartyMember.setPartyCommitteeId(null);
            xwPartyMember.setPartyBranchId(id);
            Long cnt2 = xwPartyMemberFeign.findXwPartyMemberCountByCondition(xwPartyMember);
            if(cnt2 > 0){
                responseJson.getResult().setSuccess(false);
                responseJson.getResult().setMsg("该分类下有人员信息,不可以删除!请先移除人员！");
            }else{
                xwPartyCommitteeFeign.deleteXwPartyCommittee(id);
            }
        }
        return responseJson;
    }

    public void deleteXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee) {
        xwPartyCommitteeFeign.deleteXwPartyCommitteeByCondition(xwPartyCommittee);
    }

    /**
     * 获取党组织机构树
     * @param schoolId
     * @return
     */
    public List<XwPartyCommittee> findPartyCommitteeTree(String schoolId){
        return xwPartyCommitteeFeign.findPartyCommitteeTree(schoolId);
    }

    /**
     * 带有parentId的党组织集合
     * @param committeeParam
     * @return
     */
    public List<XwPartyCommittee> findCommitteeWithParentName(XwPartyCommittee committeeParam) {
        return xwPartyCommitteeFeign.findCommitteeWithParentName(committeeParam);
    }
}
