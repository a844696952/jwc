package com.yice.edu.cn.osp.service.xw.dj.partyDuty;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyDuty.XwPartyDutyFeign;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMember.XwPartyMemberFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwPartyDutyService {
    @Autowired
    private XwPartyDutyFeign xwPartyDutyFeign;
    @Autowired
    private XwPartyMemberFeign xwPartyMemberFeign;

    public XwPartyDuty findXwPartyDutyById(String id) {
        return xwPartyDutyFeign.findXwPartyDutyById(id);
    }

    public XwPartyDuty saveXwPartyDuty(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyFeign.saveXwPartyDuty(xwPartyDuty);
    }

    public List<XwPartyDuty> findXwPartyDutyListByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyFeign.findXwPartyDutyListByCondition(xwPartyDuty);
    }

    public XwPartyDuty findOneXwPartyDutyByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyFeign.findOneXwPartyDutyByCondition(xwPartyDuty);
    }

    public long findXwPartyDutyCountByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyFeign.findXwPartyDutyCountByCondition(xwPartyDuty);
    }

    public void updateXwPartyDuty(XwPartyDuty xwPartyDuty) {
        xwPartyDutyFeign.updateXwPartyDuty(xwPartyDuty);
    }

    public ResponseJson deleteXwPartyDuty(String id) {
        ResponseJson responseJson = new ResponseJson();
        XwPartyMember xwPartyMember = new XwPartyMember();
        xwPartyMember.setPartyDutiesId(id);
        Long cnt = xwPartyMemberFeign.findXwPartyMemberCountByCondition(xwPartyMember);
        //先判断党支部委员会下有没有党员
        if(cnt > 0){
            responseJson.getResult().setSuccess(false);
            responseJson.getResult().setMsg("请先删除该职务下的党员！");
        }else{
            xwPartyDutyFeign.deleteXwPartyDuty(id);
        }
        return responseJson;
    }

    public void deleteXwPartyDutyByCondition(XwPartyDuty xwPartyDuty) {
        xwPartyDutyFeign.deleteXwPartyDutyByCondition(xwPartyDuty);
    }

    public XwPartyDuty findXwPartyDutyByName(String name) {
        return xwPartyDutyFeign.findXwPartyDutyByName(name);
    }
}
